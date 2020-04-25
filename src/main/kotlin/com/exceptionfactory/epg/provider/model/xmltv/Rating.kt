package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

/**
 * Programme Rating
 */
data class Rating(
        @JacksonXmlText val value: String,
        @JacksonXmlProperty(isAttribute = true) val system: String = "MPAA"
)