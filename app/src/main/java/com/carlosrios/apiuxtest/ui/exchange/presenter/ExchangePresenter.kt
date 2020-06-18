package com.carlosrios.apiuxtest.ui.exchange.presenter

import com.carlosrios.apiuxtest.api.ApiServiceInterface
import com.carlosrios.apiuxtest.api.RestApiManager
import com.carlosrios.apiuxtest.models.ExchangeResponse
import com.carlosrios.apiuxtest.ui.exchange.ExchangeContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ExchangePresenter(private val restApiManager: RestApiManager) : ExchangeContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var subscribe: Disposable
    private lateinit var view: ExchangeContract.View

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: ExchangeContract.View) {
        this.view = view
    }

    override fun loadData(base: String) {
        view.showProgress(true)

        subscribe = restApiManager.getExchangeList(base).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: ExchangeResponse? ->
                view.showProgress(false)
                response?.rates?.let { view.deliverData(it) }
            }, { error ->
                view.showProgress(false)
                view.showErrorMessage(error.localizedMessage!!)
            })

        subscriptions.add(subscribe)
    }
}