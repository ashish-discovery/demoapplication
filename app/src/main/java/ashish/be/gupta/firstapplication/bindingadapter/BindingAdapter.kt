package ashish.be.gupta.firstapplication.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ashish.be.gupta.firstapplication.utilities.Utility

//@BindingAdapter(value = ["roomName", "buildingNumber", "buildingName"], requireAll = true)
//fun getAddress(view: TextView, roomName: String, buildingNumber: String, buildingName: String) {
//    view.text = "$roomName/$buildingNumber-$buildingName"
//}

@BindingAdapter(value = ["buildingNumber", "buildingName"], requireAll = true)
fun getBuildingName(view: TextView, buildingNumber: String, buildingName: String) {
    if (buildingNumber.isNotEmpty() && buildingName.isNotBlank()) {
        view.text = "$buildingNumber-$buildingName"
    } else if (buildingNumber.isNotEmpty()) {
        view.text = buildingNumber
    } else {
        view.text = buildingName
    }

}

@BindingAdapter(value = ["fromDate", "toDate"], requireAll = true)
fun paymentMonth(view: TextView, fromDate: String?, toDate: String?) {
    view.text = if (fromDate != null && toDate != null) {
        "${Utility.getFormattedDate(fromDate)} TO ${Utility.getFormattedDate(toDate)}"
    } else {
        "-"
    }

}

@BindingAdapter("app:setCurrencyFormat")
fun setCurrencyFormat(view: TextView, amount: Int?) {
    view.text = if (amount != null) {
        Utility.getFormattedCurrency(amount)
    } else {
        "-"
    }

}

@BindingAdapter("app:setCurrencyFormat")
fun setCurrencyFormat(view: TextView, amount: Double?) {
    view.text = if (amount != null) {
        Utility.getFormattedCurrency(amount)
    } else {
        "-"
    }
}

@BindingAdapter("app:setFormattedDate")
fun setFormattedDate(view: TextView, date: String?) {
    if (!date.isNullOrEmpty()) {
        view.text = Utility.getFormattedDate(date)
    } else {
        view.text = "-"
    }
}