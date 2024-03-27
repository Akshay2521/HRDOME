package com.app.hrdrec.timesheet

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import com.app.hrdrec.home.getallmodules.ModuleDataRoles
import com.app.hrdrec.timesheet.request_payload.AddTimeModal
import com.app.hrdrec.timesheet.response.TimeScheduleResponse
import com.app.hrdrec.timesheet.response.project_response.ProjectEmployee
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class TimeSheetViewModel @Inject constructor(
    val apiService: ApiInterface,  val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addLocaResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<TimeScheduleResponse> = MutableLiveData()

    val mySavedResponse: MutableLiveData<AddTimeModal> = MutableLiveData()

    val projectEmployeeList: MutableLiveData<ArrayList<ProjectEmployee>> = MutableLiveData()
    val _moduleData = MutableLiveData<ArrayList<ModuleDataRoles>>()

    val updateRoleResponse: MutableLiveData<CommonResponse> = MutableLiveData()

    val addClientsResponse: MutableLiveData<CommonResponse> = MutableLiveData()




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
    fun getTimesheetschedulerDates(startDate: String, endDate: String) {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val params=HashMap<String,String>()
                params["startDate"] = startDate
                params["endDate"] = endDate
                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getTimesheetschedulerDates(it,params) }
                callBack.hideLoader()
                if (response != null&& response.body() != null) {

                    if (response.isSuccessful) {
                        Log.e("Data","herrrr")
                        myResponseList.value = response.body()
                        callBack.hideLoader()
                    }
                    else{
                        Log.e("resposne","ccccc")
                        callBack.noListData()
                    }
                }
                else{
                    val errorBody = response?.errorBody()
                    if (errorBody != null) {
                        if(response.code()==400)
                        {Log.e("resposne","ddd "+response.code())
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
    fun getTimesheetscheduler() {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getTimesheetscheduler(it) }
                if (response != null) {
                    if (response.isSuccessful&& response.body() != null) {
                        Log.e("Data","herrrr")
                        myResponseList.value = response.body()
                        callBack.hideLoader()
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





     fun getAllProjects() {


          viewModelScope.launch {
              try {
                  callBack.showLoader()

                   val data = sharedPreferences.getUserInfo()
                   val organizationId = data?.employeeId
                  val response = apiService.getprojects(organizationId!!)
                  if (response != null) {
                      if (response.isSuccessful) {
                          Log.e("getSavedData","response.body()"+response.body())
                          projectEmployeeList.value = response.body()?.data
                          callBack.hideLoader()
                      }
                  }
              } catch (e: Exception) {
                  callBack.hideLoader()
                  Log.d("main", "getPost: ${e.message}")
              }
          }
      }


    @SuppressLint("SuspiciousIndentation")
    fun addTimesheetscheduler(model: AddTimeModal) {
        viewModelScope.launch {
            try {
                callBack.showLoader()

              //  val response =apiService.addTimesheetscheduler(model)
                val response =apiService.saveTimeSheet(model)
                callBack.hideLoader()
                if (response.body() != null) {

                    if (response.isSuccessful) {
                        Log.e("Data","herrrr")
                        addClientsResponse.value = response.body()
                        callBack.hideLoader()
                    }
                }
                else{
                    callBack.hideLoader()
Log.e("sssss","sss"+response)
                      //  val errorBody = response.errorBody()
Log.e("sss","errrorrr "+response)
                        if (response.code()==500) {
Log.e("sasas","ss"+response.message())
                            Log.e("sasas","noodd"+response.body())
                            Log.e("sasas","errr"+response.errorBody())
                                //val json=JSONObject(errorBody.toString())
                            callBack.onErrorMessage("A TimeSheet with the selected weekend Date already exists")
                            }
                    else{
                            callBack.onErrorMessage("Unexpected error occurred")
                    }




                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }


    fun submitTimeSheet(model: AddTimeModal) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                callBack.showLoader()
                val response = apiService.saveTimeSheet(model)

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