package com.littlelemon.bajajfinserv

class EmployeeDetails(
    val id: Int,
    val name: String,
    val designation: String,
    val skills: List<String>,
    val projects: List<ProjectDetails>
)