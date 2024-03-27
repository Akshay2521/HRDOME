package com.app.hrdrec.expenses


import FilterDialogFragment
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityExpenseListBinding
import com.app.hrdrec.expenses.ExpenseAdd.ExpenseAdd
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.utils.CommonMethods.selectAnyDate
import com.app.hrdrec.utils.getDate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExpenseListActivity : AppCompatActivity(), CommonInterface, View.OnClickListener,
    ExpenseAdapter.FeatureCallBack, FilterDialogFragment.FilterDialogListener {

    private val expenseViewModel: ExpnesesViewModel by viewModels()
    private var expenseAdapter: ExpenseAdapter? = null

    private val binding by lazy { ActivityExpenseListBinding.inflate(layoutInflater) }
    private var isDatesVisible = false
    private var filterDialogFragment: FilterDialogFragment? = null
    var strTo = ""
    var strFrom = ""
    var strStatus = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        expenseViewModel.setCallBacks(this)
        binding.txtNoData.visibility = View.GONE
        setObserver()
        binding.headerMain.txtTitle.text = "Expense List"
        setUpListerner()

    }

    override fun onResume() {
        expenseViewModel.getTimesheetscheduler()
        super.onResume()
    }

    private fun setObserver() {
        expenseViewModel.myResponseList.observe(this) {
            Log.e("Data", "herrrr" + it.data.size)
            binding.recyclerTimeSchedule.visibility = View.VISIBLE
            if(it.data.size <= 0){
                binding.txtNoData.visibility = View.VISIBLE
            }


            expenseAdapter = ExpenseAdapter(this,it.data, this,true)
            binding.recyclerTimeSchedule.adapter = expenseAdapter
        }

        expenseViewModel.deleteExpenseResponse.observe(this) {
//            it.message?.let { it1 -> CommonMethods.showToast(this, it1)
//                CommonMethods.hideLoader()
//
//            }
            CommonMethods.showToast(this, "Deleted Successfully")
            expenseViewModel.getTimesheetscheduler()
        }
    }

    private fun setUpListerner() {
        binding.apply {
            headerMain.imgBackBtn.setOnClickListener(this@ExpenseListActivity)
            linerFiter.setOnClickListener(this@ExpenseListActivity)
            linerAdd.setOnClickListener(this@ExpenseListActivity)
//            conFromDate.setOnClickListener(this@ExpenseListActivity)
//            conToDate.setOnClickListener(this@ExpenseListActivity)
//            btnSubmit.setOnClickListener(this@ExpenseListActivity)

        }
    }


    override fun onErrorMessage(message: String) {
        CommonMethods.showToast(this, message)
    }

    override fun onResponseSuccess() {

    }

    override fun noListData() {
        binding.recyclerTimeSchedule.visibility=View.GONE
        binding.txtNoData.visibility=View.VISIBLE
    }

    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }

    private fun showFilterDialog() {
        filterDialogFragment = FilterDialogFragment()
        filterDialogFragment!!.strTo = strTo
        filterDialogFragment!!.strFrom = strFrom
        filterDialogFragment!!.strStatus = strStatus
        filterDialogFragment?.show(supportFragmentManager, "filter_dialog")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.linerAdd -> {
                val intent = Intent(this, ExpenseAdd::class.java)
                startActivity(intent)
            }
            R.id.linerFiter -> {
                showFilterDialog();
            }

            R.id.img_back_btn -> {
                finish()
            }

            R.id.conFromDate -> {

//                val datePicker = selectAnyDate(null)
//                datePicker.show(supportFragmentManager, "DatePicker")
//                datePicker.addOnPositiveButtonClickListener {
//                    binding.txtFrom.text = getDate(it)
//                }
            }

            R.id.conToDate -> {

//                if(binding.txtFrom.text.toString().isNotEmpty()) {
//                    val datePicker = selectAnyDate(null)
//                    datePicker.show(supportFragmentManager, "DatePicker")
//                    datePicker.addOnPositiveButtonClickListener {
//                        //  binding.txtTo.text = getDate(it)
//
//                        if (CommonMethods.validateDate(
//                                binding.txtFrom.text.toString(),
//                                getDate(it)
//                            )
//                        ) {
//
//                            binding.txtTo.text = getDate(it)
//                        } else {
//                            println("End date is not after start date.")
//                            CommonMethods.showToast(this, "To date is not after start date.")
//                        }
//                    }
//                }
//                else{
//                    CommonMethods.showToast(this, "Please select from first")
//                }



            }

            R.id.btnSubmit -> {


                // if(binding.txtFrom.text.toString().isNotEmpty()&&binding.txtTo.text.toString().isNotEmpty())

               // expenseViewModel.getTimesheetschedulerDates(binding.txtFrom.text.toString(),binding.txtTo.text.toString())
            }

        }
    }


    override fun onFilterApply(startDate: String, endDate: String,status:String) {
        println("startDate : ${startDate}");
        println("endDate : ${endDate}");
        println("status : ${status}");
        strFrom = startDate;
        strTo = endDate;
        strStatus = status;

        //if(!startDate.equals("") && !endDate.equals("")){
            binding.txtNoData.visibility = View.GONE
            expenseViewModel.getTimesheetschedulerDates(startDate,endDate,strStatus)
        //}


    }

    override fun selectedItem(selectedWeek: String, id: Int?) {

            if(selectedWeek == "delete"){
                CommonMethods.showAlertYesNoMessage(
                    this,
                    "Are you sure you want to delete this record"
                ) {
                   //println("id ${id}");
                   expenseViewModel.deleteExpense(id!!)

                }
            }else if(selectedWeek == "edit"){
                /*
                val intent = Intent(this, ExpenseAdd::class.java)
                intent.putExtra("sheetId", id.toString())
                startActivity(intent)
                */
                val intent = Intent(this, UpdateExpenseActivity::class.java)
                intent.putExtra("sheetId", id.toString())
                intent.putExtra("title", "Expense Edit")
                intent.putExtra("from", "EditExpense")
                startActivity(intent);
            }
    }
}
