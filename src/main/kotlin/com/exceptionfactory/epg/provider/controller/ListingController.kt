package com.exceptionfactory.epg.provider.controller

import com.exceptionfactory.epg.provider.model.xmltv.Tv
import com.exceptionfactory.epg.provider.model.xmltv.listing.ListingParameters
import com.exceptionfactory.epg.provider.service.xmltv.listing.ListingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

/**
 * XMLTV Listings Controller
 */
@RequestMapping(value = ["/listings/zap2it"])
@RestController
class ListingController(@Autowired val listingService: ListingService) {
    /**
     * Get Aggregated XMLTV Listings
     *
     * @param country Country Trigraph must be either CAN or USA
     * @param postalCode Alphanumeric Postal Code
     * @param headendId Head end Identifier for Zap2it Listings
     * @param duration Duration
     * @return XMLTV Listings
     */
    @GetMapping(value = ["/{country}/{postalCode}/{headendId}"], produces = [MediaType.APPLICATION_XML_VALUE])
    fun getListings(@PathVariable country: String,
                    @PathVariable postalCode: String,
                    @PathVariable headendId: String,
                    @RequestParam(defaultValue = "PT6H") duration: Duration): Tv {
        val listingParameters = ListingParameters(country, postalCode, headendId, duration)
        return listingService.getListings(listingParameters).block()!!
    }
}