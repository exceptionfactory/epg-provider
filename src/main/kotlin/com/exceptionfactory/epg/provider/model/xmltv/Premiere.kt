package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

/**
 * Programme Premiere Indicator
 */
data class Premiere(
        @JacksonXmlText val value: String? = null
)