package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

/**
 * Programme Length
 */
data class Length(
        @JacksonXmlText val value: Int,
        @JacksonXmlProperty(isAttribute = true) val units: String = "minutes"
)