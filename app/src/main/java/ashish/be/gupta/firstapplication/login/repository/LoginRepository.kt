package ashish.be.gupta.firstapplication.login.repository

import android.util.Base64
import ashish.be.gupta.firstapplication.constants.ConstantKey
import ashish.be.gupta.firstapplication.retrofit.ApiService
import ashish.be.gupta.firstapplication.model.Response

class LoginRepository(private val apiService: ApiService) {

    suspend fun doLogin(mobileNumber: String, password: String): Response {

        val credentials = "$mobileNumber:$password"
        val basicHeader =
            "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        return apiService.doLogin(ConstantKey.CONTENT_TYPE, basicHeader)

    }

}