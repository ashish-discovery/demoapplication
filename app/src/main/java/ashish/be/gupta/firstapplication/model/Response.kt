package ashish.be.gupta.firstapplication.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("payload") val payload: Payload<Any>?,
    @SerializedName("date") val date: String,
    @SerializedName("error") val error: Error?
)