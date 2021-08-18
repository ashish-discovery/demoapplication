package ashish.be.gupta.firstapplication.modules.model

import android.os.Parcel
import android.os.Parcelable
import ashish.be.gupta.firstapplication.constants.CustomStatus
import ashish.be.gupta.firstapplication.model.Error

data class BuildingListModel(
    val data: ArrayList<EntityBuildingModel>?,
    val error: Error?
)

data class EntityBuildingModel(
    val createdBy: String?,
    val createdAt: String?,
    val updatedBy: String?,
    val updatedOn: String?,
    val buildingId: String?,
    val buildingName: String?,
    val buildingNumber: String?,
    val floorCount: Int,
    val status: CustomStatus?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        CustomStatus.valueOf(parcel.readString().toString())
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(createdBy)
        parcel.writeString(createdAt)
        parcel.writeString(updatedBy)
        parcel.writeString(updatedOn)
        parcel.writeString(buildingId)
        parcel.writeString(buildingName)
        parcel.writeString(buildingNumber)
        parcel.writeInt(floorCount)
        if (status == null) {
            parcel.writeString("NA")
        } else {
            parcel.writeString(status.name)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EntityBuildingModel> {
        override fun createFromParcel(parcel: Parcel): EntityBuildingModel {
            return EntityBuildingModel(parcel)
        }

        override fun newArray(size: Int): Array<EntityBuildingModel?> {
            return arrayOfNulls(size)
        }
    }
}