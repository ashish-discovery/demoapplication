package ashish.be.gupta.firstapplication.modules.responseparser

import com.google.gson.annotations.SerializedName

data class AlaCartResponseParser(
    @SerializedName("createdBy") val createdBy: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedBy") val updatedBy: String?,
    @SerializedName("updatedOn") val updatedOn: String?,
    @SerializedName("alaCartId") val alaCartId: String?,
    @SerializedName("channelName") val channelName: String,
    @SerializedName("channelAmount") val channelAmount: String
)