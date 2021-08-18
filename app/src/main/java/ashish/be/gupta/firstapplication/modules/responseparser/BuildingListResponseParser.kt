package ashish.be.gupta.firstapplication.modules.responseparser

import ashish.be.gupta.firstapplication.model.Error
import com.google.gson.annotations.SerializedName

data class BuildingListResponseParser(
    @SerializedName("payload") val payload: PayloadBuildingListResponseParser?,
    @SerializedName("date") val date: String,
    @SerializedName("error") val error: Error?
)

data class PayloadBuildingListResponseParser(
    @SerializedName("entity") val entity: ArrayList<EntityBuildingResponseParser>?
)

data class EntityBuildingResponseParser(
    @SerializedName("createdBy") val createdBy: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedBy") val updatedBy: String?,
    @SerializedName("updatedOn") val updatedOn: String?,
    @SerializedName("buildingId") val buildingId: String?,
    @SerializedName("buildingName") val buildingName: String?,
    @SerializedName("buildingNumber") val buildingNumber: String?,
    @SerializedName("floorCount") val floorCount: Int,
    @SerializedName("status") val status: String?
)