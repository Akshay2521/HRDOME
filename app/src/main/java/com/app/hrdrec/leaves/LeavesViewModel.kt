package com.app.hrdrec.leaves

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import com.app.hrdrec.leaves.response.AddLeave
import com.app.hrdrec.timesheet.request_payload.AddTimeModal
import com.app.hrdrec.leaves.response.AllLeaveResponse
import com.app.hrdrec.leaves.response.LeaveBalanceResponse
import com.app.hrdrec.leaves.response.LeaveType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LeavesViewModel @Inject constructor(
    val apiService: ApiInterface,  val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addLocaResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<AllLeaveResponse> = MutableLiveData()
    val _allLeaveTpyeList = MutableLiveData<ArrayList<LeaveType>>()
    val mLeaveBalance: MutableLiveData<LeaveBalanceResponse> = MutableLiveData()


    val addClientsResponse: MutableLiveData<CommonResponse> = MutableLiveData()


    @SuppressLint("SuspiciousIndentation")
    fun getAllLeavesDates(startDate: String, endDate: String) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val params=HashMap<String,String>()
                params["startDate"] = startDate
                params["endDate"] = endDate
                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getAllLeavesDates(it,params) }
                callBack.hideLoader()
                if (response != null) {
                    if (response.isSuccessful && response.body() != null){
                        if (response.isSuccessful) {
                            Log.e("Data","herrrr")
                            myResponseList.value = response.body()
                            callBack.hideLoader()
                        }
                    }
                }
                else{
                    val errorBody = response?.errorBody()
                    if (errorBody != null) {
                        if(response.code==400)
                        {Log.e("resposne","ddd "+response.code)
                            callBack.noListData()
                        }
                    } else {

                    }

                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun getAllLeaves() {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getAllLeaves(it) }
                if (response != null) {
                    if (response.isSuccessful) {
                        callBack.hideLoader()
                        myResponseList.value = response.body()
                        callBack.hideLoader()
                    }
                    else{
                        callBack.hideLoader()
                    }
                }
                else{
                    val errorBody = response?.errorBody()
                    if (errorBody != null) {
                        callBack.hideLoader()
                        if(response.code==400)
                        {Log.e("resposne","ddd "+response.code)
                            callBack.noListData()
                        }
                    } else {

                    }

                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }

    fun checkLeaveBalance() {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val data = sharedPreferences.getUserInfo()
                val empId = data?.locationId
                val response = empId?.let { apiService.getLeaveBalance(it,2023) }
                callBack.hideLoader()
                if (response != null) {
                    if (response.isSuccessful) {
                        Log.e("Data","herrrr")
                        mLeaveBalance.value = response.body()
                        callBack.hideLoader()
                    }
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }


    @SuppressLint("SuspiciousIndentation")
    fun getLeaveTypes() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                Log.e("isSuccessful","www")
                val data = sharedPreferences.getUserInfo()
                val empId = data?.locationId
                val response = empId?.let {
                    apiService.getLeaveTypes(it)
                }
                if (response != null) {
                    if (response.isSuccessful) {
                        Log.e("isSuccessful","herrrr"+ (response.body()?.data?.size ?: 0))
                        _allLeaveTpyeList.value = response.body()?.data
                        callBack.hideLoader()
                    }
                    else{
                        Log.e("isSuccessful","elseee")
                        callBack.hideLoader()

                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorMessage = errorBody.string()

                            val json = JSONObject(errorMessage)
                            val message = json.getString("message")
                            callBack.onErrorMessage(message)
                        }
                        else {
                            callBack.onErrorMessage("Something went wrong")
                        }

                    }
                }
                else{
                    Log.e("isSuccessful","SSSelseee")
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }


    @SuppressLint("SuspiciousIndentation")
    fun applyLeave(model: AddLeave) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response =apiService.applyLeave(model)
                if (response != null) {
                    if (response.isSuccessful) {
                        Log.e("Data","herrrr")
                        callBack.hideLoader()
                        addClientsResponse.value = response.body()

                    }
                    else{
                        callBack.hideLoader()

                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorMessage = errorBody.string()

                            val json = JSONObject(errorMessage)
                            val message = json.getString("message")
                            callBack.onErrorMessage(message)
                        }
                        else {
                            callBack.onErrorMessage("Something went wrong")
                        }

                    }
                }
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }


    private lateinit var callBack: CommonInterface

    fun setCallBacks(myCallback: CommonInterface) {
        callBack = myCallback
    }



}