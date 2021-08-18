package ashish.be.gupta.firstapplication.modules.responseparser

import ashish.be.gupta.firstapplication.model.Error
import com.google.gson.annotations.SerializedName

data class CustomerListResponseParser(
    @SerializedName("payload") val payload: PayloadCustomerResponseParser?,
    @SerializedName("error") val error: Error?
)

data class PayloadCustomerResponseParser(
    @SerializedName("entity") val entity: ArrayList<EntityCustomerResponseParser>?
)

data class EntityCustomerResponseParser(
    @SerializedName("customerId") val customerId: String?,
    @SerializedName("organizationId") val organizationId: String?,
    @SerializedName("roomId") val roomId: String?,
    @SerializedName("roomName") val roomName: String?,
    @SerializedName("buildingId") val buildingId: String?,
    @SerializedName("buildingName") val buildingName: String?,
    @SerializedName("buildingNumber") val buildingNumber: String?,
    @SerializedName("floorId") val floorId: String?,
    @SerializedName("floorName") val floorName: String?,
    @SerializedName("customerName") val customerName: String?,
    @SerializedName("customerMobileNumber") val customerMobileNumber: String?,
    @SerializedName("customerEmail") val customerEmail: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("stbNumbers") val stbNumbers: ArrayList<EntitySTBModelResponseParser>?,
    @SerializedName("stbModel") var stbModel : EntitySTBModelResponseParser?,
    @SerializedName("paymentFromDate") val paymentFromDate: String?,
    @SerializedName("paymentToDate") val paymentToDate: String?,
    @SerializedName("totalBillAmount") val totalBillAmount: Int?
)