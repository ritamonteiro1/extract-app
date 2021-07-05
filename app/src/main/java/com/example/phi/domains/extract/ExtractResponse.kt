package com.example.phi.domains.extract

import java.util.*


data class ExtractResponse(
    val createdAt: Date?,
    val id: String?,
    val amount: Int?,
    val to: String?,
    val description: String?,
    val tType: String?,
    val from: String?,
    val bankName: String?
)