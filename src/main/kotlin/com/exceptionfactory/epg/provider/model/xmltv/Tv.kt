package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

/**
 * XMLTV Listings with Channels and Programmes
 */
@JacksonXmlRootElement(localName = "tv")
data class Tv(
        @JacksonXmlProperty(localName = "source-info-url", isAttribute = true)
        val sourceInfoUrl: String,

        @JacksonXmlProperty(localName = "source-info-name", isAttribute = true)
        val sourceInfoName: String,

        @JacksonXmlProperty(localName = "source-data-url", isAttribute = true)
        val sourceDataUrl: String,

        @JacksonXmlElementWrapper(useWrapping = false)
        val channel: List<Channel>,

        @JacksonXmlElementWrapper(useWrapping = false)
        val programme: List<Programme>
)