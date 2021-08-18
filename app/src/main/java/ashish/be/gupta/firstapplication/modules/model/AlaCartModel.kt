package ashish.be.gupta.firstapplication.modules.model

import android.os.Parcel
import android.os.Parcelable

data class AlaCartModel(
    val alaCartId: String?,
    val channelName: String?,
    val channelAmount: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(alaCartId)
        parcel.writeString(channelName)
        parcel.writeString(channelAmount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlaCartModel> {
        override fun createFromParcel(parcel: Parcel): AlaCartModel {
            return AlaCartModel(parcel)
        }

        override fun newArray(size: Int): Array<AlaCartModel?> {
            return arrayOfNulls(size)
        }
    }
}