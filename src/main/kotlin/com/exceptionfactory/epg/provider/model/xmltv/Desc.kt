package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

/**
 * Programme Description
 */
data class Desc(
        @JacksonXmlText val value: String,
        @JacksonXmlProperty(isAttribute = true) val lang: String = "en"
)