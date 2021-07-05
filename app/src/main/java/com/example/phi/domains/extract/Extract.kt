package com.example.phi.domains.extract


data class Extract(
    val createdAt: String,
    val id: String,
    val amount: Int,
    val to: String,
    val description: String,
    val tType: String,
    val from: String,
    val bankName: String
)
