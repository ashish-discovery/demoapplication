package ashish.be.gupta.firstapplication.modules.model

import android.os.Parcel
import android.os.Parcelable
import ashish.be.gupta.firstapplication.constants.CustomStatus
import ashish.be.gupta.firstapplication.model.Error

data class RoomListModel(
    val entity: ArrayList<EntityRoomModel>?,
    val error: Error?
)


data class EntityRoomModel(
    val floorId: String?,
    val floorName: String?,
    val floorStatus: CustomStatus?,
    val roomId: String?,
    val roomName: String?,
    val roomStatus: CustomStatus?,
    val isDisplayFloor: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        CustomStatus.valueOf(parcel.readString().toString()),
        parcel.readString(),
        parcel.readString(),
        CustomStatus.valueOf(parcel.readString().toString()),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(floorId)
        parcel.writeString(floorName)
        if (floorStatus == null) {
            parcel.writeString("NA")
        } else {
            parcel.writeString(floorStatus.name)
        }
        parcel.writeString(roomId)
        parcel.writeString(roomName)
        if (roomStatus == null) {
            parcel.writeString("NA")
        } else {
            parcel.writeString(roomStatus.name)
        }
        parcel.writeByte(if (isDisplayFloor) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EntityRoomModel> {
        override fun createFromParcel(parcel: Parcel): EntityRoomModel {
            return EntityRoomModel(parcel)
        }

        override fun newArray(size: Int): Array<EntityRoomModel?> {
            return arrayOfNulls(size)
        }
    }
}