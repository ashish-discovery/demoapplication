package ashish.be.gupta.firstapplication.modules.responseparser

import ashish.be.gupta.firstapplication.model.Error
import com.google.gson.annotations.SerializedName

data class RoomListResponseParser(
    @SerializedName("payload") val payload: PayloadRoomListResponseParser?,
    @SerializedName("error") val error: Error?
)

data class PayloadRoomListResponseParser(
    @SerializedName("entity") val entity: ArrayList<EntityRoomResponseParser>?
)

data class EntityRoomResponseParser(
    @SerializedName("floorId") val floorId: String?,
    @SerializedName("floorName") val floorName: String?,
    @SerializedName("floorStatus") val floorStatus: String?,
    @SerializedName("roomId") val roomId: String?,
    @SerializedName("roomName") val roomName: String?,
    @SerializedName("roomStatus") val roomStatus: String?
)