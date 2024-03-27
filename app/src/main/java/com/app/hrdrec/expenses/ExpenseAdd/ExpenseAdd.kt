package com.app.hrdrec.expenses.ExpenseAdd

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityExpenseAddBinding
import com.app.hrdrec.expenses.AddExpenseModel
import com.app.hrdrec.expenses.ExpenseAdapter
import com.app.hrdrec.expenses.ExpenseData
import com.app.hrdrec.expenses.ExpnesesViewModel
import com.app.hrdrec.expenses.dataclasses.ExpenseSheet
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.utils.CommonMethods.selectAnyDate
import com.app.hrdrec.utils.getDate
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody


@AndroidEntryPoint
class ExpenseAdd : AppCompatActivity(), CommonInterface, View.OnClickListener,
    ExpenseAdapter.FeatureCallBack {

    private val expenseViewModel: ExpnesesViewModel by viewModels()
    private var expenseAdapter: ExpenseAdapter? = null
    private val binding by lazy { ActivityExpenseAddBinding.inflate(layoutInflater) }
    val datePicker = selectAnyDate(null)
    val datePickerDate = selectAnyDate(null)
    val datePickerEnd = selectAnyDate(null)
    var selectedCategoryId = ""
    var selectedCategoryName = ""
    var selectedTypeId = ""
    var selectedTypeName = ""
    var currencyTypeId = ""
    var currencyName = ""
    var id = ""

    private lateinit var categories: ArrayList<ExpenseData>
    private lateinit var currency: ArrayList<ExpenseData>
    private lateinit var types: ArrayList<ExpenseData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        expenseViewModel.setCallBacks(this)
        //binding.txtNoData.visibility = View.GONE
        setObserver()
        expenseViewModel.getTimesheetscheduler()
        expenseViewModel.getExpenseCategory()
        expenseViewModel.getExpenseCurrency()

        val sheetId = intent.getStringExtra("sheetId")
        if (sheetId != null) {
            Log.d("ExpenseAdd", "Sheet ID: $sheetId")
            expenseViewModel.expenseSheetGetById(sheetId.toInt());
        }
        binding.headerMain.txtTitle.text = "Expense Add"
        setUpListerner()

        //sheetId

        binding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    val selectedCategory = parent?.getItemAtPosition(position) as ExpenseData
                    selectedCategoryId = selectedCategory.categoryId.toString()
                    selectedCategoryName = selectedCategory.categoryName.toString()
                    // Do something with the selected ID and name
                    println("selectedCategoryId ${selectedCategoryId} selectedCategoryName ${selectedCategoryName}")

                    expenseViewModel.getExpenseTypeByCategory(selectedCategoryId);

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do something when nothing is selected
                }
            }

        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val selectedCategory = parent?.getItemAtPosition(position) as ExpenseData
                selectedTypeId = selectedCategory.typeId.toString()
                selectedTypeName = selectedCategory.typeName.toString()
                // Do something with the selected ID and name

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do something when nothing is selected
            }
        }

        binding.currencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    val selectedCategory = parent?.getItemAtPosition(position) as ExpenseData
                    currencyTypeId = selectedCategory.id.toString()
                    currencyName = selectedCategory.currencyCode.toString()
                    // Do something with the selected ID and name

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do something when nothing is selected
                }
            }


    }

    private fun setObserver() {
        expenseViewModel.getExpenseResponse.observe(this) {
            //Log.e("getExpenseResponse", "getExpenseResponse" + it.data.size)
            //it.data.get(0).
            id = it.data?.id.toString();
            binding.txtFrom.setText(it.data?.fromDate)
            binding.txtTo.setText(it.data?.toDate)
            binding.txtPurpose.setText(it.data?.purposeOfExpense)
            println("it.data?.items!!.size  ${it.data?.items!!.size}");

            if (it.data?.items!!.size > 0) {
                binding.txtRemark.setText(it.data?.items!!.get(0).remarks.toString());
                binding.txtDate.setText(it.data?.items!!.get(0).date.toString());
                binding.txtAmount.setText(it.data?.items!!.get(0).submittedAmount.toString());

                currencyName = it.data?.items!!.get(0).currencyCode.toString()
                currencyTypeId = it.data?.items!!.get(0).currencyId.toString()

                selectedCategoryName = it.data?.items!!.get(0).categoryName.toString()
                selectedCategoryId = it.data?.items!!.get(0).categoryId.toString()

                selectedTypeName = it.data?.items!!.get(0).typeName.toString()
                selectedTypeId = it.data?.items!!.get(0).typeId.toString()

                val indexToPreselect = currency.indexOfFirst { it.currencyCode == currencyName }
                if (indexToPreselect != -1) {
                    binding.currencySpinner.setSelection(indexToPreselect)
                }

                val indexToPreselectCate =
                    categories.indexOfFirst { it.categoryName == selectedCategoryName }
                if (indexToPreselect != -1) {
                    binding.categorySpinner.setSelection(indexToPreselectCate)
                }

                val indexToPreselectType = types.indexOfFirst { it.typeName == selectedTypeName }
                if (indexToPreselect != -1) {
                    binding.typeSpinner.setSelection(indexToPreselectType)
                }

                //id
            }
        }

        expenseViewModel.myResponseListType.observe(this) {
            Log.e("Data", "herrrrtype" + it.data.size)

            types = it.data
            // Create adapter and set it to the spinner
            val adapter = SpinnerDataAdapter(this, R.layout.spinner_item_layout, types, "type")
            binding.typeSpinner.adapter = adapter
        }

        expenseViewModel.saveExpenseResponse.observe(this) {
            if (it.statusCode == 200) {
                finish();
                CommonMethods.hideLoader()
            }
            it.message?.let { it1 ->
                CommonMethods.showToast(this, it1)

                CommonMethods.hideLoader()
            }
        }


        expenseViewModel.myResponseListCategory.observe(this) {
            Log.e("Data", "herrrrcategory" + it.data.size)
            categories = it.data
            // Create adapter and set it to the spinner
            val adapter =
                SpinnerDataAdapter(this, R.layout.spinner_item_layout, categories, "category")
            binding.categorySpinner.adapter = adapter

        }

        expenseViewModel.myResponseListCurrency.observe(this) {
            Log.e("Data", "cur" + it.data.size)
            currency = it.data
            // Create adapter and set it to the spinner
            val adapter =
                SpinnerDataAdapter(this, R.layout.spinner_item_layout, currency, "currency")
            binding.currencySpinner.adapter = adapter
        }

    }

    private fun setUpListerner() {
        binding.apply {
            headerMain.imgBackBtn.setOnClickListener(this@ExpenseAdd)
            imgFrom.setOnClickListener(this@ExpenseAdd)
            imgTo.setOnClickListener(this@ExpenseAdd)
            imgDate.setOnClickListener(this@ExpenseAdd)
            btnSave.setOnClickListener(this@ExpenseAdd)
            btnSubmit.setOnClickListener(this@ExpenseAdd)
            btnCancel.setOnClickListener(this@ExpenseAdd)
        }
    }


    override fun onErrorMessage(message: String) {
        CommonMethods.showToast(this, message)
    }

    override fun onResponseSuccess() {

    }

    override fun noListData() {
//        binding.recyclerTimeSchedule.visibility=View.GONE
//        binding.txtNoData.visibility=View.VISIBLE
    }

    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }

    fun clickSave() {
        submitSaveExpense("Saved");
    }

    fun clickSubmit() {
        submitSaveExpense("Submitted");
    }


    fun submitSaveExpense(status: String) {
        // Create an instance of AddExpenseModel
        if (binding.txtFrom.text.toString() == "") {
            CommonMethods.showToast(ExpenseAdd@ this, "Please enter from date")
            return;
        }
        if (binding.txtTo.text.toString() == "") {
            CommonMethods.showToast(ExpenseAdd@ this, "Please enter to date")
            return;
        }
        if (binding.txtPurpose.text.toString() == "") {
            CommonMethods.showToast(ExpenseAdd@ this, "Please enter purpose")
            return;
        }


        val addExpenseModel = AddExpenseModel(
            //weekendDate = "2024-01-04",
            //totalHours = null,

            status = status,
            employeeId = expenseViewModel.sharedPreferences.getUserInfo()?.employeeId,
            organizationId = null,
            locationId = null,
            fromDate = binding.txtFrom.text.toString(),
            toDate = binding.txtTo.text.toString(),
            purposeOfExpense = binding.txtPurpose.text.toString(),
            submittedDate = "2024-03-06",
            approvedDate = null,
            reimbursedDate = null,
            description = null,
            items = listOf(
                AddExpenseModel.Item(
                    status = status,
                    typeId = selectedTypeId.toInt(),
                    typeName = selectedTypeName,
                    categoryId = selectedCategoryId.toInt(),
                    categoryName = selectedCategoryName,
                    checked = false,
                    currencyId = currencyTypeId.toInt(),
                    currencyCode = currencyName,
                    receipts = listOf(
//                        AddExpenseModel.Item.Receipt(
//                            fileName = "Screenshot 2023-05-26 105919.png",
//                            fileType = "image/png"
//                        )
                    ),
                    date = binding.txtDate.text.toString(),
                    submittedAmount = binding.txtAmount.text.toString(),
                    remarks = binding.txtRemark.text.toString(),
                )
            )
        )

        if (id != "") {
            addExpenseModel.id = id.toInt()
        }

        var expensesheet = ExpenseSheet(addExpenseModel);
        val addExpenseModelJson = Gson().toJson(addExpenseModel)
        val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), addExpenseModelJson)
        //val file = File("path_to_your_file.png")
        //val filePart = MultipartBody.Part.createFormData("file", file.name, RequestBody.create("image/*".toMediaTypeOrNull(), file))
        expenseViewModel.SubmitExpense(requestBody)

    }


    override fun onClick(v: View) {
        when (v.id) {

            R.id.btnSave -> {
                clickSave()
            }

            R.id.btnSubmit -> {
                clickSubmit()
            }

            R.id.img_back_btn, R.id.btnCancel -> {
                finish()
            }

            R.id.imgDate -> {
                datePickerDate.show(supportFragmentManager, "DatePicker")
                datePickerDate.addOnPositiveButtonClickListener {
                    binding.txtDate.text = getDate(it)
                }
            }

            R.id.imgFrom -> {
                datePickerEnd.show(supportFragmentManager, "DatePicker")
                datePickerEnd.addOnPositiveButtonClickListener {
                    binding.txtFrom.text = getDate(it)
                }
            }

            R.id.imgTo -> {
                datePicker.show(supportFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                    binding.txtTo.text = getDate(it)
                }
            }


            R.id.btnSubmit -> {

            }
        }
    }

    override fun selectedItem(week: String, id: Int?) {
        TODO("Not yet implemented")
    }


}
