package de.pbauerochse.worklogviewer.connector.v2017.domain.report

import com.fasterxml.jackson.annotation.JsonProperty
import de.pbauerochse.worklogviewer.connector.v2017.toUtcEpochMillis
import java.time.LocalDate

/**
 * Defines the start- and end date
 * for the time report
 */
data class FixedTimeRange(
    private val start: LocalDate,
    private val end: LocalDate
) {

    @get:JsonProperty("\$type")
    val youtrackType : String = "jetbrains.charisma.smartui.report.common.timeRange.FixedTimeRange"

    @get:JsonProperty("from")
    val from: Long by lazy {
        start.toUtcEpochMillis()
    }

    @get:JsonProperty("to")
    val to: Long by lazy {
        end.plusDays(1).toUtcEpochMillis()
    }

}