package com.target.targetcasestudy.data

import com.google.gson.annotations.SerializedName

data class RegularPrice(

    @SerializedName("amount_in_cents")
    var amount_in_cents: Double,

    @SerializedName("currency_symbol")
    var currency_symbol: String,


    @SerializedName("display_string")
    var display_string : String 



)
