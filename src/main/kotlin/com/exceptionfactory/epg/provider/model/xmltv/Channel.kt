package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

/**
 * XMLTV Channel
 */
data class Channel(
        @JacksonXmlProperty(isAttribute = true)
        val id: String,
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "display-name")
        val displayNames: List<DisplayName>,
        val icon: Icon
)