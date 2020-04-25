package com.exceptionfactory.epg.provider.model.xmltv.listing

import java.time.Duration

/**
 * Listing Parameters
 */
data class ListingParameters(
        val country: String,
        val postalCode: String,
        val headendId: String,
        val duration: Duration
)