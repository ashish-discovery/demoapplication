package ashish.be.gupta.firstapplication.model

import com.google.gson.annotations.SerializedName

data class RequestParser(
    @SerializedName("payload") val payload: EntityRequestParser
)

data class EntityRequestParser(
    @SerializedName("entity") val entity: Any
)