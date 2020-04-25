package com.exceptionfactory.epg.provider.service.zap2it.grid

import com.exceptionfactory.epg.provider.model.zap2it.grid.Grid
import com.exceptionfactory.epg.provider.model.zap2it.grid.GridParameters
import reactor.core.publisher.Mono

/**
 * Zap2it TV Listings Grid Service
 */
interface GridService {
    /**
     * Get Grid Listings
     *
     * @param gridParameters Grid Parameters
     * @return Reactive Mono Grid
     */
    fun getGrid(gridParameters: GridParameters): Mono<Grid>
}