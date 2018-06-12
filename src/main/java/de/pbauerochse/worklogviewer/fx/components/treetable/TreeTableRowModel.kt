package de.pbauerochse.worklogviewer.fx.components.treetable

import de.pbauerochse.worklogviewer.youtrack.domain.Issue

/**
 * Describes the DataContainer for the [WorklogsTreeTableView]
 * and represents a Row in the TreeTableView component.
 *
 * A table row might be:
 * - a row containing the [Issue] and its worklogs
 * - a headline for a [de.pbauerochse.worklogviewer.youtrack.domain.GroupByCategory]
 * - a summary row which sums up the spent time
 */
interface TreeTableRowModel {

    val isSummaryRow: Boolean
    val isIssueRow: Boolean
    val isGroupByRow: Boolean

    fun getLabel(): String

}