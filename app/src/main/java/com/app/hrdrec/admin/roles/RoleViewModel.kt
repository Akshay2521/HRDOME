package com.app.hrdrec.admin.roles

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.Url
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import com.app.hrdrec.admin.roles.get_all_roles_response.GetAllRolesResponseData
import com.app.hrdrec.home.getallmodules.ModuleDataRoles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RoleViewModel @Inject constructor(
    val apiService: ApiInterface, private val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addLocaResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<ArrayList<GetAllRolesResponseData>> = MutableLiveData()

    // val myResponseList: MutableLiveData<List<MainData>> = MutableLiveData()
    val _moduleData = MutableLiveData<ArrayList<ModuleDataRoles>>()
    val moduleData: MutableLiveData<ArrayList<ModuleDataRoles>> get() = _moduleData

    val updateRoleResponse: MutableLiveData<CommonResponse> = MutableLiveData()

    @SuppressLint("SuspiciousIndentation")
    fun getAllRoles() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getAllRoles(orgId)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    myResponseList.value = response.body()?.data
                    callBack.hideLoader()
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }

    fun getModuleList() {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                callBack.showLoader()
                val response = apiService.gelAllModule()

                when (response.isSuccessful) {
                    true -> {
                        callBack.hideLoader()

                        withContext(Dispatchers.Main) {
                            if (response.body()!!.statusCode == 200) {

//                              //  val jsonData = jsonObject.getJSONObject("data")
                                //  val userInfoData = CommonMethods.getUserClassData(jsonData, UserInfo::class.java)

                                val data = response.body()!!.data
                                _moduleData.postValue(data)


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


    fun deleteRoles(params: Int) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.deleteLocation(params)
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

    /* fun addAddress(params: AddLocationParams) {
         viewModelScope.launch {
             try {
                 val response = apiService.saveLocation(params)
                 if(response.isSuccessful)
                 // myResponse.value = response.body()
                 //If need to direct access array list data so this type of getting
                     addLocaResponse.value = response.body()
                 //SO now hear you find direct arraylist data... got it
             } catch (e: Exception) {
                 Log.d("main", "getPost: ${e.message}")
             }
         }
     }*/

    private lateinit var callBack: CommonInterface

    fun setCallBacks(myCallback: CommonInterface) {
        callBack = myCallback
    }

    fun addRoles(name: String, description: String, path: List<Int>?, locationIds: List<Int>) {
        viewModelScope.launch {
            try {
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)


                val param =
                    path?.let { DataRolesPayload(name, description, it, locationIds, orgId) }
                Log.e("Params", "assa $param")
                callBack.showLoader()
                val response = param?.let { apiService.addRoles(it) }
                if (response!!.isSuccessful)
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

    fun updateRoles(
        name: String, description: String, path: List<Int>?, locationIds: List<Int>, roleId: Int
    ) {
        viewModelScope.launch {
            try {
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)


                val param = path?.let {
                    UpdateRolesPayload(
                        name, description, it, locationIds, orgId, roleId
                    )
                }

                callBack.showLoader()
                val response = param?.let { apiService.updateRoles(it) }
                if (response!!.isSuccessful)
                // myResponse.value = response.body()
                //If need to direct access array list data so this type of getting
                    updateRoleResponse.value = response.body()
                callBack.hideLoader()
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
            }
        }
    }
}