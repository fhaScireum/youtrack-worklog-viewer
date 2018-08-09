package de.pbauerochse.worklogviewer.connector.v2017.domain.issuedetails

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class IssueField @JsonCreator constructor(
    @JsonProperty("name") val name : String,
    @JsonProperty("value") val value : String
) {

    val isResolvedField : Boolean by lazy {
        name == "resolved"
    }

}