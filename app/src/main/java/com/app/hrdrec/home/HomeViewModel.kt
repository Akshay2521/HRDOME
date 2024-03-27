package com.app.hrdrec.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.utils.ProgressLoaderCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val apiService: ApiInterface,  val sharedPreferences: SharedPrefUtils
) : ViewModel() {


    private lateinit var callBackLogin: CallBackLogin
    fun setCallBacks(signUpScreen: CallBackLogin) {
        callBackLogin = signUpScreen
    }


    private val _moduleData = MutableLiveData<ArrayList<ModuleData>>()
    val moduleData: MutableLiveData<ArrayList<ModuleData>> get() = _moduleData
    fun getModuleList() {

        val data = sharedPreferences.getUserInfo()
        if (data != null) {
            _moduleData.postValue(data.modules)
        }

      /*  CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = apiService.gelAllModule()

                when (response.isSuccessful) {
                    true -> {
                        callBackLogin.hideLoader()

                        withContext(Dispatchers.Main) {
                            if (response.body()!!.statusCode == 200) {
//                              //  val jsonData = jsonObject.getJSONObject("data")
                                //  val userInfoData = CommonMethods.getUserClassData(jsonData, UserInfo::class.java)
                                val data = response.body()!!.data
                                _moduleData.postValue(data)
                            } else callBackLogin.onErrorMessage(response.body()!!.errorMessage!!)
                        }
                    }

                    else -> {
                        withContext(Dispatchers.Main) {
                            callBackLogin.hideLoader()
                            callBackLogin.onErrorMessage("userName doesn't exist")
                            //CommonKotlin.hideProgress()
                        }
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }*/
    }

    interface CallBackLogin : ProgressLoaderCall {
        fun onErrorMessage(message: String)
    }
}