package com.vintrace.winestatisticservice.service

import com.vintrace.winestatisticservice.controller.BreakDown
import com.vintrace.winestatisticservice.controller.Statistics
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.kotlin.test.expectError

import reactor.test.StepVerifier
import java.util.*

internal class StatisticServiceTest {

    private val staticService :StatisticService = StatisticService();

    @Test
    fun byYearSuccess() {
        StepVerifier.create(staticService.byYear("11YVCHAR001"))
                .expectNext(Statistics("year", Arrays.asList(
                        BreakDown(85.0, "2011"),
                        BreakDown(46.0, "2010")
                ))).verifyComplete()
    }

    @Test
    fun byYearInvalidLotCode() {
        StepVerifier.create(staticService.byYear("11YVCHAR05"))
            .expectErrorMatches{ it is ResponseStatusException
                    && it.status == HttpStatus.BAD_REQUEST
            }
            .verify()
    }

    @Test
    fun byVariety() {
        StepVerifier.create(staticService.byVariety("11YVCHAR001"))
            .expectNext(Statistics("variety", Arrays.asList(
                BreakDown(121.0, "Chardonnay"),
                BreakDown(10.0, "Pinot Noir")
            ))).verifyComplete()
    }

    @Test
    fun byRegion() {
        StepVerifier.create(staticService.byRegion("11YVCHAR001"))
            .expectNext(Statistics("region", Arrays.asList(
                BreakDown(100.0, "Yarra Valley"),
                BreakDown(26.0, "Macedon"),
                BreakDown(5.0, "Mornington")
            ))).verifyComplete()
    }

    @Test
    fun byYearVarirty() {
        StepVerifier.create(staticService.byYearVariety("11YVCHAR001"))
            .expectNext(Statistics("year-variety", Arrays.asList(
                BreakDown(80.0, Arrays.asList(2011, "Chardonnay").toString()),
                BreakDown(41.0, Arrays.asList(2010, "Chardonnay").toString()),
                BreakDown(5.0, Arrays.asList(2011, "Pinot Noir").toString()),
                BreakDown(5.0, Arrays.asList(2010, "Pinot Noir").toString())
            ))).verifyComplete()
    }
}