package ashish.be.gupta.firstapplication.modules.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ashish.be.gupta.firstapplication.R
import ashish.be.gupta.firstapplication.constants.HttpStatus
import ashish.be.gupta.firstapplication.databinding.ActivityCustomerLogsListBinding
import ashish.be.gupta.firstapplication.logout.DoLogout
import ashish.be.gupta.firstapplication.modules.adapter.CustomerLogsAdapter
import ashish.be.gupta.firstapplication.modules.model.EntityCustomerLogsModel
import ashish.be.gupta.firstapplication.modules.viewmodel.CustomerViewModel
import ashish.be.gupta.firstapplication.utilities.Utility
import kotlinx.android.synthetic.main.activity_customer_logs_list.*
import org.koin.android.scope.createScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class CustomerLogsListActivity : AppCompatActivity() {

//    private val customerViewModel by lazy {
//        ViewModelProvider(this).get(CustomerViewModel::class.java)
//    }

    private val customerViewModel by viewModel<CustomerViewModel>()
    private val scope: Scope by lazy { createScope(this) }

    private var listCustomerLogs = ArrayList<EntityCustomerLogsModel>()
    private val customerLogsAdapter = scope.get<CustomerLogsAdapter> {
        parametersOf(listCustomerLogs)
    }
    private lateinit var activityCustomerLogsListBinding: ActivityCustomerLogsListBinding
    private var customerId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_customer_logs_list)

        activityCustomerLogsListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_customer_logs_list)
        activityCustomerLogsListBinding.lifecycleOwner = this
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        customerId = intent.getStringExtra("customerId") ?: "-1"

        recyclerViewCustomerLogsList.adapter = customerLogsAdapter
//        setUpRecyclerView()
        setUpObserver()

        customerViewModel.getCustomerLogsList(customerId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

//    private fun setUpRecyclerView() {
//        recyclerViewCustomerLogsList.apply {
//            adapter = customerLogsAdapter
//            addItemDecoration(
//                DividerItemDecoration(
//                    this@CustomerLogsListActivity, LinearLayoutManager.VERTICAL
//                )
//            )
//        }
//    }

    private fun setUpObserver() {
        customerViewModel.responseCustomerLogsList.observe(this, { response ->
            when (response.status) {
                HttpStatus.LOADING -> {
                    recyclerViewCustomerLogsList.visibility = View.GONE
                    progressBarCustomerLogsList.show()
                }

                HttpStatus.SUCCESS -> {

                    response.data?.let { paymentListModel ->
                        val data = paymentListModel.entity
                        listCustomerLogs.clear()
                        if (data != null) {
                            listCustomerLogs.addAll(data)
                            customerLogsAdapter.notifyDataSetChanged()
                            recyclerViewCustomerLogsList.visibility = View.VISIBLE
                        } else {
                            Utility.showToast(this, "No Data", Toast.LENGTH_SHORT)
                        }

                        progressBarCustomerLogsList.hide()
                    }
                }

                HttpStatus.ERROR -> {
                    progressBarCustomerLogsList.hide()
                    Utility.showToast(
                        this, response.data!!.error!!.message, Toast.LENGTH_SHORT
                    )
                    if (response.data.error!!.code == 401) {
                        DoLogout(this).logoutAndRedirect()
                    }
                }
            }
        })
    }
}