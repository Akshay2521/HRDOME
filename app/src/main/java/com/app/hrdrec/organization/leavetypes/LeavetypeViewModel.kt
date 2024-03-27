package com.app.hrdrec.organization.leavetypes

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.Url
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.organization.clients.addclients.AddClientsParams
import com.app.hrdrec.organization.clients.addclients.UpdateClientsParams
import com.app.hrdrec.organization.leavetypes.leavetype_models.LeavetypeData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeavetypeViewModel @Inject constructor(
    val apiService: ApiInterface,
    val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addLocaResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<ArrayList<LeavetypeData>> = MutableLiveData()
    // val myResponseList: MutableLiveData<List<MainData>> = MutableLiveData()
    val allLocationsResponseList: MutableLiveData<ArrayList<com.app.hrdrec.organization.leavetypes.locations_response.LocationListResponse>> =
        MutableLiveData()

    val updateLeavetypeResponse: MutableLiveData<CommonResponse> = MutableLiveData()

    @SuppressLint("SuspiciousIndentation")
    fun getLeavetypeRoles() {
        viewModelScope.launch {
            try {
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getAllLeavetypes(orgId)
                if (response.isSuccessful) {
                    Log.e("Get leave type roles", "" + response.body()?.data)
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    myResponseList.value = response.body()?.data
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                Log.d("Get leave type roles", "getPost: ${e.message}")
                Log.e("Get leave type roles", "")
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

    fun addLeavetype(params: AddLeavetypePayload) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.addleavetype(params)
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


    fun deleteLeavetype(params: Int) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.deleteleavetypes(params)
                if (response.isSuccessful)
                    addLocaResponse.value = response.body()
                callBack.hideLoader()
                Log.e("deleteLeavetype", "checking add Leave type"+response.body())
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.e("deleteLeavetype", "checking add Leave type")
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

    fun UpdateLeavetype(params: UpdateLeavetypePayload) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.updateleavetype(params)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    updateLeavetypeResponse.value = response.body()
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

    fun getAllLocations() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getLocationByOrg1(orgId)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    allLocationsResponseList.value = response.body()?.data
                    callBack.hideLoader()
                    Log.e("getAllLocations", "checking add Leave type"+response.body())
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                callBack.hideLoader()
                Log.e("getAllLocations", "checking add Leave type")
                Log.d("main", "getPost: ${e.message}")
            }
        }
    }

    private lateinit var callBack: CommonInterface
    fun setCallBacks(myCallback: CommonInterface) {
        callBack = myCallback
    }

}
