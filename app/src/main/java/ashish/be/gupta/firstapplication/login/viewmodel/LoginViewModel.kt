package ashish.be.gupta.firstapplication.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashish.be.gupta.firstapplication.login.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import ashish.be.gupta.firstapplication.model.Error
import ashish.be.gupta.firstapplication.model.Payload
import ashish.be.gupta.firstapplication.model.Resource
import ashish.be.gupta.firstapplication.model.Response
import ashish.be.gupta.firstapplication.utilities.Utility

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    fun doLogin(mobileNumber: String, password: String): MutableLiveData<Resource<Response>> {

        val response = MutableLiveData<Resource<Response>>()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                response.postValue(Resource.loading(null))
                response.postValue(
                    Resource.success(
                        loginRepository.doLogin(mobileNumber, password)
                    )
                )
            } catch (httpException: HttpException) {

                response.postValue(
                    Resource.error(
                        Response(
                            Payload("null"), "", Utility.handleHttpException(httpException)
                        )
                    )
                )

            } catch (e: Exception) {
                val error = Error(-1, e.message.toString())
                response.postValue(Resource.error(Response(Payload(""), "", error)))
            }
        }

        return response

    }


}