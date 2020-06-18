package com.carlosrios.apiuxtest.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExchangeResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("rates")
	val rates: Rates? = null,

	@field:SerializedName("base")
	val base: String? = null
) : Parcelable
