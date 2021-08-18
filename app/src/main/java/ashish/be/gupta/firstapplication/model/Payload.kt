package ashish.be.gupta.firstapplication.model

import com.google.gson.annotations.SerializedName

data class Payload<T>(
    @SerializedName("entity") val entity : T
)