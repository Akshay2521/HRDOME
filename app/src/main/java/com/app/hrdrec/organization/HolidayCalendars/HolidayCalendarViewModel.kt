package com.app.hrdrec.organization.HolidayCalendars

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hrdrec.Url
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.organization.HolidayCalendars.dataclass.AllHolidayCalendarData
import com.app.hrdrec.organization.locations.addlocation.AddLocationParams
import com.app.hrdrec.organization.locations.addlocation.UpdateLocationParams
import com.app.hrdrec.organization.locations.get_location_response.AllLocationData
import com.app.hrdrec.organization.locations.get_location_response.CountryListModel
import com.app.hrdrec.organization.locations.state_response.StateDTOs
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HolidayCalendarViewModel @Inject constructor(
    val apiService: ApiInterface, val sharedPreferences: SharedPrefUtils
) : ViewModel() {

    val addHolidayCalendarResponse: MutableLiveData<CommonResponse> = MutableLiveData()
    val myResponseList: MutableLiveData<ArrayList<AllHolidayCalendarData>> = MutableLiveData()

    private lateinit var callBack: CommonInterface

    fun setCallBacks(myCallback: CommonInterface) {
        callBack = myCallback
    }

    fun getAllHolidayCalendars() {
        viewModelScope.launch {
            try {
                callBack.showLoader()
                val orgId = sharedPreferences.getInt(Url.ORGANISATIONID)
                val response = apiService.getAllHolidayCalendars(orgId)
                if (response.isSuccessful) {
                    myResponseList.value = response.body()?.data?.let { ArrayList(it) }
                    callBack.hideLoader()
                    Log.e("Holiday Calendars", "Success" + response.body())
                } else {
                    callBack.hideLoader()
                    callBack.noListData()
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
                val response = apiService.deleteHolidayCalendar(params)
                if (response.isSuccessful) {
                    addHolidayCalendarResponse.value = response.body()
                    callBack.hideLoader()
                }
            } catch (e: Exception) {
                Log.d("main", "getPost: ${e.message}")
                callBack.hideLoader()
            }
        }
    }

//    fun updateAddress(params: UpdateLocationParams) {
//        viewModelScope.launch {
//            try {
//                callBack.showLoader()
//                val response = apiService.getHolidaysByLocation(params)
//                if (response.isSuccessful) {
//                    addHolidayCalendarResponse.value = response.body()
//                    callBack.hideLoader()
//                }
//            } catch (e: Exception) {
//                Log.d("main", "getPost: ${e.message}")
//                callBack.hideLoader()
//            }
//        }
//    }

//    fun addAddress(params: AddLocationParams) {
//        viewModelScope.launch {
//            try {
//                callBack.showLoader()
//                val response = apiService.saveLocation(params)
//                if (response.isSuccessful) {
//                    // myResponse.value = response.body()
//                    //If need to direct access array list data so this type of getting
//                    addLocaResponse.value = response.body()
//                    callBack.hideLoader()
//                    //SO now hear you find direct arraylist data.
//                    // .. got it
//                }
//            } catch (e: Exception) {
//                Log.d("main", "getPost: ${e.message}")
//                callBack.hideLoader()
//            }
//        }
//    }
}