package com.example.phi.domains

import java.util.*

data class DetailsExtractResponse(
    val amount: Int?,
    val id: String?,
    val authentication: String?,
    val tType: String?,
    val createdAt: Date?,
    val to: String?,
    val description: String?
)