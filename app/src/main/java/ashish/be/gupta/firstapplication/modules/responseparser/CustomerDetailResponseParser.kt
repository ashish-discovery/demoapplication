package ashish.be.gupta.firstapplication.modules.responseparser

import ashish.be.gupta.firstapplication.model.Error
import com.google.gson.annotations.SerializedName

data class CustomerDetailResponseParser(
    @SerializedName("payload") val payload: PayloadCustomerDetailResponseParser?,
    @SerializedName("error") val error: Error?
)
data class PayloadCustomerDetailResponseParser(
    @SerializedName("entity") val entity : EntityCustomerResponseParser?
)