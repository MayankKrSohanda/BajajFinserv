package com.littlelemon.bajajfinserv

import kotlinx.serialization.Serializable

@Serializable
class ProjectDetails(
    val name: String,
    val description: String?,
    val team: List<TeamDetails>,
    val tasks: List<TaskDetails>? = null
)