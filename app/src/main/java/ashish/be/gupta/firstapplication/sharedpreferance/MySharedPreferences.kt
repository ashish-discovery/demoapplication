package ashish.be.gupta.firstapplication.sharedpreferance

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    private val LOGGEDIN = "loggedin"
    private val ACCESS_TOKEN = "access_token"

    init {
        sharedPreferences =
            context.getSharedPreferences("ashish.be.gupta.firstapplication", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.apply()
    }

    fun setLoginStatus(value: Boolean) {
        editor.putBoolean(LOGGEDIN, value)
        editor.commit()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(LOGGEDIN, false)
    }

    fun setAccessToken(accessToken: String) {
        editor.putString(ACCESS_TOKEN, accessToken)
        editor.commit()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN, "")
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }

}