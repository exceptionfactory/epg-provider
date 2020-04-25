package com.exceptionfactory.epg.provider.service.zap2it.grid

import com.exceptionfactory.epg.provider.model.zap2it.grid.Grid
import com.exceptionfactory.epg.provider.model.zap2it.grid.GridParameters
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.net.URI

/**
 * Default Grid Service implementation using Spring WebClient for Zap2it Grid Listings
 */
@Service
class DefaultGridService(@Value("\${epg.provider.zap2it.grid.uri}") val gridUri: URI,
                         @Autowired val webClientBuilder: WebClient.Builder) : GridService {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    val webClient: WebClient by lazy {
        webClientBuilder.build()
    }

    /**
     * Get Grid Listing using Spring WebClient with caching
     *
     * @param gridParameters Grid Parameters
     * @return Reactive Mono Grid
     */
    @Cacheable(value = ["grid"])
    override fun getGrid(gridParameters: GridParameters): Mono<Grid> {
        logger.info("Get Grid Country [{}] Postal Code [{}] Time [{}]", gridParameters.country, gridParameters.postalCode, gridParameters.time)
        val uri = UriComponentsBuilder.fromUri(gridUri)
                .queryParam("country", gridParameters.country)
                .queryParam("postalCode", gridParameters.postalCode)
                .queryParam("headendId", gridParameters.headendId)
                .queryParam("timespan", gridParameters.timespan)
                .queryParam("time", gridParameters.time)
                .build()
                .toUri()
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Grid::class.java)
    }
}