package com.example.phi.domains.details.extract


data class DetailsExtract(
    val amount: Int,
    val id: String,
    val authentication: String,
    val tType: String,
    val createdAt: String,
    val to: String,
    val description: String
)
