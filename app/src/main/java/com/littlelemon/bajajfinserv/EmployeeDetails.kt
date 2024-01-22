package com.littlelemon.bajajfinserv

import kotlinx.serialization.Serializable

@Serializable
class EmployeeDetails(
    val id: String,
    val name: String?,
    val designation: String?,
    val skills: List<String>,
    val projects: List<ProjectDetails>?
)