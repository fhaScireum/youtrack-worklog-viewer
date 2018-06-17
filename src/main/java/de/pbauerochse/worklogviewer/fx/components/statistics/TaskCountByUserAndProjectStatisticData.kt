package de.pbauerochse.worklogviewer.fx.components.statistics

import de.pbauerochse.worklogviewer.youtrack.domain.Issue
import de.pbauerochse.worklogviewer.youtrack.domain.WorklogItem

/**
 * Groups the found issues by user and
 * then by project and displays cumulative
 * data for each project
 */
class TaskCountByUserAndProjectStatisticData(issues : List<Issue>) {

    val userStatistics : List<UserStatistic> = extractStatistics(issues)

    private fun extractStatistics(issues: List<Issue>): List<UserStatistic> {
        val worklogsGroupedByUserDisplayname = issues
            .flatMap { it.worklogItems }
            .groupBy { it.userDisplayname }
            .toSortedMap()

        return worklogsGroupedByUserDisplayname.map {
            val userDisplayname = it.key
            val worklogsForThisUser = it.value

            val projectStatistics = getProjectStatistics(worklogsForThisUser)

            UserStatistic(userDisplayname, projectStatistics)
        }
    }

    private fun getProjectStatistics(worklogsForUser: List<WorklogItem>): List<ProjectStatistic> {
        val distinctProjects = worklogsForUser
            .map { it.issue.project }
            .distinct()
            .sorted()

        val totalTimeSpentInTimerange = worklogsForUser.map { it.durationInMinutes }.sum()

        val worklogsByProject = worklogsForUser.groupBy { it.issue.project }
        val issuesByProject = worklogsForUser
            .map { it.issue }
            .distinct()
            .groupBy { it.project }

        return distinctProjects.map {
            val totalTimeSpentInMinutesOnThisProject = worklogsByProject[it]!!.map { it.durationInMinutes }.sum()
            val numberOfWorkedIssuesInThisProject = issuesByProject[it]!!.count()
            val percentOfTimeSpentOnThisProject = totalTimeSpentInMinutesOnThisProject.toDouble() / totalTimeSpentInTimerange.toDouble()
            ProjectStatistic(it, percentOfTimeSpentOnThisProject, numberOfWorkedIssuesInThisProject, totalTimeSpentInMinutesOnThisProject)
        }
    }


}