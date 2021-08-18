package ashish.be.gupta.firstapplication.retrofit

import ashish.be.gupta.firstapplication.model.Response
import ashish.be.gupta.firstapplication.modules.responseparser.*
import com.google.gson.JsonObject
import retrofit2.http.*

interface ApiService {

    @GET("/admin/login")
    suspend fun doLogin(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String
    ): Response

    @GET("/admin/logout")
    suspend fun doLogout(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String
    ): Response


    @GET("/admin/buildings")
    suspend fun getBuildingList(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String
    ): BuildingListResponseParser


    @GET("/admin/rooms-buildingid/{buildingId}")
    suspend fun getRoomsByBuilding(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String,
        @Path("buildingId") buildingId: String
    ): RoomListResponseParser



    @GET("/admin/get-customer-logs/{customerId}")
    suspend fun getCustomerLogs(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String,
        @Path("customerId") customerId: String
    ): CustomerLogsListResponseParser

    /*****
     * customer apis start
     */

    @POST("/customer/create-customer")
    suspend fun addCustomerDetails(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String,
        @Body jsonObject: JsonObject
    ): CustomerDetailResponseParser

    @PUT("/customer/update-customer")
    suspend fun updateCustomerDetails(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String,
        @Body jsonObject: JsonObject
    ): CustomerDetailResponseParser

    @GET("/customer/get-customers")
    suspend fun getCustomerList(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String
    ): CustomerListResponseParser

    @GET("/customer/search-customers/{value}")
    suspend fun searchCustomer(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String,
        @Path("value") value: String
    ): CustomerListResponseParser

    @GET("/admin/get-customers/{buildingId}")
    suspend fun getCustomerList(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String,
        @Path("buildingId") buildingId: String
    ): CustomerListResponseParser

    @GET("/admin/get-customer/{customerId}")
    suspend fun getCustomerDetails(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String,
        @Path("customerId") customerId: String
    ): CustomerDetailResponseParser


    @GET("/admin/generate-password/{customerId}")
    suspend fun generatePassword(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") auth: String,
        @Path("customerId") customerId: String
    ): Response


    /*****
     * customer apis end
     */

}