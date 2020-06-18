package com.carlosrios.apiuxtest.api

import com.carlosrios.apiuxtest.models.ExchangeResponse
import io.reactivex.Observable

class RestApiManager(var apiService : ApiServiceInterface):RestApiHelper {

    override fun getExchangeList(base: String): Observable<ExchangeResponse> {
        return apiService.getExchangeList(base)
    }


}