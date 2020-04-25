package com.exceptionfactory.epg.provider

import com.exceptionfactory.epg.provider.controller.ListingController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EpgProviderApplicationTests {
    private val country = "USA"

    private val postalCode = "10001"

    private val headendId = "DFLTE"

    private val firstChannelDisplayNamePath = "/tv/channel[1]/display-name[1]"

    private val firstChannelDisplayName = "1"

    private val firstProgrammeEpisodeNumPath = "/tv/programme[1]/episode-num[1]"

    private val listingsUri = "/listings/zap2it/{country}/{postalCode}/{headendId}"

    private val listingsDurationUri = "${listingsUri}?duration=PT6H"

    @Autowired
    private lateinit var listingController: ListingController

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun testControllerFound() {
        assertThat(listingController).isNotNull
    }

    @Test
    fun testListingsWithChannelAndProgrammeFound() {
        getListings(listingsUri)
    }

    @Test
    fun testListingsDurationWithChannelAndProgrammeFound() {
        getListings(listingsDurationUri)
    }

    private fun getListings(uri: String) {
        webClient.get()
                .uri(uri, country, postalCode, headendId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful
                .expectHeader()
                .contentType(MediaType.APPLICATION_XML)
                .expectBody()
                .xpath(firstChannelDisplayNamePath).isEqualTo(firstChannelDisplayName)
                .xpath(firstProgrammeEpisodeNumPath).exists()
    }
}
