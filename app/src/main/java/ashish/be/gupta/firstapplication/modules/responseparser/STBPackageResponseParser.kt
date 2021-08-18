package ashish.be.gupta.firstapplication.modules.responseparser

import ashish.be.gupta.firstapplication.model.Error
import com.google.gson.annotations.SerializedName

data class STBPackageResponseParser(
    @SerializedName("payload") val payload: PayloadSTBPackageResponseParser?,
    @SerializedName("error") val error: Error?
)

data class PayloadSTBPackageResponseParser(
    @SerializedName("entity") val entity: EntitySTBPackageResponseParser?
)