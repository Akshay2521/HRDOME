package com.app.hrdrec.expenses


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityExpenseStatusUpdateBinding
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class UpdateExpenseActivity : AppCompatActivity(), CommonInterface, View.OnClickListener,
    ExpenseAdapter.FeatureCallBack {

    private val expenseViewModel: ExpnesesViewModel by viewModels()
    private var expenseAdapter: ExpenseUpdateAdapter? = null
    private val binding by lazy { ActivityExpenseStatusUpdateBinding.inflate(layoutInflater) }
    var form = "";

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        expenseViewModel.setCallBacks(this)
        binding.txtNoData.visibility = View.GONE


        setObserver()
        expenseViewModel.getAuthorizeExpenseList()
        binding.headerMain.txtTitle.text = "Authorize List"
        setUpListerner()

        val sheetId = intent.getStringExtra("sheetId")
        val title = intent.getStringExtra("title")
        val from = intent.getStringExtra("from")
        if (from != null) {
            form = from
            if (from == "Authorize") {
                binding.btnApprove.text = "Approve"
                binding.btnReject.text = "Reject"
            } else if (from == "Reimbursed") {
                binding.btnApprove.text = "Reimburse"
                binding.btnReject.text = "Reject"
            } else if (from == "EditExpense") {
                binding.btnApprove.text = "Submit"
                binding.btnReject.text = "Update"
            }
        }


        if (title != null) {
            binding.headerMain.txtTitle.text = title
        }
        if (sheetId != null) {
            Log.d("ExpenseAdd", "Sheet ID: $sheetId")
            expenseViewModel.expenseSheetGetById(sheetId.toInt());
        }
    }

    private fun setObserver() {
        expenseViewModel.getExpenseResponse.observe(this) {
            //id = it.data?.id.toString();
            binding.txtFrom.setText(it.data.fromDate)
            binding.txtTo.setText(it.data.toDate)
            binding.txtPurpose.setText(it.data.purposeOfExpense)


            expenseAdapter = ExpenseUpdateAdapter(this, it.data.items, this, false, form)
            binding.recyclerTimeSchedule.adapter = expenseAdapter

        }

        expenseViewModel.addClientsResponse.observe(this) {
            it.message?.let { it1 ->
                CommonMethods.showToast(this, it1)
                CommonMethods.hideLoader()
            }
        }

        expenseViewModel.saveExpenseResponse.observe(this) {
            if (it.statusCode == 200) {
                finish();
                CommonMethods.hideLoader()
            }

//            it.message?.let { it1 -> CommonMethods.showToast(this, it1)
//
//                CommonMethods.hideLoader()
//            }
        }


    }

    private fun setUpListerner() {
        binding.apply {
            headerMain.imgBackBtn.setOnClickListener(this@UpdateExpenseActivity)
            binding.btnApprove.setOnClickListener(this@UpdateExpenseActivity)
            binding.btnReject.setOnClickListener(this@UpdateExpenseActivity)
            binding.btnCancel.setOnClickListener(this@UpdateExpenseActivity)
        }
    }


    override fun onErrorMessage(message: String) {
        CommonMethods.showToast(this, message)
    }

    override fun onResponseSuccess() {

    }

    override fun noListData() {
        binding.recyclerTimeSchedule.visibility = View.GONE
        binding.txtNoData.visibility = View.VISIBLE
    }

    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }


    fun getModel(status: String): String {
        val currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val expenseData = expenseViewModel.getExpenseResponse.value?.data

        expenseData?.fromDate = binding.txtFrom.text.toString();
        expenseData?.toDate = binding.txtTo.text.toString();
        expenseData?.purposeOfExpense = binding.txtPurpose.text.toString();

        if (status == "Approved") {
            expenseData?.approvedDate = formattedDate;
            for (item in expenseData?.items!!) {
                if (item.checked) {
                    item.checked = false;
                    item.status = "Approved";
                }
            }

        } else if (status == "Reimbursed") {
            expenseData?.reimbursedDate = formattedDate;
            expenseData?.reimbursedBy =
                expenseViewModel.sharedPreferences.getUserInfo()?.employeeId?.toString();
            expenseData?.reimbursedName =
                expenseViewModel.sharedPreferences.getUserInfo()?.firstName?.toString();
            expenseData?.reimbursedDate = formattedDate;

            for (item in expenseData?.items!!) {
                if (item.checked) {
                    item.checked = false;
                    item.status = "Reimbursed";
                }
            }

        } else if (status == "ApproveReject") {
            expenseData?.status = "Submitted";
            expenseData?.approvedDate = formattedDate;
            for (item in expenseData?.items!!) {
                if (item.checked) {
                    item.checked = false;
                    item.status = "Rejected";
                }
            }

        } else {
            expenseData?.status = status;
        }
        expenseData?.items = expenseAdapter!!.getUpdatedData();
        println("here data");
        println(Gson().toJson(expenseData));

        return Gson().toJson(expenseData);

    }

    fun getModelPlain(): String {
        var expenseData = expenseViewModel.getExpenseResponse.value?.data
        expenseData?.fromDate = binding.txtFrom.text.toString();
        expenseData?.toDate = binding.txtTo.text.toString();
        expenseData?.purposeOfExpense = binding.txtPurpose.text.toString();

        expenseData?.items = expenseAdapter!!.getUpdatedData();

        return Gson().toJson(expenseData);

    }


    override fun onClick(v: View) {
        when (v.id) {
            ///Saved, Partially_Approved, Reimbursed, Submitted, Rejected, Repudiated, Approved, Partially_Reimbursed
            R.id.img_back_btn, R.id.btnCancel -> {
                finish()
            }

            R.id.btnApprove -> {
                if (binding.btnApprove.text == "Approved") {
                    CommonMethods.showAlertYesNoMessage(
                        this, "Are you sure you want to Authorize this record"
                    ) {

                        val updatedData = getModel("Approved");
                        val requestBody =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), updatedData)
                        expenseViewModel.SubmitExpense(requestBody)

                    }
                } else if (binding.btnApprove.text == "Reimburse") {
                    CommonMethods.showAlertYesNoMessage(
                        this, "Are you sure you want to Reimburse this record"
                    ) {

                        val updatedData = getModel("Reimbursed");
                        val requestBody =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), updatedData)
                        expenseViewModel.SubmitExpense(requestBody)
                    }

                } else if (binding.btnApprove.text == "Submit") {
                    CommonMethods.showAlertYesNoMessage(
                        this, "Are you sure you want to Submit this record"
                    ) {
                        val updatedData = getModel("Submitted");
                        val requestBody =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), updatedData)
                        expenseViewModel.SubmitExpense(requestBody)


                    }
                }
            }

            R.id.btnReject -> {

                if (binding.btnReject.text == "Reject") {
                    CommonMethods.showAlertYesNoMessage(
                        this, "Are you sure you want to Reject this record"
                    ) {


                        //if(binding.btnApprove.text == "Approved"){
                        showLoader();
                        val updatedData = getModel("ApproveReject");
                        val requestBody =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), updatedData)
                        expenseViewModel.SubmitExpense(requestBody)
                        //}

                    }

                } else {
                    val updatedData = getModelPlain();
                    val requestBody =
                        RequestBody.create("text/plain".toMediaTypeOrNull(), updatedData)
                    expenseViewModel.SubmitExpense(requestBody)
                }
            }
        }
    }


    override fun selectedItem(selectedWeek: String, id: Int?) {

//        val intent = Intent(this, AddTimeSheetActivity::class.java)
//        intent.putExtra("selectedWeek", selectedWeek)
//        intent.putExtra("timeId", id)
//        resultLauncher.launch(intent)
    }
}
