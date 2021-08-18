package ashish.be.gupta.firstapplication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import ashish.be.gupta.firstapplication.MainActivity
import ashish.be.gupta.firstapplication.R
import ashish.be.gupta.firstapplication.constants.HttpStatus
import ashish.be.gupta.firstapplication.databinding.ActivityLoginBinding
import ashish.be.gupta.firstapplication.login.viewmodel.LoginViewModel
import ashish.be.gupta.firstapplication.sharedpreferance.MySharedPreferences
import ashish.be.gupta.firstapplication.singleton.AdminDetails
import ashish.be.gupta.firstapplication.utilities.Utility
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

//    private val loginViewModel by lazy {
//        ViewModelProvider(this).get(LoginViewModel::class.java)
//    }

    private val loginViewModel by viewModel<LoginViewModel>()

    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var sharedPreferences: MySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)

        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        activityLoginBinding.handler = this
        activityLoginBinding.lifecycleOwner = this

        sharedPreferences = MySharedPreferences(this)

        if (sharedPreferences.isLoggedIn()) {

            AdminDetails.token = sharedPreferences.getAccessToken()!!
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

    private fun isFormValid(mobileNumber: String, password: String): Boolean {

        if (mobileNumber.isBlank()) {
            Toast.makeText(
                this,
                "Mobile Number is required",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (mobileNumber.length != 10) {
            Toast.makeText(
                this,
                "Mobile Number is invalid",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (password.isBlank()) {
            Toast.makeText(
                this,
                "Password is required",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        return true

    }

    fun doLogin() {

        isEnableFields(false)

        val mobileNumber = textInputLayoutEditTextMobile.text?.trim().toString()
        val password = textInputLayoutEditTextPassword.text?.trim().toString()

        if (isFormValid(mobileNumber, password)) {

            loginViewModel.doLogin(mobileNumber, password).observe(this, Observer {

                when (it.status) {
                    HttpStatus.LOADING -> {
                        contentLoadingProgressBar.show()
                    }

                    HttpStatus.SUCCESS -> {
                        contentLoadingProgressBar.hide()
                        val token = it.data!!.payload!!.entity as String
                        saveDetails(token)
                        redirectToDashboard()
                    }

                    HttpStatus.ERROR -> {
                        contentLoadingProgressBar.hide()
                        isEnableFields(true)

                        Utility.showSnackBar(
                            relativeLayoutLogin, it.data!!.error!!.message,
                            Snackbar.LENGTH_SHORT, R.color.colorError
                        )

                    }
                }

            })

        } else {
            isEnableFields(true)
        }

    }

    fun forgotPassword() {

    }

    // save token to sharedPreference and singleton obj
    private fun saveDetails(token: String) {
        sharedPreferences.setAccessToken("Bearer $token")
        sharedPreferences.setLoginStatus(true)
        AdminDetails.token = "Bearer $token"
    }

    private fun redirectToDashboard() {
        val intent = Intent(baseContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isEnableFields(value: Boolean) {
        buttonLogin.isEnabled = value
        textInputLayoutEditTextMobile.isEnabled = value
        textInputLayoutEditTextPassword.isEnabled = value
    }
}