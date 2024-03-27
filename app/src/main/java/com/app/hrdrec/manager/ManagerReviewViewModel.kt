package com.app.hrdrec.manager

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.home.getallmodules.ModuleDataRoles
import com.app.hrdrec.manager.all_leave_submitted.GetLeaveManagerModel
import com.app.hrdrec.manager.response.all_submitted_response.GetSubmitedResponse
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.timesheet.request_payload.AddTimeModal
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ManagerReviewViewModel @Inject constructor(
    val apiService: ApiInterface,  val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addLocaResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<GetSubmitedResponse> = MutableLiveData()

    val mySavedResponse: MutableLiveData<AddTimeModal> = MutableLiveData()
    val _moduleData = MutableLiveData<ArrayList<ModuleDataRoles>>()

    val updateRoleResponse: MutableLiveData<CommonResponse> = MutableLiveData()

    val addClientsResponse: MutableLiveData<CommonResponse> = MutableLiveData()


    val getAllLeaveList: MutableLiveData<GetLeaveManagerModel> = MutableLiveData()

     fun getSavedData(intExtra: Int) {


         viewModelScope.launch {
             try {
                 callBack.showLoader()

//                 val data = sharedPreferences.getUserInfo()
//                 val empId = data?.employeeId
                 val response = intExtra.let { apiService.getSavedTimesheetById(intExtra) }
                 if (response != null) {
                     if (response.isSuccessful) {
                         Log.e("getSavedData","response.body()"+response.body())
                         mySavedResponse.value = response.body()?.data
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
    fun getAllLeaveManagerDates(startDate: String, endDate: String) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val params=HashMap<String,String>()
                params["startDate"] = startDate
                params["endDate"] = endDate
                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getAllLeaveManagerDates(it,params) }
                callBack.hideLoader()
                if (response != null) {
                    if (response.isSuccessful) {
                        Log.e("Data","herrrr")
                        getAllLeaveList.value = response.body()
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
    fun getAllLeaveManager() {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getAllLeaveManager(it) }
                if (response != null) {
                    if (response.isSuccessful) {
                        Log.e("Data","herrrr")
                        getAllLeaveList.value = response.body()
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


    fun getSubmittedByDates(startDate: String, endDate: String) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val params=HashMap<String,String>()
                params["startDate"] = startDate
                params["endDate"] = endDate
                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getSubmittedByManagerByDates(it,params) }
                callBack.hideLoader()
                if (response != null) {
                    if (response.isSuccessful) {
                        Log.e("Data","herrrr")
                        myResponseList.value = response.body()
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
    fun getSubmittedByManager() {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getSubmittedByManager(it) }
                if (response != null) {
                    if (response.isSuccessful) {
                        Log.e("Data","herrrr")
                        myResponseList.value = response.body()
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









    fun approvRejectRecord(from: String, id: Int, projectId: Int,remarks: String) {

        viewModelScope.launch {

            try {

                val response : Response<CommonResponse>
                callBack.showLoader()
                if(from == "approve")
                    response= apiService.approveRecord(id,projectId,remarks)
                else
                response = apiService.rejectRecord(id,projectId,remarks)

                when (response.isSuccessful) {
                    true -> {
                        callBack.hideLoader()

                        withContext(Dispatchers.Main) {
                            if (response.body()!!.statusCode == 200) {

//                              //  val jsonData = jsonObject.getJSONObject("data")
                                //  val userInfoData = CommonMethods.getUserClassData(jsonData, UserInfo::class.java)

                                addClientsResponse.value = response.body()
                                callBack.hideLoader()


                            } else callBack.onErrorMessage(response.body()!!.errorMessage!!)
                        }

                    }

                    else -> {
                        withContext(Dispatchers.Main) {
                            callBack.hideLoader()
                            callBack.onErrorMessage("Something went wrong")

                            //CommonKotlin.hideProgress()

                        }
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

    }




    fun approvRejectLeave(from: String, id: Int,remarks: String) {

        viewModelScope.launch {

            try {

                val response : Response<CommonResponse>
                callBack.showLoader()
                if(from == "approve")
                    response= apiService.approveLeave(id,remarks)
                else
                    response = apiService.rejectLeave(id,remarks)

                when (response.isSuccessful) {
                    true -> {
                        callBack.hideLoader()

                        withContext(Dispatchers.Main) {
                            if (response.body()!!.statusCode == 200) {

//                              //  val jsonData = jsonObject.getJSONObject("data")
                                //  val userInfoData = CommonMethods.getUserClassData(jsonData, UserInfo::class.java)

                                addClientsResponse.value = response.body()
                                callBack.hideLoader()


                            } else callBack.onErrorMessage(response.body()!!.errorMessage!!)
                        }

                    }

                    else -> {
                        withContext(Dispatchers.Main) {
                            callBack.hideLoader()
                            callBack.onErrorMessage("Something went wrong")

                            //CommonKotlin.hideProgress()

                        }
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

    }









    private lateinit var callBack: CommonInterface

    fun setCallBacks(myCallback: CommonInterface) {
        callBack = myCallback
    }




}