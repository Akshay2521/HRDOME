package com.app.hrdrec.admin.users

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.Url
import com.app.hrdrec.admin.users.employes_response.EmployeeListResponse
import com.app.hrdrec.admin.users.user_models.UserData
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import com.app.hrdrec.admin.roles.get_all_roles_response.GetAllRolesResponseData
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val apiService: ApiInterface,
    val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addLocaResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<ArrayList<UserData>> = MutableLiveData()
    // val myResponseList: MutableLiveData<List<MainData>> = MutableLiveData()

    val allRolesResponseList: MutableLiveData<ArrayList<GetAllRolesResponseData>> =
        MutableLiveData()
    val allEmployeesResponseList: MutableLiveData<ArrayList<EmployeeListResponse>> =
        MutableLiveData()

    val updateUserResponse: MutableLiveData<CommonResponse> = MutableLiveData()

    @SuppressLint("SuspiciousIndentation")
    fun getUserRoles() {
        viewModelScope.launch {
            try {
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getAllUsers(orgId)
                if (response.isSuccessful) {
                    Log.e("Data", "" + response.body()?.data)
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    myResponseList.value = response.body()?.data
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }

    fun addUser(param: AddUserPayload) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.addUser(param)
                if (response.isSuccessful) {
                    addLocaResponse.value = response.body()
                    callBack.hideLoader()
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
            }
        }
    }


    fun deleteUser(params: Int) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.deleteusers(params)
                if (response.isSuccessful)
                // myResponse.value = response.body()
                //If need to direct access array list data so this type of getting
                    addLocaResponse.value = response.body()
                callBack.hideLoader()
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
            }
        }
    }

    fun UpdateUser(param: UpdateUserPayload) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.updateUser(param)
                if (response.isSuccessful) {
                    updateUserResponse.value = response.body()
                    callBack.hideLoader()
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
            }
        }
    }

    fun getAllRoles() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getAllRoles(orgId)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    allRolesResponseList.value = response.body()?.data
                    callBack.hideLoader()
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }


    fun getAllEmployees() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getEmployeeByOrg(orgId)
                if (response.isSuccessful) {
                    Log.e("emploee", "sssfsff")
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    allEmployeesResponseList.value = response.body()?.data
                    callBack.hideLoader()
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