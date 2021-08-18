package ashish.be.gupta.firstapplication.modules.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ashish.be.gupta.firstapplication.R
import ashish.be.gupta.firstapplication.constants.HttpStatus
import ashish.be.gupta.firstapplication.databinding.ActivitySearchCustomerBinding
import ashish.be.gupta.firstapplication.logout.DoLogout
import ashish.be.gupta.firstapplication.modules.adapter.SearchCustomerAdapter
import ashish.be.gupta.firstapplication.modules.model.EntityCustomerModel
import ashish.be.gupta.firstapplication.modules.viewmodel.CustomerViewModel
import ashish.be.gupta.firstapplication.utilities.Utility
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search_customer.*
import org.koin.android.scope.createScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class SearchCustomerActivity : AppCompatActivity(), SearchCustomerAdapter.OnItemClickListener {

//    private val customerViewModel by lazy {
//        ViewModelProvider(this).get(CustomerViewModel::class.java)
//    }

    private val customerViewModel by viewModel<CustomerViewModel>()
    private val scope: Scope by lazy { createScope(this) }

    private var listCustomer = ArrayList<EntityCustomerModel>()
    private val customerAdapter = scope.get<SearchCustomerAdapter> {
        parametersOf(listCustomer, this)
    }
    private lateinit var activitySearchCustomerBinding: ActivitySearchCustomerBinding
    private var value = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_search_customer)

        activitySearchCustomerBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_search_customer)
        activitySearchCustomerBinding.lifecycleOwner = this
        activitySearchCustomerBinding.handler = this
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setUpCustomerObserver()
        setUpRecyclerView()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onSearch() {
        value = textInputLayoutEditTextSearch.text!!.trim().toString()
        if (validate()) {
            customerViewModel.searchCustomer(value)
        }
    }

    private fun setUpRecyclerView() {
        //        customerAdapter = SearchCustomerAdapter(listCustomer, this)
        val linearLayoutManager = LinearLayoutManager(this).apply {
            orientation = RecyclerView.VERTICAL
        }
        recyclerViewSearchCustomer.apply {
            layoutManager = linearLayoutManager
            adapter = customerAdapter
//            addItemDecoration(DividerItemDecoration(this@CustomerListActivity, LinearLayoutManager.VERTICAL))
        }
    }

    private fun validate(): Boolean {

        if (value.isEmpty()) {
            Utility.showSnackBar(
                searchCustomerContainer, "Value is required",
                Snackbar.LENGTH_SHORT, R.color.colorError
            )
            return false
        }

        if (value.contains('%')) {
            Utility.showSnackBar(
                searchCustomerContainer, "Special character % is not allowed",
                Snackbar.LENGTH_SHORT, R.color.colorError
            )
            return false
        }

        return true
    }

    private fun setUpCustomerObserver() {
        customerViewModel.responseCustomerList.observe(this, { response ->
            when (response.status) {
                HttpStatus.LOADING -> {
                    recyclerViewSearchCustomer.visibility = View.GONE
                    progressBarSearchCustomer.show()
                }
                HttpStatus.SUCCESS -> {
                    response.data?.let { customerListModel ->
                        listCustomer.clear()
                        if (customerListModel.entity != null) {
                            listCustomer.addAll(customerListModel.entity)
                            customerAdapter.notifyDataSetChanged()
                            recyclerViewSearchCustomer.visibility = View.VISIBLE
                        } else {
                            Utility.showToast(
                                this, "No Customer", Toast.LENGTH_SHORT
                            )
                        }
                    }

                    progressBarSearchCustomer.hide()

                }
                HttpStatus.ERROR -> {
                    progressBarSearchCustomer.hide()
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

    override fun onItemClick(entityCustomerModel: EntityCustomerModel) {
        val intent = Intent(this, AddEditCustomerActivity::class.java)
        intent.putExtra("customerID", entityCustomerModel.customerId)
        startActivity(intent)
    }

    override fun showPopMenu(view: View, entityCustomerModel: EntityCustomerModel) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu_customer_list)
        popup.setOnMenuItemClickListener { item ->
            when (item!!.itemId) {

//                R.id.item_stb -> {
//                    val intent = Intent(
//                        this, CustomerSTBListActivity::class.java
//                    )
//                    intent.putExtra("customerId", entityCustomerModel.customerId)
//                    startActivity(intent)
//                }
//
//                R.id.item_generate_bill -> {
//
//                    val intent = Intent(
//                        this, GenerateBillActivity::class.java
//                    )
//                    intent.putExtra("customerDetails", entityCustomerModel)
//                    startActivity(intent)
//
//                }
//
//                R.id.item_view_payment -> {
//                    val intent = Intent(
//                        this, CustomerReceiptsListActivity::class.java
//                    )
//                    intent.putExtra("customerID", entityCustomerModel.customerId)
//                    startActivity(intent)
//                }
//
//                R.id.item_send_notification -> {
//                    val intent = Intent(
//                        this, SendNotificationActivity::class.java
//                    )
//                    intent.putExtra("customerDetails", entityCustomerModel)
//                    startActivity(intent)
//                }
            }

            false
        }

        popup.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}