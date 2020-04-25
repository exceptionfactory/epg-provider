package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

/**
 * XMLTV Programme
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Programme(
        @JacksonXmlProperty(isAttribute = true)
        val start: String,

        @JacksonXmlProperty(isAttribute = true)
        val stop: String?,

        @JacksonXmlProperty(isAttribute = true)
        val channel: String,

        val title: Title,

        val subTitle: SubTitle?,

        val desc: Desc?,

        val length: Length,

        val icon: Icon?,

        val new: New?,

        val premiere: Premiere?,

        val rating: Rating?,

        @JacksonXmlElementWrapper(useWrapping = false)
        val category: List<Category>,

        @JacksonXmlProperty(localName = "episode-num")
        @JacksonXmlElementWrapper(useWrapping = false)
        val episodeNum: List<EpisodeNum>
)