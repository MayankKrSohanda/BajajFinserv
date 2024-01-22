package com.littlelemon.bajajfinserv

import kotlinx.serialization.Serializable

@Serializable
class TeamDetails(
    val name: String?,
    val role: String
)