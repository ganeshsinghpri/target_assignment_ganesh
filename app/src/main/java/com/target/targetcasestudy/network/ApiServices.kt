import com.target.targetcasestudy.data.DealItem
import com.target.targetcasestudy.data.Products

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiServices {


    @GET("deals")
    fun getAllRestaurants(): Call<Products>

    @GET("deals/{id}")
    fun getProductById(
        @Path("id") id: Int
    ): Call<DealItem>


}