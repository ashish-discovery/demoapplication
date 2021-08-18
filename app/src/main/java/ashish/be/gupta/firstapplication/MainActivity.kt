package ashish.be.gupta.firstapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ashish.be.gupta.firstapplication.constants.HttpStatus
import ashish.be.gupta.firstapplication.databinding.ActivityMainBinding
import ashish.be.gupta.firstapplication.logout.DoLogout
import ashish.be.gupta.firstapplication.logout.viewmodel.LogoutViewModel
import ashish.be.gupta.firstapplication.modules.view.CustomerListActivity
import ashish.be.gupta.firstapplication.modules.view.SearchCustomerActivity
import ashish.be.gupta.firstapplication.utilities.Utility
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    private val loginViewModel by viewModel<LogoutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        activityMainBinding.handler = this
        activityMainBinding.lifecycleOwner = this

    }

    fun customerList(){
        val intent = Intent(baseContext, CustomerListActivity::class.java)
        startActivity(intent)
    }

    fun searchCustomerList(){
        val intent = Intent(baseContext, SearchCustomerActivity::class.java)
        startActivity(intent)
    }

    fun logout() {
        loginViewModel.doLogout().observe(this, {
            when (it.status) {
                HttpStatus.LOADING -> {

                }

                HttpStatus.SUCCESS -> {
                    val result = it.data?.payload?.entity as Boolean
                    if (result) {
                        DoLogout(this).logoutAndRedirect()
                    }
                }

                HttpStatus.ERROR -> {
                    Utility.showToast(this, it.data!!.error!!.message, Toast.LENGTH_SHORT)
                }
            }
        })
    }
}