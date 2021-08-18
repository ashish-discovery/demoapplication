package ashish.be.gupta.firstapplication.modules.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ashish.be.gupta.firstapplication.CustomSpinner
import ashish.be.gupta.firstapplication.R
import ashish.be.gupta.firstapplication.constants.HttpStatus
import ashish.be.gupta.firstapplication.databinding.ActivityCustomerListBinding
import ashish.be.gupta.firstapplication.logout.DoLogout
import ashish.be.gupta.firstapplication.modules.adapter.CustomerAdapter
import ashish.be.gupta.firstapplication.modules.model.EntityBuildingModel
import ashish.be.gupta.firstapplication.modules.model.EntityCustomerModel
import ashish.be.gupta.firstapplication.modules.spinneradapter.BuildingSpinnerAdapter
import ashish.be.gupta.firstapplication.modules.viewmodel.BuildingViewModel
import ashish.be.gupta.firstapplication.modules.viewmodel.CustomerViewModel
import ashish.be.gupta.firstapplication.utilities.Utility
import kotlinx.android.synthetic.main.activity_customer_list.*
import org.koin.android.scope.createScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class CustomerListActivity : AppCompatActivity(), CustomerAdapter.OnItemClickListener {

//    private val customerViewModel by lazy {
//        ViewModelProvider(this).get(CustomerViewModel::class.java)
//    }

//    private val buildingViewModel by lazy {
//        ViewModelProvider(this).get(BuildingViewModel::class.java)
//    }

    companion object {
        private const val CUSTOMER_ADD_EDIT_REQUEST_CODE = 9637826
    }

    private val buildingViewModel by viewModel<BuildingViewModel>()
    private val customerViewModel by viewModel<CustomerViewModel>()
    private val scope: Scope by lazy { createScope(this) }

    private lateinit var activityCustomerListBinding: ActivityCustomerListBinding
    private var listCustomer = ArrayList<EntityCustomerModel>()
    private var listBuilding = ArrayList<EntityBuildingModel>()

    private val customerAdapter = scope.get<CustomerAdapter> { parametersOf(listCustomer, this) }
    private val buildingSpinnerAdapter = scope.get<BuildingSpinnerAdapter> {
        parametersOf(listBuilding)
    }

    private var buildingSpinnerCheck = 0
    private var buildingId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_customer_list)

        activityCustomerListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_customer_list)
        activityCustomerListBinding.lifecycleOwner = this
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setUpBuildingObserver()
        setUpCustomerObserver()
        setUpRecyclerView()
        setUpSpinner()

        buildingViewModel.getBuildingList()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.apply {
            val result = getBooleanExtra("isBillGenerated", false)
            if (result) {
                customerViewModel.getCustomerList(buildingId)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpSpinner() {
//        buildingSpinnerAdapter = BuildingSpinnerAdapter(listBuilding)
        spinnerCustomerListBuilding.adapter = buildingSpinnerAdapter

        spinnerCustomerListBuilding.setOnItemSelectedListener(object :
            CustomSpinner.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long, userSelected: Boolean
            ) {
                if (userSelected && ++buildingSpinnerCheck > 1) {
                    val buildingId = listBuilding[position].buildingId.toString()
                    if (buildingId != "-1") {
                        customerViewModel.getCustomerList(buildingId)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }

        })

    }

    private fun setUpRecyclerView() {
//        customerAdapter = CustomerAdapter(listCustomer, this)
        val linearLayoutManager = LinearLayoutManager(this).apply {
            orientation = RecyclerView.VERTICAL
        }
        recyclerViewCustomerList.apply {
            layoutManager = linearLayoutManager
            adapter = customerAdapter
//            addItemDecoration(DividerItemDecoration(this@CustomerListActivity, LinearLayoutManager.VERTICAL))
        }
    }

    private fun setUpBuildingObserver() {
        buildingViewModel.responseBuildingList.observe(this, { response ->
            when (response.status) {
                HttpStatus.LOADING -> {
                    recyclerViewCustomerList.visibility = View.GONE
                    progressBarCustomerList.show()
                }
                HttpStatus.SUCCESS -> {

                    response.data?.let { buildingListModel ->
                        buildingListModel.data?.let { arrayListBuilding ->

                            val model = EntityBuildingModel(
                                "", "", "", "",
                                "-1", "Select Building", "",
                                0, null
                            )

                            arrayListBuilding.add(0, model)

                            listBuilding.clear()
                            listBuilding.addAll(arrayListBuilding)
                            buildingSpinnerAdapter.notifyDataSetChanged()
                            progressBarCustomerList.hide()
                        }
                    }


                }
                HttpStatus.ERROR -> {
                    progressBarCustomerList.hide()
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

    private fun setUpCustomerObserver() {
        customerViewModel.responseCustomerList.observe(this, { response ->
            when (response.status) {
                HttpStatus.LOADING -> {
                    recyclerViewCustomerList.visibility = View.GONE
                    progressBarCustomerList.show()
                }
                HttpStatus.SUCCESS -> {
                    response.data?.let { customerListModel ->
                        if (customerListModel.entity != null) {
                            val data = customerListModel.entity
                            listCustomer.clear()
                            if (data != null) {
                                var floorName = ""
                                for (i in 0 until data.size) {

                                    if (floorName != data[i].floorName) {

                                        listCustomer.add(
                                            EntityCustomerModel(
                                                "", "", "", "",
                                                "", "", "", "",
                                                data[i].floorName, "", "",
                                                "", null, null,
                                                "", "", "",
                                                null,
                                                true, "", null
                                            )
                                        )

                                    }

                                    floorName = data[i].floorName.toString()
                                    listCustomer.add(data[i])

                                }

//                        listCustomer.addAll(data)
                                customerAdapter.notifyDataSetChanged()
                                recyclerViewCustomerList.visibility = View.VISIBLE
                            } else {
                                Utility.showToast(
                                    this, "No Customer",
                                    Toast.LENGTH_LONG
                                )
                            }
                        } else {
                            Utility.showToast(
                                this,
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            )
                        }
                    }
                    progressBarCustomerList.hide()
                }
                HttpStatus.ERROR -> {
                    progressBarCustomerList.hide()
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
        startActivityForResult(intent, CUSTOMER_ADD_EDIT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // if true then, customer's data is updated
        if (requestCode == CUSTOMER_ADD_EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            customerViewModel.getCustomerList(buildingId)
        }
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

                R.id.item_logs -> {
                    val intent = Intent(
                        this, CustomerLogsListActivity::class.java
                    )
                    intent.putExtra("customerId", entityCustomerModel.customerId)
                    startActivity(intent)
                }

            }

            false
        }

        popup.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}