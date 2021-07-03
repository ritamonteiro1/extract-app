package com.example.phi.api

import com.example.phi.domains.AmountResponse
import com.example.phi.domains.DetailsExtract
import com.example.phi.domains.ExtractListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface DataService {

    @GET("myBalance")
    fun recoverAmount(
        @Header(TOKEN) token: String?,
    ): Call<AmountResponse?>

    @GET("myStatement/:limit/:offset")
    fun recoverExtract(
        @Header(TOKEN) token: String?,
    ): Call<ExtractListResponse?>

    @GET("myStatement/detail/:id/")
    fun recoverDetailsExtract(
        @Header(TOKEN) token: String?,
    ): Call<DetailsExtract?>

    companion object {
        const val TOKEN: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    }
}