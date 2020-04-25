package com.exceptionfactory.epg.provider.model.zap2it.grid

import java.time.OffsetDateTime

data class Event(
        val callSign: String,
        val duration: String,
        val startTime: OffsetDateTime,
        val endTime: OffsetDateTime?,
        val thumbnail: String,
        val channelNo: String,
        val seriesId: String,
        val rating: String?,
        val tags: List<String>,
        val filter: List<String>,
        val flag: List<String>,
        val program: Program
)