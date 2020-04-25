package com.exceptionfactory.epg.provider.model.zap2it.grid

data class Program(
        val title: String,
        val id: String,
        val tmsId: String,
        val shortDesc: String?,
        val season: String?,
        val releaseYear: String?,
        val episode: String?,
        val episodeTitle: String?,
        val seriesId: String,
        val isGeneric: String
)