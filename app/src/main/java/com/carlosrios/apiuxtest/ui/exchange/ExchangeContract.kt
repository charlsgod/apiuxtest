package com.carlosrios.apiuxtest.ui.exchange

import com.carlosrios.apiuxtest.models.Rates
import com.carlosrios.apiuxtest.ui.base.BaseContract

class ExchangeContract {

    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage(error: String)
        fun deliverData(rates: Rates)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun loadData(base: String)
    }
}