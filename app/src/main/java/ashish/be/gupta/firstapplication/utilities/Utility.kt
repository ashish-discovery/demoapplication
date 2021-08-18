package ashish.be.gupta.firstapplication.utilities

import android.content.Context
import android.provider.Settings
import android.view.View
import android.widget.Toast
import ashish.be.gupta.firstapplication.model.Error
import ashish.be.gupta.firstapplication.model.Resource
import ashish.be.gupta.firstapplication.model.Response
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.HttpException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class Utility {

    companion object {

        fun checkDigit(number: Int): String {
            return if (number <= 9) "0$number" else number.toString()
        }

        fun getDayOfMonthSuffix(n: Int): String {

            if (n in 11..13) {
                return "th"
            }
            return when (n % 10) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
        }

        fun getDeviceId(context: Context): String {
            return Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
            )
        }

        fun showToast(context: Context, message: String, duration: Int) {
            Toast.makeText(context, message, duration).show()
        }

        fun showSnackBar(view: View, message: String, duration: Int, color: Int) {
            val snackBar = Snackbar.make(view, message, duration)
            val sbView = snackBar.view
            sbView.setBackgroundColor(color)
            snackBar.show()
        }

        fun readError(code: Int, message: String): Resource<Response> {
            val error = Error(code, message)
            return Resource.error(Response(null, "", error))
        }

        // input -> 2021-06-10
        // output -> 10-Jun-2021
        fun getFormattedDate(data: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val sourceDate = sdf.parse(data)
            val targetSDF = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            return targetSDF.format(sourceDate)
        }

        // input -> 2021-06-10T14:23:45.231+000
        // output -> 10-Jun-2021 14:25:50
        fun getFormattedDateTime(data: String): String {
            val date = data.split(".")[0]
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault())
            val sourceDate = sdf.parse(data)
            val targetSDF = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", Locale.getDefault())
            return targetSDF.format(sourceDate)
        }

        fun getFormattedCurrency(amount: Int): String {
            return "₹ " + DecimalFormat("##,##,##0").format(amount)
        }

        fun getFormattedCurrency(amount: Double): String {
            return "₹ " + DecimalFormat("##,##,###.##").format(amount)
        }

        // return todays date in yyyy-MM-dd format
        fun getTodayDate(): String {
            val calender = Calendar.getInstance()
            return "${calender.get(Calendar.YEAR)}-${
                checkDigit(calender.get(Calendar.MONTH) + 1)
            }-${checkDigit(calender.get(Calendar.DAY_OF_MONTH))}"
        }

        fun convertToMilliSec(date: String): Long {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            var timeInMilliseconds: Long = 0
            try {
                val mDate = sdf.parse(date)
                timeInMilliseconds = mDate.time
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return timeInMilliseconds
        }

        fun handleHttpException(httpException: HttpException): Error {
            return if (httpException.code() == 401 || httpException.code() == 500) {
                val responseBody = JSONObject(httpException.response()!!.errorBody()!!.string())
                val errorJson = responseBody.getJSONObject("error")
                Error(errorJson.getInt("code"), errorJson.getString("message"))
            } else {
                Error(-1, httpException.message.toString())
            }
        }

        /*
        param -> 2021-06-01
        return -> 2021-06-30
         */
        fun getLastDayOfMonth(date: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateObj = sdf.parse(date)
            val calendar = Calendar.getInstance()
            calendar.time = dateObj ?: Date()
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            return sdf.format(calendar.time)
        }

    }

}