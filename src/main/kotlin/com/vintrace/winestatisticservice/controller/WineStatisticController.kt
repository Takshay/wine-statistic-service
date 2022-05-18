package com.vintrace.winestatisticservice.controller

import com.vintrace.winestatisticservice.service.StatisticService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/breakdown")
class WineStatisticController {

    @Autowired
    lateinit var statisticService:StatisticService

    @GetMapping(value = ["/year/{lotCode}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun year(@PathVariable lotCode: String): Mono<ResponseEntity<Statistics>> {
        return statisticService.byYear(lotCode)
                .flatMap { statistic -> Mono.just(ResponseEntity.ok(statistic)) }
    }

    @GetMapping(value = ["/variety/{lotCode}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun variety(@PathVariable lotCode: String): Mono<ResponseEntity<Statistics>> {
        return statisticService.byVariety(lotCode)
                .flatMap { statistic -> Mono.just(ResponseEntity.ok(statistic)) }
    }

    @GetMapping(value = ["/region/{lotCode}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun region(@PathVariable lotCode: String): Mono<ResponseEntity<Statistics>> {
        return statisticService.byRegion(lotCode)
                .flatMap { statistic -> Mono.just(ResponseEntity.ok(statistic)) }
    }

    @GetMapping(value = ["/year-variety/{lotCode}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun yearVariety(@PathVariable lotCode: String): Mono<ResponseEntity<Statistics>> {
        return statisticService.byYearVariety(lotCode)
                .flatMap { statistic -> Mono.just(ResponseEntity.ok(statistic)) }
    }
}

data class Statistics (val breakDownType: String,
                      val breakDown: List<BreakDown> )

data class BreakDown(val percentage: Double,
                     val key: String)
