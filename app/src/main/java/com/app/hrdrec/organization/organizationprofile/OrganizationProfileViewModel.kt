package com.app.hrdrec.organization.organizationprofile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.Url
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.organization.organizationprofile.addorganizationprofile.AddOrganizationProfileParams
import com.app.hrdrec.organization.organizationprofile.addorganizationprofile.UpdateOrganizationProfileParams
import com.app.hrdrec.organization.organizationprofile.get_organizationprofile_response.AllOrganizationProfileData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrganizationProfileViewModel @Inject constructor(
    val apiService: ApiInterface, val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addOrganizationProfileResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<AllOrganizationProfileData> = MutableLiveData()
    val updateorganizationProfileResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    private lateinit var callBack: CommonInterface

    fun setCallBacks(myCallback: CommonInterface) {
        callBack = myCallback
    }

    fun getAllOrganizationProfile() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getAllOrganizationProfile(orgId)
                if (response.isSuccessful) {
                    myResponseList.value = response.body()?.data
                    callBack.hideLoader()
                    Log.e("Organization Profile", "Getting org Profile" + response.body())
                } else {
                    callBack.hideLoader()
                    callBack.noListData()
                }
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
                callBack.hideLoader()
                e.printStackTrace()
                Log.e("Org profile","e"+e.printStackTrace())
            }
        }
    }

    fun addAddress(params: AddOrganizationProfileParams) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.saveOrganizationProfile(params)
                if (response.isSuccessful) {
                    Log.e("Get Organization profile", "" + response.body())
                    addOrganizationProfileResponse.value = response.body()
                    callBack.hideLoader()
                }
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
                Log.e("Get Organization profile", "")
                callBack.hideLoader()
            }
        }
    }

    fun updateAddress(params: UpdateOrganizationProfileParams) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.updateOrganizationProfile(params)
                if (response.isSuccessful) {
                    Log.e("Update Address", "" + response.body())
                    updateorganizationProfileResponse.value = response.body()
                    callBack.hideLoader()
                }
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
                Log.e("Get Organization profile", "")
                callBack.hideLoader()
            }
        }
    }
}