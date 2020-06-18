package com.carlosrios.apiuxtest.api

import com.carlosrios.apiuxtest.models.ExchangeResponse
import io.reactivex.Observable
import retrofit2.http.Path

interface RestApiHelper {

    fun getExchangeList(base: String): Observable<ExchangeResponse>
}