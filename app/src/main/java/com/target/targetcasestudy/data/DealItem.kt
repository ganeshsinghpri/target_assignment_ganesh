package com.target.targetcasestudy.data

import com.google.gson.annotations.SerializedName

data class DealItem(

  var id: Int,

  @SerializedName("title")
  var title: String,
  var description: String,
  var price: String,
  var aisle: String,

  @SerializedName("image_url")
  var imageUrl : String,

  @SerializedName("regular_price")
  var regular_price: RegularPrice,



  var sale_price     : SalePrice




)