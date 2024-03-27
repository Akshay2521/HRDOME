package com.app.hrdrec.organization.clients

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.Url
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.organization.clients.addclients.AddClientsParams
import com.app.hrdrec.organization.clients.addclients.UpdateClientsParams
import com.app.hrdrec.organization.clients.get_clients_response.AllClientsData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    val apiService: ApiInterface,
    val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addClientsResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<ArrayList<AllClientsData>> = MutableLiveData()

    // val myResponseList: MutableLiveData<List<MainData>> = MutableLiveData()
    val updateClientsResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    private lateinit var callBack: CommonInterface

    fun setCallBacks(myCallback: CommonInterface) {
        callBack = myCallback
    }

    fun getAllClients() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getAllClients(orgId)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    myResponseList.value = response.body()?.data
                    callBack.hideLoader()
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
                callBack.hideLoader()
            }
        }
    }

    fun addAddress(params: AddClientsParams) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.saveClients(params)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    addClientsResponse.value = response.body()
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

    fun updateAddress(params: UpdateClientsParams) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.updateClients(params)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    updateClientsResponse.value = response.body()
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

    fun deleteAddress(params: Int) {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val response = apiService.deleteClients(params)
                if (response.isSuccessful) {
                    // myResponse.value = response.body()
                    //If need to direct access array list data so this type of getting
                    addClientsResponse.value = response.body()
                    callBack.hideLoader()
                }
                //SO now hear you find direct arraylist data... got it
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
                callBack.hideLoader()
            }
        }
    }
}