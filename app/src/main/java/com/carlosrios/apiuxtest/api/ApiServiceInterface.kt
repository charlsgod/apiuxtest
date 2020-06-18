package com.carlosrios.apiuxtest.api

import com.carlosrios.apiuxtest.models.ExchangeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {

    @GET("latest/")
    fun getExchangeList(@Query("base") base: String): Observable<ExchangeResponse>

}