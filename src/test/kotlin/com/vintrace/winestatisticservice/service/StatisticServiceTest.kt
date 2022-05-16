package com.vintrace.winestatisticservice.service

import com.vintrace.winestatisticservice.controller.BreakDown
import com.vintrace.winestatisticservice.controller.Statistics
import org.junit.jupiter.api.Test

import reactor.test.StepVerifier
import java.util.*

internal class StatisticServiceTest {

    private val staticService :StatisticService = StatisticService();

    @Test
    fun byYearSuccess() {
        StepVerifier.create(staticService.byYear("11YVCHAR001"))
                .expectNext(Statistics("year", Arrays.asList(
                        BreakDown(85.0, "2011"),
                        BreakDown(26.0, "2010")
                ))).verifyComplete()
    }

    /*@Test
    fun byYearInvalidLotCode() {
        StepVerifier.create(staticService.byYear("11YVCHAR05"))
                .expectNextMatches(ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid lotCode")))
                .verify()
    }*/

    @Test
    fun byVariety() {
    }

    @Test
    fun byRegion() {
    }

    @Test
    fun byYearVarirty() {
    }
}