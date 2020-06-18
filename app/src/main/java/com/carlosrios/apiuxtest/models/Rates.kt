package com.carlosrios.apiuxtest.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rates(

	@field:SerializedName("MXN")
	val mXN: Double? = null,

	@field:SerializedName("GBP")
	val gBP: Double? = null,

	@field:SerializedName("CAD")
	val cAD: Double? = null
) : Parcelable