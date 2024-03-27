package com.app.hrdrec.expenses

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.expenses.dataclasses.ExpenseReport
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import com.app.hrdrec.timesheet.request_payload.AddTimeModal

import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ExpnesesViewModel @Inject constructor(
    val apiService: ApiInterface,  val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addLocaResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<ExpenseResponse> = MutableLiveData()
    val addClientsResponse: MutableLiveData<CommonResponse> = MutableLiveData()

    val myResponseListCategory: MutableLiveData<ExpenseResponse> = MutableLiveData()
    val myResponseListCurrency: MutableLiveData<ExpenseResponse> = MutableLiveData()
    val myResponseListType: MutableLiveData<ExpenseResponse> = MutableLiveData()
    val saveExpenseResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val getExpenseResponse: MutableLiveData<ExpenseReport> = MutableLiveData()

    val deleteExpenseResponse: MutableLiveData<CommonResponse> = MutableLiveData()

    //
    @SuppressLint("SuspiciousIndentation")
    fun expenseSheetGetById(id: Int) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.expenseSheetGetById(id)
                callBack.hideLoader()
                if (response != null&& response.body() != null) {

                    if (response.isSuccessful) {
                        Log.e("expenseSheetGetById","expenseSheetGetById")
                        getExpenseResponse.value = response.body()
                        Log.e("expenseSheetGetById",getExpenseResponse.value.toString())
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


    fun deleteExpense(params: Int) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.deleteExpense(params)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    deleteExpenseResponse.value = response.body()
                    callBack.hideLoader()
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
                callBack.hideLoader()
            }
        }
    }


    @SuppressLint("SuspiciousIndentation")
    fun getReimberseListFromDate(startDate: String, endDate: String,status:String) {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val params=HashMap<String,String>()
                if(startDate != "" &&  endDate != ""){
                    params["startDate"] = startDate
                    params["endDate"] = endDate
                }
                var strstatus = "All";
                if(status != ""){
                    strstatus = status;
                }
                params["status"] = strstatus

                val data = sharedPreferences.getUserInfo()
                val userName = data?.username
                val response = userName?.let { apiService.getReimberseListFromDate(it,params) }
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


    fun SubmitExpense(model: RequestBody) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                callBack.showLoader()
                val response = apiService.saveExpanseSheet(model)

                when (response.isSuccessful) {
                    true -> {
                        callBack.hideLoader()

                        withContext(Dispatchers.Main) {

                            println("response.body()");
                            println(response.body());
                            if (response.body()!!.statusCode == 200) {

//                              //  val jsonData = jsonObject.getJSONObject("data")
                                //  val userInfoData = CommonMethods.getUserClassData(jsonData, UserInfo::class.java)

                                saveExpenseResponse.value = response.body()
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


    @SuppressLint("SuspiciousIndentation")
    fun getExpenseCurrency() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = let { apiService.getCurrency() }
                if (response != null) {
                    if (response.isSuccessful&& response.body() != null) {
                        Log.e("Data","herrrr")
                        Log.e("Data",response.body().toString())

                        myResponseListCurrency.value = response.body()
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

    @SuppressLint("SuspiciousIndentation")
    fun getAuthorizeExpenseListByDate(startDate: String, endDate: String, status: String) {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val params=HashMap<String,String>()
                if(startDate != "" &&  endDate != ""){
                    params["startDate"] = startDate
                    params["endDate"] = endDate
                }
                var strstatus = "All";
                if(status != ""){
                    strstatus = status;
                }
                params["status"] = strstatus

                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getAuthorizeExpenseListByDate(it,params) }
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
    fun getTimesheetschedulerDates(startDate: String, endDate: String,status:String) {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val params=HashMap<String,String>()
                if(startDate != "" &&  endDate != ""){
                    params["startDate"] = startDate
                    params["endDate"] = endDate
                }
                var strstatus = "All";
                if(status != ""){
                    strstatus = status;
                }
                params["status"] = strstatus

                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getExpenseListFromDates(it,params) }
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
    fun getReimberseList() {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val data = sharedPreferences.getUserInfo()
                val userName = data?.username
                val response = userName?.let { apiService.getReimberseList(it) }
                if (response != null) {
                    if (response.isSuccessful&& response.body() != null) {
                        Log.e("Data","herrrr")
                        Log.e("Data",response.body().toString())

                        myResponseList.value = response.body()
                        callBack.hideLoader()
                    }else{
                        callBack.noListData()
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


    @SuppressLint("SuspiciousIndentation")
    fun getAuthorizeExpenseList() {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getAuthorizeExpenseList(it) }
                if (response != null) {
                    if (response.isSuccessful&& response.body() != null) {
                        Log.e("Data","herrrr")
                        Log.e("Data",response.body().toString())

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


    @SuppressLint("SuspiciousIndentation")
    fun getExpenseTypeByCategory(category_id: String) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                println("here categoyr id ${category_id}")
                val id = category_id.toInt();
                val response = id.let { apiService.getExpenseType(it) }
                if (response != null) {
                    if (response.isSuccessful&& response.body() != null) {
                        Log.e("Data","herrrr")
                        Log.e("Data",response.body().toString())

                        myResponseListType.value = response.body()
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


    @SuppressLint("SuspiciousIndentation")
    fun getExpenseCategory() {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val data = sharedPreferences.getUserInfo()
                val empId = data?.organizationId
                val response = empId?.let { apiService.getExpenseCategory(it) }
                if (response != null) {
                    if (response.isSuccessful&& response.body() != null) {
                        Log.e("Data","herrrr")
                        Log.e("Data",response.body().toString())

                        myResponseListCategory.value = response.body()
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


    @SuppressLint("SuspiciousIndentation")
    fun getTimesheetscheduler() {
        viewModelScope.launch {
            try {
                callBack.showLoader()

                val data = sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                val response = empId?.let { apiService.getExpenseList(it) }
                if (response != null) {
                    if (response.isSuccessful&& response.body() != null) {
                        Log.e("Data","herrrr")
                        Log.e("Data",response.body().toString())

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




    private lateinit var callBack: CommonInterface

    fun setCallBacks(myCallback: CommonInterface) {
        callBack = myCallback
    }



}