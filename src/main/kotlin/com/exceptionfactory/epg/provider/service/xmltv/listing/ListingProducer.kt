package com.exceptionfactory.epg.provider.service.xmltv.listing

import com.exceptionfactory.epg.provider.model.xmltv.Category
import com.exceptionfactory.epg.provider.model.xmltv.Channel
import com.exceptionfactory.epg.provider.model.xmltv.Desc
import com.exceptionfactory.epg.provider.model.xmltv.DisplayName
import com.exceptionfactory.epg.provider.model.xmltv.EpisodeNum
import com.exceptionfactory.epg.provider.model.xmltv.EpisodeNumberSystem
import com.exceptionfactory.epg.provider.model.xmltv.Icon
import com.exceptionfactory.epg.provider.model.xmltv.Length
import com.exceptionfactory.epg.provider.model.xmltv.New
import com.exceptionfactory.epg.provider.model.xmltv.Premiere
import com.exceptionfactory.epg.provider.model.xmltv.PreviouslyShown
import com.exceptionfactory.epg.provider.model.xmltv.Programme
import com.exceptionfactory.epg.provider.model.xmltv.Rating
import com.exceptionfactory.epg.provider.model.xmltv.SubTitle
import com.exceptionfactory.epg.provider.model.xmltv.Title
import com.exceptionfactory.epg.provider.model.xmltv.Tv
import com.exceptionfactory.epg.provider.model.zap2it.grid.Event
import com.exceptionfactory.epg.provider.model.zap2it.grid.Grid
import com.exceptionfactory.epg.provider.model.zap2it.grid.Program

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.time.format.DateTimeFormatter

/**
 * XMLTV Listing Producer from Grid Listings
 */
