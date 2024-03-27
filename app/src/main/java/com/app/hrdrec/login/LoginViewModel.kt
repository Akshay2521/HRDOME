package com.app.hrdrec.login

import androidx.lifecycle.ViewModel
import com.app.hrdrec.Url.ISLOGINAPI
import com.app.hrdrec.Url.ORGANISATIONID
import com.app.hrdrec.di.SharedPrefUtils
import com.app.hrdrec.login.model.LoginParams
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.utils.ProgressLoaderCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val apiService: ApiInterface,
     val sharedPreferences: SharedPrefUtils
) : ViewModel() {


    private lateinit var callBackLogin: CallBackLogin

    fun setCallBacks(signUpScreen: CallBackLogin) {
        callBackLogin = signUpScreen
    }

    fun loginApi(param: LoginParams) {
        sharedPreferences.saveBoolean(ISLOGINAPI, true)
        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = apiService.loginUser(param)

                when (response.isSuccessful) {
                    true -> {
                        callBackLogin.hideLoader()

                        withContext(Dispatchers.Main) {
                            if (response.body()!!.statusCode == 200) {

//                              //  val jsonData = jsonObject.getJSONObject("data")
                                //  val userInfoData = CommonMethods.getUserClassData(jsonData, UserInfo::class.java)

                                sharedPreferences.saveBoolean(ISLOGINAPI, false)
                                val data = response.body()!!.data
                                if (data != null) {
                                    sharedPreferences.saveUserInfo(data)
                                }
                                if (data != null) {
                                    data.organizationId?.let {
                                        sharedPreferences.saveInt(
                                            ORGANISATIONID,
                                            it
                                        )
                                    }
                                }
                                sharedPreferences.saveBoolean("isLogin", true)
                                callBackLogin.onResponseSuccess()

                            } else
                                callBackLogin.onErrorMessage(response.body()!!.errorMessage!!)
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
        }
    }

    interface CallBackLogin : ProgressLoaderCall {
        fun onErrorMessage(message: String)
        fun onResponseSuccess()
    }
}