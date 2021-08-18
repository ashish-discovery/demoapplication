package ashish.be.gupta.firstapplication.modules.model

import android.os.Parcel
import android.os.Parcelable
import ashish.be.gupta.firstapplication.constants.CustomStatus
import ashish.be.gupta.firstapplication.model.Error

data class CustomerListModel(
    val entity: ArrayList<EntityCustomerModel>?,
    val error: Error?
)

data class EntityCustomerModel(
    val customerId: String?,
    val organizationId: String?,
    val roomId: String?,
    val roomName: String?,
    val buildingId: String?,
    val buildingName: String?,
    val buildingNumber: String?,
    val floorId: String?,
    val floorName: String?,
    val customerName: String?,
    val customerMobileNumber: String,
    val customerEmail: String?,
//    val packageAmount: String?,
    val status: CustomStatus?,
    val stbNumbers: ArrayList<EntitySTBModel>?,
    val stbNumber: String?,
    val paymentFromDate: String?,
    val paymentToDate: String?,
    val totalBillAmount: Int?,
    val isDisplayFloor: Boolean = false,
    val fullAddress: String?,
    val stbPackageModel: EntitySTBPackageModel?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString() ?: "-",
        parcel.readString(),
//        parcel.readString(),
        CustomStatus.valueOf(parcel.readString().toString()),
        parcel.createTypedArrayList(EntitySTBModel),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readParcelable(EntitySTBPackageModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(customerId)
        parcel.writeString(organizationId)
        parcel.writeString(roomId)
        parcel.writeString(roomName)
        parcel.writeString(buildingId)
        parcel.writeString(buildingName)
        parcel.writeString(buildingNumber)
        parcel.writeString(floorId)
        parcel.writeString(floorName)
        parcel.writeString(customerName)
        parcel.writeString(customerMobileNumber)
        parcel.writeString(customerEmail)
//        parcel.writeString(packageAmount)
        if (status == null) {
            parcel.writeString("NA")
        } else {
            parcel.writeString(status.name)
        }
        parcel.writeTypedList(stbNumbers)
        parcel.writeString(stbNumber)
        parcel.writeString(paymentFromDate)
        parcel.writeString(paymentToDate)
        if (totalBillAmount != null) {
            parcel.writeInt(totalBillAmount)
        } else {
            parcel.writeInt(0)
        }
        parcel.writeByte(if (isDisplayFloor) 1 else 0)
        parcel.writeString(fullAddress)
        parcel.writeParcelable(stbPackageModel, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EntityCustomerModel> {
        override fun createFromParcel(parcel: Parcel): EntityCustomerModel {
            return EntityCustomerModel(parcel)
        }

        override fun newArray(size: Int): Array<EntityCustomerModel?> {
            return arrayOfNulls(size)
        }
    }
}