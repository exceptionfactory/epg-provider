package com.exceptionfactory.epg.provider.model.zap2it.grid

data class Channel(
        val callSign: String,
        val affiliateCallSign: String,
        val channelId: String,
        val channelNo: String,
        val id: String,
        val thumbnail: String,
        val stationFilters: List<String>,
        val stationGenres: List<Boolean>,
        val events: List<Event>
)