package ashish.be.gupta.firstapplication.modules.responseparser

import ashish.be.gupta.firstapplication.model.Error
import com.google.gson.annotations.SerializedName

data class CustomerSTBListResponseParser(
    @SerializedName("payload") val payload: PayloadCustomerSTBListResponseParser?,
    @SerializedName("error") val error: Error?
)

data class PayloadCustomerSTBListResponseParser(
    @SerializedName("entity") val entity: ArrayList<EntitySTBModelResponseParser>?
)

data class EntitySTBModelResponseParser(
    @SerializedName("stbId") val stbId: String?,
    @SerializedName("stbNumber") val stbNumber: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("stbPackageModel") var stbPackageModel: EntitySTBPackageResponseParser?,
    @SerializedName("alaCartModels") var alaCartModels: ArrayList<AlaCartResponseParser>?
)