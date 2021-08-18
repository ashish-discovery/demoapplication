package ashish.be.gupta.firstapplication.logout.repository

import ashish.be.gupta.firstapplication.constants.ConstantKey
import ashish.be.gupta.firstapplication.model.Response
import ashish.be.gupta.firstapplication.retrofit.ApiService
import ashish.be.gupta.firstapplication.singleton.AdminDetails

class LogoutRepository(private val apiService: ApiService) {

    suspend fun doLogout(): Response {
        return apiService.doLogout(ConstantKey.CONTENT_TYPE, AdminDetails.token)
    }

}