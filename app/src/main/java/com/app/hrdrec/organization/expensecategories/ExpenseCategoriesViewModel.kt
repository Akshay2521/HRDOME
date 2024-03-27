package com.app.hrdrec.organization.expensecategories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.Url
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.organization.expensecategories.dataclass.AllExpenseCategoriesData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseCategoriesViewModel @Inject constructor(
    val apiService: ApiInterface, val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addExpenseCategoriesResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<ArrayList<AllExpenseCategoriesData>> = MutableLiveData()
    val updateExpenseCategoriesResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    private lateinit var callBack: CommonInterface

    fun setCallBacks(myCallback: CommonInterface) {
        callBack = myCallback
    }

    fun getAllExpenseCategories() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getAllExpenseCategories(orgId)
                if (response.isSuccessful) {
                    myResponseList.value = response.body()?.data
                    Log.e("Success ExpenseCategories","ExpenseCategories"+response.body())
                    callBack.hideLoader()
                } else {
                    callBack.hideLoader()
                    callBack.noListData()
                }
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
                callBack.hideLoader()
            }
        }
    }


//    fun addExpenseCategories(params: AddExpenseCategoriesParams) {
//        viewModelScope.launch {
//            try {
//                callBack.showLoader()
//                val response = apiService.saveExpenseCategories(params)
//                if (response.isSuccessful) {
//                    addExpenseCategoriesResponse.value = response.body()
//                    callBack.hideLoader()
//                }
//            } catch (e: Exception) {
//                Log.d("main", "getPost: ${e.message}")
//                callBack.hideLoader()
//            }
//        }
//    }

//    fun updateExpenseCategories(params: UpdateExpenseCategoriesParams) {
//        viewModelScope.launch {
//            try {
//                callBack.showLoader()
//                val response = apiService.updateExpenseCategories(params)
//                if (response.isSuccessful) {
//                    updateExpenseCategoriesResponse.value = response.body()
//                    callBack.hideLoader()
//                }
//            } catch (e: Exception) {
//                Log.d("main", "getPost: ${e.message}")
//                callBack.hideLoader()
//            }
//        }
//    }

//    fun deleteExpenseCategories(params: Int) {
//        viewModelScope.launch {
//            try {
//                callBack.showLoader()
//                val response = apiService.deleteLocation(params)
//                if (response.isSuccessful) {
//                    // myResponse.value = response.body()
//                    //If need to direct access array list data so this type of getting
//                    addExpenseCategoriesResponse.value = response.body()
//                    callBack.hideLoader()
//                }
//                //SO now hear you find direct arraylist data... got it
//            } catch (e: Exception) {
//                Log.d("main", "getPost: ${e.message}")
//                callBack.hideLoader()
//            }
//        }
//    }


}