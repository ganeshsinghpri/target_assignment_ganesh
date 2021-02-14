package com.target.targetcasestudy.data

import com.google.gson.annotations.SerializedName

data class Products(

    @SerializedName("products")
    var results: ArrayList<DealItem>

)