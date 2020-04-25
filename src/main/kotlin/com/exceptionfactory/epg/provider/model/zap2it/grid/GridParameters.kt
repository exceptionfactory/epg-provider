package com.exceptionfactory.epg.provider.model.zap2it.grid

/**
 * Zap2it Grid Listing Parameters
 */
data class GridParameters(
        val country: String,
        val postalCode: String,
        val headendId: String,
        val timespan: Int = 6,
        val time: Long
) {
}