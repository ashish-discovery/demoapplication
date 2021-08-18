package ashish.be.gupta.firstapplication.logout.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashish.be.gupta.firstapplication.logout.repository.LogoutRepository
import ashish.be.gupta.firstapplication.model.Error
import ashish.be.gupta.firstapplication.model.Resource
import ashish.be.gupta.firstapplication.model.Response
import ashish.be.gupta.firstapplication.utilities.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

class LogoutViewModel(private val logoutRepository: LogoutRepository) : ViewModel() {

    fun doLogout(): MutableLiveData<Resource<Response>> {
        val response = MutableLiveData<Resource<Response>>()
        response.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                response.postValue(Resource.success(logoutRepository.doLogout()))
            } catch (httpException: HttpException) {

                response.postValue(
                    Resource.error(
                        Response(
                            null, "", Utility.handleHttpException(httpException)
                        )
                    )
                )

            } catch (e: Exception) {
                response.postValue(readError(-1, e.message.toString()))
            }
        }

        return response
    }

    private fun readError(code: Int, message: String): Resource<Response> {
        val error = Error(code, message)
        return Resource.error(Response(null, "", error))
    }

}