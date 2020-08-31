package com.exceptionfactory.epg.provider.model.xmltv

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

/**
 * Programme Previously Shown Indicator
 */
data class PreviouslyShown(
        @JacksonXmlText val value: String? = null
)