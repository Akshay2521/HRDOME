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
class ExpenseAuthorizeListActivity : AppCompatActivity(), CommonInterface, View.OnClickListener,
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
        binding.linerAdd.visibility  = View.GONE;

        setObserver()

        binding.headerMain.txtTitle.text = "Authorize List"
        setUpListerner()
    }

    override fun onResume() {
        expenseViewModel.getAuthorizeExpenseList()
        super.onResume()

    }

    private fun setObserver() {
        expenseViewModel.myResponseList.observe(this) {
            Log.e("Data", "herrrr" + it.data.size)
            binding.recyclerTimeSchedule.visibility = View.VISIBLE
            if(it.data.size <= 0){
                binding.txtNoData.visibility = View.VISIBLE
            }


            expenseAdapter = ExpenseAdapter(this,it.data, this,false)
            binding.recyclerTimeSchedule.adapter = expenseAdapter
        }

        expenseViewModel.addClientsResponse.observe(this) {
            it.message?.let { it1 -> CommonMethods.showToast(this, it1)
                CommonMethods.hideLoader()
            }
        }


    }

    private fun setUpListerner() {
        binding.apply {
            headerMain.imgBackBtn.setOnClickListener(this@ExpenseAuthorizeListActivity)
            linerFiter.setOnClickListener(this@ExpenseAuthorizeListActivity)
            linerAdd.setOnClickListener(this@ExpenseAuthorizeListActivity)

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
//                val intent = Intent(this, AddTimeSheetActivity::class.java)
//                intent.putExtra("selectedWeek", "")
//                startActivity(intent)
            }
            R.id.linerFiter -> {
                showFilterDialog();
            }

            R.id.img_back_btn -> {
                finish()
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
//        if(!startDate.equals("") && !endDate.equals("")){
//            binding.txtNoData.visibility = View.GONE
//            expenseViewModel.getAuthorizeExpenseListByDate(startDate,endDate,status)
//        }
        binding.txtNoData.visibility = View.GONE
        expenseViewModel.getAuthorizeExpenseListByDate(startDate,endDate,status)


    }

    override fun selectedItem(selectedWeek: String, id: Int?) {
        println("click happen");

        if(expenseViewModel.myResponseList.value?.data?.get(id!!)!!.status == "Submitted"){
            val intent = Intent(this, UpdateExpenseActivity::class.java)
            intent.putExtra("sheetId", expenseViewModel.myResponseList.value?.data?.get(id!!)!!.sheetId.toString())
            intent.putExtra("title", "Authorize Expense")
            intent.putExtra("from", "Authorize")
            startActivity(intent)
        }





    }
}
