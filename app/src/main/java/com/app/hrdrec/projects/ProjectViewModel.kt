package com.app.hrdrec.projects

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.Url
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.projects.clients_response.ClientListResponse
import com.app.hrdrec.projects.employee_response.EmployeeListResponse
import com.app.hrdrec.projects.manager_response.ManagerListResponse
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    val apiService: ApiInterface,
    val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addLocaResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<ArrayList<ProjectData>> = MutableLiveData()
    val projectResourceList: MutableLiveData<ArrayList<ProjectData>> = MutableLiveData()
    // val myResponseList: MutableLiveData<List<MainData>> = MutableLiveData()
    val allClientsResponseList: MutableLiveData<ArrayList<ClientListResponse>> =
        MutableLiveData()


    val updateProjectResponse: MutableLiveData<CommonResponse> = MutableLiveData()


    val allEmployeesResponseList: MutableLiveData<ArrayList<EmployeeListResponse>> =
        MutableLiveData()

    val allManagerResponseList: MutableLiveData<ArrayList<ManagerListResponse>> =
        MutableLiveData()
    val updateProjectResourcesResponse: MutableLiveData<CommonResponse> = MutableLiveData()

    @SuppressLint("SuspiciousIndentation")
    fun getProjectRoles() {
        viewModelScope.launch {
            try {
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getAllProjects(orgId)
                if (response.isSuccessful) {
                    Log.e("Get Project roles", "" + response.body()?.data)
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    myResponseList.value = response.body()?.data
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                Log.d("Get Projects roles", "getPost: ${e.message}")
                Log.e("Get Projects roles", "")
            }
        }
    }

//    fun addLeavetype(param: AddLeavetypePayload) {
//        viewModelScope.launch {
//            try {
//                callBack.showLoader()
//                val response = apiService.addleavetype(param)
//                if (response.isSuccessful) {
//                    addLocaResponse.value = response.body()
//                    callBack.hideLoader()
//                    Log.e("addLeave Type", "checking add Leave type"+response.body())
//                }
//                //SO now hear you find direct arraylist data... got it
//            } catch (e: Exception) {
//                callBack.hideLoader()
//                Log.e("addLeave Type1", "checking add Leave type")
//            }
//        }
//    }

    fun addProject(params: AddProjectPayload) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.addproject(params)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    addLocaResponse.value = response.body()
                    callBack.hideLoader()
                    //SO now hear you find direct arraylist data.
                    // .. got it
                }
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
                callBack.hideLoader()
            }
        }
    }


    fun deleteProject(params: Int) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.deleteprojects(params)
                if (response.isSuccessful)
                    addLocaResponse.value = response.body()
                callBack.hideLoader()
                Log.e("deleteProject", "checking add Project"+response.body())
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.e("deleteProject", "checking add Project")
            }
        }
    }

//    fun UpdateLeavetype(param: UpdateLeavetypePayload) {
//        viewModelScope.launch {
//            try {
//                callBack.showLoader()
//                val response = apiService.updateleavetype(param)
//                if (response.isSuccessful) {
//                    updateLeavetypeResponse.value = response.body()
//                    callBack.hideLoader()
//                    Log.e("UpdateLeavetype", "checking add Leave type"+response.body())
//                }
//                //SO now hear you find direct arraylist data... got it
//            } catch (e: Exception) {
//                callBack.hideLoader()
//                Log.e("UpdateLeavetype", "checking add Leave type")
//            }
//        }
//    }

    fun UpdateProject(params: UpdateProjectPayload) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.updateproject(params)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    updateProjectResponse.value = response.body()
                    callBack.hideLoader()
                    //SO now hear you find direct arraylist data.
                    // .. got it
                }
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
                callBack.hideLoader()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun getProjectResourceRoles() {
        viewModelScope.launch {
            try {
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getAllProjectResource(orgId)
                if (response.isSuccessful) {
                    Log.e("Data", "" + response.body()?.data)
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    projectResourceList.value = response.body()?.data
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }

    fun getAllClients() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getClientsByOrg1(orgId)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    allClientsResponseList.value = response.body()?.data
                    callBack.hideLoader()
                    Log.e("getAllClients", "checking add Client"+response.body())
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.e("getAllClients", "checking add Client")
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }

    fun getAllEmployees() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getemployeeByOrg1(orgId)
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

    fun getAllManagers() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getManagerByOrg(orgId)
                if (response.isSuccessful) {
                    Log.e("manager", "sssfsff")
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    allManagerResponseList.value = response.body()?.data
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

    fun addProjectResource(param: AddProjectResourcesPayload) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.addProjectResource(param)
                if (response.isSuccessful) {
                    addLocaResponse.value = response.body()
                    callBack.hideLoader()
                }
                else
                {
                    callBack.hideLoader()
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
            }
        }
    }
    fun UpdateProjectResources(param: UpdateProjectResourcesPayload) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.updateProjectResource(param)
                if (response.isSuccessful) {
                    updateProjectResourcesResponse.value = response.body()
                    callBack.hideLoader()
                }
                else
                {
                    callBack.hideLoader()
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
            }
        }
    }

}