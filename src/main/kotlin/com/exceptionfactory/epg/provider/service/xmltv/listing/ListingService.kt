package com.exceptionfactory.epg.provider.service.xmltv.listing

import com.exceptionfactory.epg.provider.model.xmltv.Tv
import com.exceptionfactory.epg.provider.model.xmltv.listing.ListingParameters
import reactor.core.publisher.Mono

/**
 * XMLTV Listing Service
 */
interface ListingService {
    /**
     * Get XMLTV Listings based on Parameters
     *
     * @param listingParameters Listing Parameters
     * @return Reactive Mono XMLTV Listings
     */
    fun getListings(listingParameters: ListingParameters): Mono<Tv>
}