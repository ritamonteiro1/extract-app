package com.example.phi.api

import com.example.phi.domains.amount.AmountResponse
import com.example.phi.domains.details.extract.DetailsExtractResponse
import com.example.phi.domains.extract.list.ExtractListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DataService {

    @GET("myBalance")
    fun recoverAmount(
        @Header(TOKEN_HEADER) token: String,
    ): Call<AmountResponse>

    @GET("myStatement/20/0")
    fun recoverExtract(
        @Header(TOKEN_HEADER) token: String,
    ): Call<ExtractListResponse>

    @GET("myStatement/detail/{id}/")
    fun recoverDetailsExtract(
        @Path("id") id: String,
        @Header(TOKEN_HEADER) token: String,
    ): Call<DetailsExtractResponse>

    companion object {
        const val TOKEN_HEADER = "token"
        const val TOKEN_VALUE = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    }
}