@Component
class ListingProducer(
        @Value("\${epg.provider.zap2it.grid.uri}") val gridUri: URI,
        @Value("\${epg.provider.zap2it.info.uri}") val infoUri: String,
        @Value("\${epg.provider.zap2it.icon.uri}") val iconUri: String,
        @Value("\${epg.provider.zap2it.info.name}") val infoName: String) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss Z")

    private val programIdRegex = Regex("^(.+\\d{8})(\\d{4})$")

    private val filterPrefix = "filter-"

    private val liveFlag = "Flag"

    private val newFlag = "New"

    private val notPreviouslyShownFlags = listOf(liveFlag, newFlag)

    private val premiereFlag = "Premiere"

    private val zero = '0'

    private val padStartSeason = 2

    /**
     * Get XMLTV Listings
     *
     * @param grid Zap2it Grid
     * @return XMLTV Listings
     */
    fun getTv(grid: Grid): Tv {
        val channels = getChannels(grid)
        val programmes = getProgrammes(grid)
        return Tv(infoUri, infoName, gridUri.toString(), channels, programmes)
    }

    /**
     * Get Channels
     *
     * @param grid Grid Listings
     * @return XMLTV Channels
     */
    private fun getChannels(grid: Grid): List<Channel> {
        return grid.channels.map { channel ->
            val id = getChannelId(channel.id)
            val thumbnailUri = getThumbnailUri(channel.thumbnail)
            val icon = Icon(thumbnailUri.toString())
            Channel(id, getDisplayNames(channel), icon)
        }
    }

    /**
     * Get Programmes
     *
     * @param grid Grid Listings
     * @return XMLTV Programmes
     */
    private fun getProgrammes(grid: Grid): List<Programme> {
        val programmes = ArrayList<Programme>()
        grid.channels.forEach { channel ->
            val id = getChannelId(channel.id)
            val channelProgrammes = channel.events.map { event -> getProgramme(event, id) }
            programmes.addAll(channelProgrammes)
        }
        return programmes
    }

    /**
     * Get Channel Identifier using RFC 2838 DNS formatting
     *
     * @param id Identifier from Channel Record
     * @return Channel Identifier with Source URI Host DNS
     */
    private fun getChannelId(id: String): String {
        return "${id}.${gridUri.host}"
    }

    /**
     * Get Channel Display Names from Grid Channel using Call Sign and Channel Number
     *
     * @param channel Grid Channel
     * @return Display Names
     */
    private fun getDisplayNames(channel: com.exceptionfactory.epg.provider.model.zap2it.grid.Channel): List<DisplayName> {
        val channelNo = DisplayName(channel.channelNo)
        val channelNoCallSign = DisplayName("${channel.channelNo} ${channel.callSign}")
        val callSign = DisplayName(channel.callSign)
        return listOf(channelNo, channelNoCallSign, callSign)
    }

    /**
     * Get Thumbnail URI from Channel Thumbnail
     *
     * @param thumbnail Channel Thumbnail
     * @return Thumbnail URI
     */
    private fun getThumbnailUri(thumbnail: String): URI {
        val iconSrc = "https:${thumbnail}"
        return UriComponentsBuilder.fromHttpUrl(iconSrc).query(null).build().toUri()
    }

    /**
     * Get Programme from Event and Program properties
     *
     * @param event Event Record
     * @param channelId Channel Identifier
     * @return Programme parsed from Event Record
     */
    private fun getProgramme(event: Event, channelId: String): Programme {
        val program = event.program
        val subTitle = program.episodeTitle?.let { SubTitle(program.episodeTitle) }
        val desc = program.shortDesc?.let { Desc(program.shortDesc) }
        val rating = event.rating?.let { Rating(event.rating) }
        val categories = event.filter.map { filter -> Category(filter.removePrefix(filterPrefix)) }
        val new = if (event.flag.contains(newFlag)) New() else null
        val premiere = if (event.flag.contains(premiereFlag)) Premiere() else null
        val previouslyShown = getPreviouslyShown(event)
        val iconSrc = UriComponentsBuilder.fromHttpUrl(iconUri).buildAndExpand(event.thumbnail).toUri().toString()

        return Programme(
                event.startTime.format(formatter),
                event.endTime?.format(formatter),
                channelId,
                Title(program.title),
                subTitle,
                desc,
                Length(event.duration.toInt()),
                Icon(iconSrc),
                new,
                premiere,
                previouslyShown,
                rating,
                categories,
                getEpisodeNumbers(event.program)
        )
    }

    /**
     * Get Episode Numbers from Program properties
     *
     * @param program Program Record
     * @return Episode Numbers parsed from Program
     */
    private fun getEpisodeNumbers(program: Program): List<EpisodeNum> {
        val episodeNumbers = ArrayList<EpisodeNum>()

        programIdRegex.find(program.tmsId)?.let {
            val (seriesId, episodeId) = it.destructured
            val programId = EpisodeNumberSystem.PROGRAM_ID.format.format(seriesId, episodeId)
            episodeNumbers.add(EpisodeNum(programId, EpisodeNumberSystem.PROGRAM_ID.system))
        }

        if (!program.season.isNullOrBlank() && !program.episode.isNullOrBlank()) {
            val seasonNumber = program.season.padStart(padStartSeason, zero)
            val episodeNumber = program.episode.padStart(padStartSeason, zero)
            val seasonEpisodeNumber = EpisodeNumberSystem.SEASON_EPISODE.format.format(seasonNumber, episodeNumber)
            episodeNumbers.add(EpisodeNum(seasonEpisodeNumber, EpisodeNumberSystem.SEASON_EPISODE.system))
        }
        return episodeNumbers
    }

    /**
     * Get Previously Shown indicator when Event Flags do not contain New or Live status
     *
     * @param event Event Record
     * @return Previously Shown or null when indicated
     */
    private fun getPreviouslyShown(event: Event): PreviouslyShown? {
        return if (event.flag.none { flag -> notPreviouslyShownFlags.contains(flag) }) {
            PreviouslyShown()
        } else {
            null
        }
    }
}