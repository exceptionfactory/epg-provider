package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

/**
 * Channel Display Name
 */
data class DisplayName(
        @JacksonXmlText val value: String,
        @JacksonXmlProperty(isAttribute = true) val lang: String = "en"
)