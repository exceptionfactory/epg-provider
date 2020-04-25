package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

/**
 * XMLTV Icon
 */
data class Icon(
        @JacksonXmlProperty(isAttribute = true)
        val src: String
)