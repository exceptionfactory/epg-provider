package com.exceptionfactory.epg.provider.service.xmltv.listing

import com.exceptionfactory.epg.provider.model.xmltv.Tv
import com.exceptionfactory.epg.provider.model.xmltv.listing.ListingParameters
import com.exceptionfactory.epg.provider.model.zap2it.grid.GridParameters
import com.exceptionfactory.epg.provider.service.zap2it.grid.GridService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * Default Listing Service implemented using Zap2it Grid Listings
 */
@Service
class DefaultListingService(@Autowired val gridService: GridService, @Autowired val listingProducer: ListingProducer) : ListingService {
    /** Maximum Number of Hours for each set of Listings */
    private val listingHours = 6L

    /**
     * Get XMLTV Listings from multiple Zap2it Grid Service Listings
     *
     * @param listingParameters Listing Parameters
     * @return Reactive Mono XMLTV Listings
     */
    override fun getListings(listingParameters: ListingParameters): Mono<Tv> {
        var start = Instant.now().truncatedTo(ChronoUnit.HOURS)
        val listings = ArrayList<Tv>()

        val end = start.plus(listingParameters.duration)
        while (end > start) {
            val gridParameters = getGridParameters(listingParameters, start)
            val monoGrid = gridService.getGrid(gridParameters)
            val grid = monoGrid.block() ?: throw IllegalArgumentException("Grid Listings not found")
            val listing = listingProducer.getTv(grid)
            listings.add(listing)

            start = start.plus(listingHours, ChronoUnit.HOURS)
        }
        return Mono.just(getAggregatedListings(listings))
    }

    /**
     * Get Grid Parameters from Listing Location and Start Time
     *
     * @param listingParameters Listing Parameters
     * @param start Start Time for Listings
     * @return Grid Parameters
     */
    private fun getGridParameters(listingParameters: ListingParameters, start: Instant): GridParameters {
        return GridParameters(
                country = listingParameters.country,
                postalCode = listingParameters.postalCode,
                headendId = listingParameters.headendId,
                time = start.epochSecond)
    }

    /**
     * Get Aggregated TV Listings
     *
     * @param listings XMLTV Listings
     * @return Aggregated XMLTV Listings
     */
    private fun getAggregatedListings(listings: List<Tv>): Tv {
        val channels = listings.flatMap { listing -> listing.channel }.distinctBy { it.id }
        val programmes = listings.flatMap { listing -> listing.programme }
        val listing = listings.first()
        return Tv(listing.sourceInfoUrl, listing.sourceInfoName, listing.sourceDataUrl, channels, programmes)
    }
}