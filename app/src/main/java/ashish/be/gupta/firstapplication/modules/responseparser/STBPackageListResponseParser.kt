package ashish.be.gupta.firstapplication.modules.responseparser

import ashish.be.gupta.firstapplication.model.Error
import com.google.gson.annotations.SerializedName

data class STBPackageListResponseParser(
    @SerializedName("payload") val payload: PayloadSTBPackageListResponseParser?,
    @SerializedName("error") val error: Error?
)

data class PayloadSTBPackageListResponseParser(
    @SerializedName("entity") val entity: ArrayList<EntitySTBPackageResponseParser>?
)

data class EntitySTBPackageResponseParser(
    @SerializedName("createdBy") val createdBy: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedBy") val updatedBy: String?,
    @SerializedName("updatedOn") val updatedOn: String?,
    @SerializedName("packageId") val packageId: String?,
    @SerializedName("packageName") val packageName: String,
    @SerializedName("packageDetails") val packageDetails: String,
    @SerializedName("packageAmount") val packageAmount: Int
)