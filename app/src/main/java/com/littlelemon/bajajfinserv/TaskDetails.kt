package com.littlelemon.bajajfinserv

import kotlinx.serialization.Serializable

@Serializable
class TaskDetails(
    val id: String,
    val name: String?,
    val status: String?
)