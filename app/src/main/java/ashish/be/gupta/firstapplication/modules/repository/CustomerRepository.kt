package ashish.be.gupta.firstapplication.modules.repository

import ashish.be.gupta.firstapplication.constants.ConstantKey
import ashish.be.gupta.firstapplication.model.RequestParser
import ashish.be.gupta.firstapplication.model.Response
import ashish.be.gupta.firstapplication.modules.responseparser.*

import ashish.be.gupta.firstapplication.retrofit.ApiService
import ashish.be.gupta.firstapplication.singleton.AdminDetails
import com.google.gson.JsonObject

class CustomerRepository(private val apiService: ApiService) {

    suspend fun getCustomerList(): CustomerListResponseParser {
        return apiService.getCustomerList(ConstantKey.CONTENT_TYPE, AdminDetails.token)
    }

    suspend fun getCustomerList(buildingId: String): CustomerListResponseParser {
        return apiService.getCustomerList(ConstantKey.CONTENT_TYPE, AdminDetails.token, buildingId)
    }

    suspend fun searchCustomer(value: String): CustomerListResponseParser {
        return apiService.searchCustomer(ConstantKey.CONTENT_TYPE, AdminDetails.token, value)
    }

    suspend fun getCustomerDetails(customerID: String): CustomerDetailResponseParser {
        return apiService.getCustomerDetails(
            ConstantKey.CONTENT_TYPE, AdminDetails.token, customerID
        )
    }

    suspend fun addCustomerDetails(requestJsonObject: JsonObject): CustomerDetailResponseParser {
        return apiService.addCustomerDetails(
            ConstantKey.CONTENT_TYPE, AdminDetails.token, requestJsonObject
        )
    }

    suspend fun updateCustomerDetails(requestJsonObject: JsonObject): CustomerDetailResponseParser {
        return apiService.updateCustomerDetails(
            ConstantKey.CONTENT_TYPE, AdminDetails.token, requestJsonObject
        )
    }

    suspend fun generatePassword(customerID: String): Response {
        return apiService.generatePassword(ConstantKey.CONTENT_TYPE, AdminDetails.token, customerID)
    }


    suspend fun getCustomerLogsList(customerId: String): CustomerLogsListResponseParser {
        return apiService.getCustomerLogs(ConstantKey.CONTENT_TYPE, AdminDetails.token, customerId)
    }

}