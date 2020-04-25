package com.exceptionfactory.epg.provider.model.xmltv

/**
 * XMLTV Episode Number System
 */
enum class EpisodeNumberSystem(val system: String, val format: String) {
    PROGRAM_ID("dd_progid", "%s.%s"),

    SEASON_EPISODE("SxxExx", "S%sE%s")
}