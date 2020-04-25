package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

/**
 * Programme New Indicator
 */
data class New(
        @JacksonXmlText val value: String? = null
)