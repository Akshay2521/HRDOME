package com.app.hrdrec.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.viewModels
import com.app.hrdrec.R
import com.app.hrdrec.admin.roles.AddRoles
import com.app.hrdrec.home.HomeActivity
import com.app.hrdrec.login.model.LoginParams
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.utils.CommonMethods.showToast
import com.app.hrdrec.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : AppCompatActivity(), LoginViewModel.CallBackLogin {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginViewModel.setCallBacks(this)
        if(loginViewModel.sharedPreferences.getSavedBoolean("isLogin"))
        {
            onResponseSuccess()
        }
        else {


            val login = findViewById<Button>(R.id.login)
            val emailAddress = findViewById<EditText>(R.id.usn)
            val password = findViewById<EditText>(R.id.pwd)
            val cbRememberMe = findViewById<CheckBox>(R.id.cbRememberMe)
            sessionManager = SessionManager(this)

            // Check if there are saved credentials
            val savedUsername = sessionManager.getSavedUsername()
            val savedPassword = sessionManager.getSavedPassword()

            if (!savedUsername.isNullOrBlank() && !savedPassword.isNullOrBlank()) {
                // Auto-fill the username and password fields
                emailAddress.setText(savedUsername)
                password.setText(savedPassword)
                cbRememberMe.isChecked = true
            }

        login.setOnClickListener {
            if (emailAddress.text.toString().isEmpty()) {
                showToast(this, "please enter user name")
                return@setOnClickListener
            }


            if (password.text.toString().isEmpty()) {
                showToast(this, "please enter password")
                return@setOnClickListener
            }
            if (cbRememberMe.isChecked) {
                sessionManager.saveLoginDetails(emailAddress.text.toString().trim(), password.text.toString())
            } else {
                sessionManager.clearSavedCredentials()
            }



//CommonMethods.showLoader(this)
            showLoader()
            loginViewModel.loginApi(
                LoginParams(
                    emailAddress.text.toString().trim(),
                    password.text.toString()
                )
            )

        }
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }
    }



    @SuppressLint("SuspiciousIndentation")
    override fun onResponseSuccess() {
        val intent = Intent(this, HomeActivity::class.java)
      //  val intent = Intent(this, AddRoles::class.java)
              startActivity(intent)
        finish()
    }

    override fun onErrorMessage(message: String) {
        showToast(this,message)
    }

    override fun showLoader() {
       CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
       CommonMethods.hideLoader()
    }
}