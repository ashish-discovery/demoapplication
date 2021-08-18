package ashish.be.gupta.firstapplication.logout

import android.content.Context
import android.content.Intent
import ashish.be.gupta.firstapplication.login.LoginActivity
import ashish.be.gupta.firstapplication.sharedpreferance.MySharedPreferences
import ashish.be.gupta.firstapplication.singleton.MasterDetails

class DoLogout(val context: Context) {

    private var mySharedPreferences: MySharedPreferences = MySharedPreferences(context)

    fun logoutAndRedirect() {
        deleteDetails()
        val intent = Intent(context, LoginActivity::class.java)
        // clear all activities from stack and add this activity
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    private fun deleteDetails() {
        mySharedPreferences.clear()
        MasterDetails.complaintStatus.clear()
        MasterDetails.paymentType.clear()
        MasterDetails.status.clear()
    }

}