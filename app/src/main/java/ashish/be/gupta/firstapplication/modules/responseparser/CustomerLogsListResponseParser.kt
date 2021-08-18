package ashish.be.gupta.firstapplication.modules.responseparser

import ashish.be.gupta.firstapplication.model.Error
import com.google.gson.annotations.SerializedName

data class CustomerLogsListResponseParser(
    @SerializedName("payload") val payload: PayloadCustomerLogsListResponseParser?,
    @SerializedName("error") val error: Error?
)

data class PayloadCustomerLogsListResponseParser(
    @SerializedName("entity") val entity: ArrayList<EntityCustomerLogsResponseParser>?
)

data class EntityCustomerLogsResponseParser(
    @SerializedName("createdBy") val createdBy: String?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedBy") val updatedBy: String,
    @SerializedName("updatedOn") val updatedOn: String?,
    @SerializedName("customerLogId") val customerLogId: String,
    @SerializedName("customerId") val customerId: String,
    @SerializedName("logs") val logs: String
)