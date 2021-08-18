package ashish.be.gupta.firstapplication.modules.model

import android.os.Parcel
import android.os.Parcelable
import ashish.be.gupta.firstapplication.model.Error

data class STBPackageListModel(
    val data: ArrayList<EntitySTBPackageModel>?,
    val error: Error?
)

data class EntitySTBPackageModel(
    val createdBy: String?,
    val createdAt: String?,
    val updatedBy: String?,
    val updatedOn: String?,
    val packageId: String?,
    val packageName: String?,
    val packageDetails: String?,
    val packageAmount: Int,
    var amount : String?,
    val displayName: String?
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
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(createdBy)
        parcel.writeString(createdAt)
        parcel.writeString(updatedBy)
        parcel.writeString(updatedOn)
        parcel.writeString(packageId)
        parcel.writeString(packageName)
        parcel.writeString(packageDetails)
        parcel.writeInt(packageAmount)
        parcel.writeString(amount)
        parcel.writeString(displayName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EntitySTBPackageModel> {
        override fun createFromParcel(parcel: Parcel): EntitySTBPackageModel {
            return EntitySTBPackageModel(parcel)
        }

        override fun newArray(size: Int): Array<EntitySTBPackageModel?> {
            return arrayOfNulls(size)
        }
    }
}