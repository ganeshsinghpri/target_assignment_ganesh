import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.target.targetcasestudy.data.DealItem

import com.target.targetcasestudy.helper.NetworkHelper
import com.target.targetcasestudy.interfaces.NetworkResponseCallback


class DealItemViewModel(private val app: Application) : AndroidViewModel(app) {


    val mNavigateToNextScreen = MutableLiveData(false)

    val mDealDetailItem = MutableLiveData<DealItem>()

    fun sendDealDetailItem(text: DealItem) {
        mDealDetailItem.value = text

    }


    private var mList: MutableLiveData<List<DealItem>> =
        MutableLiveData<List<DealItem>>().apply { value = emptyList() }

    private var mDealItem = MutableLiveData<DealItem>()


    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = ProductRepository.getInstance()

    fun fetchDeals(forceFetch: Boolean): MutableLiveData<List<DealItem>> {
        if (NetworkHelper.isNetworkAvailable(app.baseContext)) {
            mShowProgressBar.value = true
            mList = mRepository.getDeals(object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
            }, forceFetch)
        } else {
            mShowNetworkError.value = true
        }
        return mList
    }


    fun getDealDetails(id: Int, forceFetch: Boolean): MutableLiveData<DealItem> {


        if (NetworkHelper.isNetworkAvailable(app.baseContext)) {
            mShowProgressBar.value = true
            mNavigateToNextScreen.value = false
            mDealItem = mRepository.getProductDetails(id, object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                    mNavigateToNextScreen.value = true
                }
            }, forceFetch)
        } else {
            mShowNetworkError.value = true
            mNavigateToNextScreen.value = false
        }
        return mDealItem
    }


}