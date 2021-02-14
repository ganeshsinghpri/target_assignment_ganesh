import androidx.lifecycle.MutableLiveData
import com.target.targetcasestudy.data.DealItem
import com.target.targetcasestudy.data.Products
import com.target.targetcasestudy.interfaces.NetworkResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository private constructor() {

    private lateinit var mCallback: NetworkResponseCallback
    private var mDealList: MutableLiveData<List<DealItem>> =
        MutableLiveData<List<DealItem>>().apply { value = emptyList() }


    private var mDealItem = MutableLiveData<DealItem>()

    companion object {
        private var mInstance: ProductRepository? = null
        fun getInstance(): ProductRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = ProductRepository()
                }
            }
            return mInstance!!
        }
    }


    private lateinit var mDealListResultCall: Call<Products>

    private lateinit var mDealItemDataCall: Call<DealItem>

    fun getDeals(
        callback: NetworkResponseCallback,
        forceFetch: Boolean
    ): MutableLiveData<List<DealItem>> {
        mCallback = callback
        if (mDealList.value!!.isNotEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mDealList
        }
        mDealListResultCall = RestClient.getInstance().getApiService().getAllRestaurants()
        mDealListResultCall.enqueue(object : Callback<Products> {

            override fun onResponse(call: Call<Products>, response: Response<Products>) {

                if (response.code() == 200 && response.isSuccessful) {
                    mDealList.value = response.body()?.results
                    mCallback.onNetworkSuccess()
                } else {
                    mDealList.value = emptyList()
                    mCallback.onNetworkFailure(Throwable("Unable to Fetch now, Please try again later "))
                }


            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                mDealList.value = emptyList()
                mCallback.onNetworkFailure(t)
            }

        })
        return mDealList
    }


    fun getProductDetails(
        id: Int,
        callback: NetworkResponseCallback,
        forceFetch: Boolean
    ): MutableLiveData<DealItem> {
        mCallback = callback
        if (mDealItem.value != null && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mDealItem
        }

        mDealItemDataCall = RestClient.getInstance().getApiService().getProductById(id)
        mDealItemDataCall.enqueue(object : Callback<DealItem> {

            override fun onResponse(call: Call<DealItem>, response: Response<DealItem>) {
                if (response.code() == 200 && response.isSuccessful) {
                    mDealItem.value = response.body()
                    mCallback.onNetworkSuccess()
                } else {
                    mDealItem.value = null
                    mCallback.onNetworkFailure(Throwable("Unable to Fetch now, Please try again later "))
                }
            }

            override fun onFailure(call: Call<DealItem>, t: Throwable) {

                mDealItem.value = null
                mCallback.onNetworkFailure(t)
            }

        })
        return mDealItem
    }
}