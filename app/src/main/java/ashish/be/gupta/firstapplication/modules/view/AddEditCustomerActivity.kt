package ashish.be.gupta.firstapplication.modules.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import ashish.be.gupta.firstapplication.*
import ashish.be.gupta.firstapplication.constants.HttpStatus
import ashish.be.gupta.firstapplication.databinding.ActivityAddEditCustomerBinding
import ashish.be.gupta.firstapplication.logout.DoLogout
import ashish.be.gupta.firstapplication.modules.model.EntityBuildingModel
import ashish.be.gupta.firstapplication.modules.model.EntityCustomerModel
import ashish.be.gupta.firstapplication.modules.model.EntityRoomModel
import ashish.be.gupta.firstapplication.modules.model.EntitySTBPackageModel
import ashish.be.gupta.firstapplication.modules.spinneradapter.BuildingSpinnerAdapter
import ashish.be.gupta.firstapplication.modules.spinneradapter.RoomSpinnerAdapter
import ashish.be.gupta.firstapplication.modules.viewmodel.BuildingViewModel
import ashish.be.gupta.firstapplication.modules.viewmodel.CustomerViewModel
import ashish.be.gupta.firstapplication.modules.viewmodel.RoomViewModel
import ashish.be.gupta.firstapplication.utilities.Utility
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_add_edit_customer.*
import org.koin.android.scope.createScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class AddEditCustomerActivity : AppCompatActivity() {

    private val customerViewModel by viewModel<CustomerViewModel>()
    private val buildingViewModel by viewModel<BuildingViewModel>()
    private val roomViewModel by viewModel<RoomViewModel>()
    private val scope: Scope by lazy { createScope(this) }

    private lateinit var activityAddEditCustomerBinding: ActivityAddEditCustomerBinding

    private var listBuilding = ArrayList<EntityBuildingModel>()
    private var listRoom = ArrayList<EntityRoomModel>()
    private var listSTBPackage = ArrayList<EntitySTBPackageModel>()

    private val buildingSpinnerAdapter = scope.get<BuildingSpinnerAdapter> {
        parametersOf(listBuilding)
    }
    private val roomSpinnerAdapter = scope.get<RoomSpinnerAdapter> {
        parametersOf(listRoom)
    }

    private var customerDetails: EntityCustomerModel? = null
    private var isEditing = false
    private var customerID = ""
    private var customerName = ""
    private var customerMobileNumber = ""
    private var packageAmount = ""
    private var customerEmail = ""
    private var buildingID = "-1"
    private var roomID = "-1"
    private var floorID = "-1"
    private var stbPackageID = "-1"
    private var buildingSpinnerCheck = 0
    private var roomSpinnerCheck = 0
    private var stbPackageSpinnerCheck = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_edit_customer)

        activityAddEditCustomerBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_edit_customer)
        activityAddEditCustomerBinding.handler = this
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setUpSpinner()

        setUpCustomerDetailObserver()
        setUpBuildingObserver()
        setUpRoomListObserver()
        setSaveObserver()
        setGeneratePasswordObserver()

        displayViewByIntent()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_edit_customer, menu)
        val menuItem = menu!!.getItem(0)
        menuItem.isVisible = intent.hasExtra("customerID")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_generate_password) {

            if (customerDetails != null && customerDetails!!.customerMobileNumber.isNotEmpty()) {
                customerViewModel.generatePassword(customerID)
            } else {
                Utility.showSnackBar(
                    addEditCustomerContainer, "Mobile number is required",
                    Snackbar.LENGTH_SHORT, R.color.colorError
                )
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayViewByIntent() {
        if (intent.hasExtra("customerID")) {
            customerID = intent.getStringExtra("customerID") ?: "-1"
            customerViewModel.getCustomerDetails(customerID)
            activityAddEditCustomerBinding.buttonName = "MODIFY"
            isEditing = true
            title = "Modify Customer"
        } else {
            buildingViewModel.getBuildingList()
            activityAddEditCustomerBinding.buttonName = "ADD"
            isEditing = false
            title = "Add Customer"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onSubmit() {
        isEnableFields(false)
        customerName = textInputLayoutEditTextName.text!!.trim().toString()
        customerMobileNumber = textInputLayoutEditTextMobile.text!!.trim().toString()
        customerEmail = textInputLayoutEditTextEmail.text!!.trim().toString()

        if (validateForm()) {
            customerViewModel.saveCustomerDetails(generateJsonParam(), isEditing)
        } else {
            isEnableFields(true)
        }

    }

    private fun setUpSpinner() {
        spinnerAddEditCustomerBuilding.adapter = buildingSpinnerAdapter
        spinnerAddEditCustomerRoom.adapter = roomSpinnerAdapter
        handlerSpinnerEvent()
    }

    private fun handlerSpinnerEvent() {

        spinnerAddEditCustomerBuilding.setOnItemSelectedListener(object :
            CustomSpinner.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long, userSelected: Boolean
            ) {
                if (userSelected && ++buildingSpinnerCheck > 1) {
                    listBuilding[position].buildingId?.let {
                        roomViewModel.getRoomsByBuilding(it)
                        buildingID = it
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })


        spinnerAddEditCustomerRoom.setOnItemSelectedListener(object :
            CustomSpinner.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long, userSelected: Boolean
            ) {
                if (userSelected && ++roomSpinnerCheck > 1) {
                    listRoom[position].let {
                        roomID = it.roomId.toString()
                        floorID = it.floorId.toString()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })

//        spinnerAddEditCustomerSTBPackage.setOnItemSelectedListener(object :
//            CustomSpinner.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>, view: View, position: Int, id: Long, userSelected: Boolean
//            ) {
//                if (userSelected) {
//                    listSTBPackage[position].let {
//                        stbPackageID = it.packageId ?: "-1"
//                        textInputLayoutEditTextPackageAmount.setText((it.packageAmount).toString())
//                    }
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {}
//        })


    }

    private fun validateForm(): Boolean {

        if (customerName.isEmpty()) {
            textInputLayoutEditTextName.requestFocus()
            Utility.showSnackBar(
                addEditCustomerContainer, "Customer Name is required",
                Snackbar.LENGTH_SHORT, R.color.colorError
            )
            return false
        }

        if (buildingID == "-1") {
            Utility.showSnackBar(
                addEditCustomerContainer, "Building is required",
                Snackbar.LENGTH_SHORT, R.color.colorError
            )
            return false
        }

        if (roomID == "-1" || floorID == "-1") {
            Utility.showSnackBar(
                addEditCustomerContainer, "Room is required",
                Snackbar.LENGTH_SHORT, R.color.colorError
            )
            return false
        }

        return true

    }

    private fun isEnableFields(value: Boolean) {
        buttonCustomerSubmit.isEnabled = value
        textInputLayoutEditTextName.isEnabled = value
        textInputLayoutEditTextMobile.isEnabled = value
        textInputLayoutEditTextEmail.isEnabled = value
        spinnerAddEditCustomerBuilding.isEnabled = value
        spinnerAddEditCustomerRoom.isEnabled = value
    }

    private fun setUpCustomerDetailObserver() {

        customerViewModel.responseCustomerDetails.observe(this, { response ->

            when (response.status) {
                HttpStatus.LOADING -> {
                    progressBarAddEditCustomer.show()
                }
                HttpStatus.SUCCESS -> {

                    response.data?.let { customerDetailModel ->
                        if (customerDetailModel.entity != null) {
                            val entity = customerDetailModel.entity
                            activityAddEditCustomerBinding.customerDetails = entity
                            customerDetails = entity
                            buildingID = entity.buildingId ?: "-1"
                            roomID = entity.roomId ?: "-1"
                            floorID = entity.floorId ?: "-1"
                            stbPackageID = entity.stbPackageModel?.packageId ?: "-1"
                        } else {
                            Utility.showToast(
                                this, "Something went wrong", Toast.LENGTH_SHORT
                            )
                        }
                    }

                    progressBarAddEditCustomer.hide()
                    buildingViewModel.getBuildingList()

                }

                HttpStatus.ERROR -> {
                    progressBarAddEditCustomer.hide()
                    Utility.showSnackBar(
                        progressBarAddEditCustomer, response.data!!.error!!.message,
                        Snackbar.LENGTH_SHORT, R.color.colorError
                    )


                    if (response.data.error!!.code == 401) {
                        DoLogout(this).logoutAndRedirect()
                    }

                }

            }

        })

    }

    private fun setUpBuildingObserver() {
        buildingViewModel.responseBuildingList.observe(this, Observer { response ->
            when (response.status) {
                HttpStatus.LOADING -> {
                    progressBarAddEditCustomer.show()
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
                            if (isEditing) {
                                makeBuildingSelected()
                                roomViewModel.getRoomsByBuilding(buildingID)
                            } else {
                                progressBarAddEditCustomer.hide()
                            }
                        }
                    }

                }
                HttpStatus.ERROR -> {
                    progressBarAddEditCustomer.hide()
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

    private fun setUpRoomListObserver() {
        roomViewModel.responseRoomList.observe(this, { response ->
            when (response.status) {
                HttpStatus.LOADING -> {
                    progressBarAddEditCustomer.show()
                }

                HttpStatus.SUCCESS -> {
                    listRoom.clear()
                    response.data?.let { roomListModel ->
                        if (roomListModel.entity != null) {
                            val model = EntityRoomModel(
                                "-1", "", null, "-1",
                                "Select Room", null
                            )
                            listRoom.add(model)
                            listRoom.addAll(roomListModel.entity)
                            roomSpinnerAdapter.notifyDataSetChanged()
                            if (isEditing) {
                                makeRoomSelected()
                            }
                        } else {
                            Utility.showToast(
                                this, "Something went wrong", Toast.LENGTH_SHORT
                            )
                        }
                    }
                    progressBarAddEditCustomer.hide()
                }

                HttpStatus.ERROR -> {
                    progressBarAddEditCustomer.hide()
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

    private fun setSaveObserver() {

        customerViewModel.responseSaveUpdateCustomerDetails.observe(this, { response ->

            when (response.status) {
                HttpStatus.LOADING -> {
                    progressBarAddEditCustomer.show()
                }
                HttpStatus.SUCCESS -> {

                    response.data?.let { customerDetailModel ->

                        if (isEditing) {
                            Utility.showToast(
                                this, "Data Modified", Toast.LENGTH_SHORT
                            )
                            val intent = Intent()
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        } else {
                            Utility.showToast(
                                this, "Data Saved", Toast.LENGTH_SHORT
                            )
                            progressBarAddEditCustomer.hide()
                        }
                    }

                }

                HttpStatus.ERROR -> {
                    progressBarAddEditCustomer.hide()
                    isEnableFields(true)
                    Utility.showSnackBar(
                        progressBarAddEditCustomer, response.data!!.error!!.message,
                        Snackbar.LENGTH_SHORT, R.color.colorError
                    )
                    if (response.data.error!!.code == 401) {
                        DoLogout(this).logoutAndRedirect()
                    }

                }
            }

        })

    }

    private fun setGeneratePasswordObserver() {

        customerViewModel.responseGeneratePassword.observe(this, { response ->

            when (response.status) {

                HttpStatus.LOADING -> {
                    progressBarAddEditCustomer.show()
                }

                HttpStatus.SUCCESS -> {

                    progressBarAddEditCustomer.hide()
                    val email = textInputLayoutEditTextEmail.text!!.trim().toString()
                    if (email.isNotBlank()) {
                        Utility.showToast(
                            this, "Password sent on $email", Toast.LENGTH_SHORT
                        )
                    }

                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Password")
                    alertDialogBuilder.setMessage(response.data!!.payload!!.entity as String)
                    alertDialogBuilder.show()

                }

                HttpStatus.ERROR -> {
                    progressBarAddEditCustomer.hide()
                    Utility.showSnackBar(
                        progressBarAddEditCustomer, response.data!!.error!!.message,
                        Snackbar.LENGTH_SHORT, R.color.colorError
                    )

                    if (response.data.error!!.code == 401) {
                        DoLogout(this).logoutAndRedirect()
                    }

                }
            }

        })

    }

    private fun generateJsonParam(): JsonObject {
        val json = JsonObject()
        val jsonPayload = JsonObject()
        val jsonEntity = JsonObject()

        jsonEntity.addProperty("buildingId", buildingID)
        jsonEntity.addProperty("roomId", roomID)
        jsonEntity.addProperty("floorId", floorID)
        jsonEntity.addProperty("customerEmail", customerEmail)
        jsonEntity.addProperty("customerMobileNumber", customerMobileNumber)
        jsonEntity.addProperty("customerName", customerName)

        if (isEditing) {
            jsonEntity.addProperty("customerId", customerID)
        }

        jsonPayload.add("entity", jsonEntity)
        json.add("payload", jsonPayload)

        return json
    }

    private fun makeBuildingSelected() {
        for (i in 0 until listBuilding.size) {
            if (listBuilding[i].buildingId == buildingID) {
                spinnerAddEditCustomerBuilding.programmaticallySetPosition(i, false)
                break
            }
        }
    }

    private fun makeRoomSelected() {
        for (i in 0 until listRoom.size) {
            if (listRoom[i].roomId == roomID) {
                spinnerAddEditCustomerRoom.programmaticallySetPosition(i, false)
                break
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}