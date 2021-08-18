package ashish.be.gupta.firstapplication.modules.model

import android.os.Parcel
import android.os.Parcelable
import ashish.be.gupta.firstapplication.constants.CustomStatus
import ashish.be.gupta.firstapplication.model.Error

data class STBModel(
    val data: EntitySTBModel?,
    val error: Error?
)

data class EntitySTBModel(
    val stbId: String?,
    val stbNumber: String?,
    val alaCartDetails: String?, // this holds alacart name and their price
    val status: CustomStatus?,
    val stbPackageModel: EntitySTBPackageModel?,
    val alaCartModels: ArrayList<AlaCartModel>?,
    var stbSelected: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        CustomStatus.valueOf(parcel.readString()!!),
        parcel.readParcelable(EntitySTBPackageModel::class.java.classLoader),
        parcel.createTypedArrayList(AlaCartModel),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(stbId)
        parcel.writeString(stbNumber)
        parcel.writeString(alaCartDetails)
        parcel.writeString(status?.name)
        parcel.writeParcelable(stbPackageModel, flags)
        parcel.writeTypedList(alaCartModels)
        parcel.writeByte(if (stbSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EntitySTBModel> {
        override fun createFromParcel(parcel: Parcel): EntitySTBModel {
            return EntitySTBModel(parcel)
        }

        override fun newArray(size: Int): Array<EntitySTBModel?> {
            return arrayOfNulls(size)
        }
    }
}