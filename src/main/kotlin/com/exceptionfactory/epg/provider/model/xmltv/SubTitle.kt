package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

/**
 * Programme Subtitle
 */
data class SubTitle(
        @JacksonXmlText val value: String,
        @JacksonXmlProperty(isAttribute = true) val lang: String = "en"
)