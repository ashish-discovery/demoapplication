package ashish.be.gupta.firstapplication.modules.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashish.be.gupta.firstapplication.constants.CustomStatus
import ashish.be.gupta.firstapplication.model.Error
import ashish.be.gupta.firstapplication.model.RequestParser
import ashish.be.gupta.firstapplication.model.Resource
import ashish.be.gupta.firstapplication.model.Response
import ashish.be.gupta.firstapplication.modules.model.*
import ashish.be.gupta.firstapplication.modules.repository.CustomerRepository
import ashish.be.gupta.firstapplication.modules.responseparser.EntityCustomerResponseParser
import ashish.be.gupta.firstapplication.utilities.Utility
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import java.lang.StringBuilder

class CustomerViewModel(private val customerRepository: CustomerRepository) : ViewModel() {

    val responseCustomerList = MutableLiveData<Resource<CustomerListModel>>()
    val responseCustomerLogsList = MutableLiveData<Resource<CustomerLogsModel>>()
    val responseCustomerDetails = MutableLiveData<Resource<CustomerDetailModel>>()
    val responseSaveUpdateCustomerDetails = MutableLiveData<Resource<CustomerDetailModel>>()
    val responseGeneratePassword = MutableLiveData<Resource<Response>>()

    fun getCustomerList(): MutableLiveData<Resource<CustomerListModel>> {
        responseCustomerList.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = customerRepository.getCustomerList()
                val customerList = ArrayList<EntityCustomerModel>()
                response.payload?.let { payload ->
                    if (payload.entity != null) {
                        customerList.addAll(parseCustomersDetail(payload.entity))
                        responseCustomerList.postValue(
                            Resource.success(CustomerListModel(customerList, null))
                        )
                    } else {
                        responseCustomerList.postValue(
                            Resource.error(
                                CustomerListModel(
                                    null,
                                    Error(response.error!!.code, response.error.message)
                                )
                            )
                        )
                    }
                }

            } catch (httpException: HttpException) {
                responseCustomerList.postValue(
                    Resource.error(
                        CustomerListModel(null, Utility.handleHttpException(httpException))
                    )
                )
            } catch (e: Exception) {
                val error = Error(-1, e.message.toString())
                responseCustomerList.postValue(
                    Resource.error(CustomerListModel(null, error))
                )
            }

        }

        return responseCustomerList
    }

    fun getCustomerList(buildingId: String): MutableLiveData<Resource<CustomerListModel>> {
        responseCustomerList.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = customerRepository.getCustomerList(buildingId)
                val customerList = ArrayList<EntityCustomerModel>()
                response.payload?.let { payload ->
                    if (payload.entity != null) {
                        customerList.addAll(parseCustomersDetail(payload.entity))
                        responseCustomerList.postValue(
                            Resource.success(CustomerListModel(customerList, null))
                        )
                    } else {
                        responseCustomerList.postValue(
                            Resource.error(
                                CustomerListModel(
                                    null,
                                    Error(response.error!!.code, response.error.message)
                                )
                            )
                        )
                    }

                }


            } catch (httpException: HttpException) {

                responseCustomerList.postValue(
                    Resource.error(
                        CustomerListModel(null, Utility.handleHttpException(httpException))
                    )
                )

            } catch (e: Exception) {
                val error = Error(-1, e.message.toString())
                responseCustomerList.postValue(
                    Resource.error(CustomerListModel(null, error))
                )
            }

        }

        return responseCustomerList
    }

    fun searchCustomer(value: String): MutableLiveData<Resource<CustomerListModel>> {
        responseCustomerList.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = customerRepository.searchCustomer(value)
                val customerList = ArrayList<EntityCustomerModel>()
                response.payload?.let { payload ->
                    if (payload.entity != null) {
                        customerList.addAll(parseCustomersDetail(payload.entity))
                        responseCustomerList.postValue(
                            Resource.success(CustomerListModel(customerList, null))
                        )
                    } else {
                        responseCustomerList.postValue(
                            Resource.error(
                                CustomerListModel(
                                    null,
                                    Error(response.error!!.code, response.error.message)
                                )
                            )
                        )

                    }

                }


            } catch (httpException: HttpException) {

                responseCustomerList.postValue(
                    Resource.error(
                        CustomerListModel(
                            null, Utility.handleHttpException(httpException)
                        )
                    )
                )

            } catch (e: Exception) {
                val error = Error(-1, e.message.toString())
                responseCustomerList.postValue(
                    Resource.error(CustomerListModel(null, error))
                )
            }

        }

        return responseCustomerList
    }

    fun getCustomerDetails(customerID: String): MutableLiveData<Resource<CustomerDetailModel>> {
        responseCustomerDetails.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {

            try {

                val response = customerRepository.getCustomerDetails(customerID)
                var model: EntityCustomerModel? = null
                response.payload?.let { payload ->
                    if (payload.entity != null) {
                        model = parseCustomerDetail(payload.entity)
                        responseCustomerDetails.postValue(
                            Resource.success(CustomerDetailModel(model, null))
                        )
                    } else {
                        responseCustomerDetails.postValue(
                            Resource.error(
                                CustomerDetailModel(
                                    null,
                                    Error(response.error!!.code, response.error.message)
                                )
                            )
                        )

                    }

                }


            } catch (httpException: HttpException) {

                responseCustomerDetails.postValue(
                    Resource.error(
                        CustomerDetailModel(null, Utility.handleHttpException(httpException))
                    )
                )

            } catch (e: Exception) {
                val error = Error(-1, e.message.toString())
                responseCustomerDetails.postValue(
                    Resource.error(CustomerDetailModel(null, error))
                )
            }

        }

        return responseCustomerDetails
    }

    fun saveCustomerDetails(requestJsonObject: JsonObject, isUpdate: Boolean):
            MutableLiveData<Resource<CustomerDetailModel>> {
        responseSaveUpdateCustomerDetails.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {

            try {

                val response = if (isUpdate) {
                    customerRepository.updateCustomerDetails(requestJsonObject)
                } else {
                    customerRepository.addCustomerDetails(requestJsonObject)
                }

                var model: EntityCustomerModel? = null
                response.payload?.let { payload ->
                    if (payload.entity != null) {
                        model = parseCustomerDetail(payload.entity)
                        responseSaveUpdateCustomerDetails.postValue(
                            Resource.success(CustomerDetailModel(model, null))
                        )
                    } else {
                        responseSaveUpdateCustomerDetails.postValue(
                            Resource.error(
                                CustomerDetailModel(
                                    null,
                                    Error(response.error!!.code, response.error.message)
                                )
                            )
                        )

                    }

                }


            } catch (httpException: HttpException) {

                responseSaveUpdateCustomerDetails.postValue(
                    Resource.error(
                        CustomerDetailModel(null, Utility.handleHttpException(httpException))
                    )
                )

            } catch (e: Exception) {
                val error = Error(-1, e.message.toString())
                responseSaveUpdateCustomerDetails.postValue(
                    Resource.error(CustomerDetailModel(null, error))
                )
            }

        }

        return responseSaveUpdateCustomerDetails
    }

    fun generatePassword(customerID: String): MutableLiveData<Resource<Response>> {
        responseGeneratePassword.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                responseGeneratePassword.postValue(
                    Resource.success(customerRepository.generatePassword(customerID))
                )
            } catch (httpException: HttpException) {

                responseGeneratePassword.postValue(
                    Resource.error(
                        Response(
                            null, "", Utility.handleHttpException(httpException)
                        )
                    )
                )
            } catch (e: Exception) {
                val error = Error(-1, e.message.toString())
                responseGeneratePassword.postValue(
                    Resource.error(Response(null, "", error))
                )
            }
        }

        return responseGeneratePassword
    }

    private fun parseCustomersDetail(
        listCustomersResponse: ArrayList<EntityCustomerResponseParser>
    ): ArrayList<EntityCustomerModel> {

        val customerList = ArrayList<EntityCustomerModel>()
        listCustomersResponse.forEach { entity ->
            customerList.add(parseCustomerDetail(entity))
        }

        return customerList
    }

    private fun parseCustomerDetail(model: EntityCustomerResponseParser): EntityCustomerModel {

        val listSTBNumber = ArrayList<EntitySTBModel>()
        model.stbNumbers?.let { stbNumbers ->
            stbNumbers.forEach { stbModel ->
                listSTBNumber.add(
                    EntitySTBModel(
                        stbModel.stbId,
                        stbModel.stbNumber,
                        null,
                        CustomStatus.valueOf(stbModel.status ?: "NA"),
                        null, null, false
                    )
                )
            }
        }

        var stbPackageModel: EntitySTBPackageModel? = null

        model.stbModel?.stbPackageModel?.let {
            stbPackageModel = EntitySTBPackageModel(
                it.createdBy,
                it.createdAt,
                it.updatedBy,
                it.updatedOn,
                it.packageId,
                it.packageName,
                it.packageDetails,
                it.packageAmount,
                it.packageAmount.toString(),
                it.packageName + " (₹ " + it.packageAmount + ")"
            )
        }

        return EntityCustomerModel(
            model.customerId,
            model.organizationId,
            model.roomId,
            model.roomName,
            model.buildingId,
            model.buildingName,
            model.buildingNumber,
            model.floorId,
            model.floorName,
            model.customerName,
            model.customerMobileNumber ?: "-",
            model.customerEmail,
//            model.packageAmount,
            CustomStatus.valueOf(model.status ?: "NA"),
            listSTBNumber,
            model.stbModel?.stbNumber,
            model.paymentFromDate,
            model.paymentToDate,
            model.totalBillAmount,
            false,
            "${model.roomName}/${model.buildingNumber}-${model.buildingName}",
            stbPackageModel
        )

    }

    fun getCustomerLogsList(customerId: String): MutableLiveData<Resource<CustomerLogsModel>> {
        responseCustomerLogsList.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = customerRepository.getCustomerLogsList(customerId)
                val customerLogsList = ArrayList<EntityCustomerLogsModel>()
                response.payload?.let { payload ->
                    if (payload.entity != null) {

                        payload.entity.forEach { customerLogs ->
                            customerLogsList.add(
                                EntityCustomerLogsModel(
                                    customerLogs.customerLogId,
                                    customerLogs.customerId,
                                    customerLogs.logs,
                                    customerLogs.updatedBy,
                                    Utility.getFormattedDateTime(customerLogs.createdAt)
                                )
                            )
                        }

                        responseCustomerLogsList.postValue(
                            Resource.success(CustomerLogsModel(customerLogsList, null))
                        )
                    } else {
                        responseCustomerLogsList.postValue(
                            Resource.error(
                                CustomerLogsModel(
                                    null,
                                    Error(response.error!!.code, response.error.message)
                                )
                            )
                        )
                    }

                }


            } catch (httpException: HttpException) {

                responseCustomerLogsList.postValue(
                    Resource.error(
                        CustomerLogsModel(null, Utility.handleHttpException(httpException))
                    )
                )

            } catch (e: Exception) {
                val error = Error(-1, e.message.toString())
                responseCustomerLogsList.postValue(
                    Resource.error(CustomerLogsModel(null, error))
                )
            }

        }

        return responseCustomerLogsList
    }

}