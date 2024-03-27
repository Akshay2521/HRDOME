package com.app.hrdrec.manager

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityMangerLeaveReviewBinding
import com.app.hrdrec.manager.all_leave_submitted.LeaveManagerData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.utils.getDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MangerLeaveReviewActivity : AppCompatActivity(), View.OnClickListener, CommonInterface,
    ManagerReviewLeaveAdapter.FeatureCallBack {

    private val binding by lazy { ActivityMangerLeaveReviewBinding.inflate(layoutInflater) }
    private val viewModel: ManagerReviewViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
viewModel.setCallBacks(this)
        setUpListener()
        setObserver()
        viewModel.getAllLeaveManager()
    }

    private fun setUpListener() {
        binding.apply {
            headerMain.imgBackBtn.setOnClickListener(this@MangerLeaveReviewActivity)

            conFromDate.setOnClickListener(this@MangerLeaveReviewActivity)
            conToDate.setOnClickListener(this@MangerLeaveReviewActivity)
            btnSubmit.setOnClickListener(this@MangerLeaveReviewActivity)

        }
    }

    private fun setObserver() {
        viewModel.getAllLeaveList.observe(this) {
            Log.e("Data", "herrrr" + it.data.size)
           val managerReviewTimeSheetAdapter = ManagerReviewLeaveAdapter(it.data, this)
            binding.recyclerLeave.adapter = managerReviewTimeSheetAdapter
        }

        viewModel.addClientsResponse.observe(this) {
            it.statusMessage?.let { it1 -> CommonMethods.showToast(this, it1)

                viewModel.getAllLeaveManager()
            }

        }


    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.img_back_btn -> {
                finish()
            }

            R.id.conFromDate -> {

                val datePicker = CommonMethods.selectDatePrevios(null)
                datePicker.show(supportFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                    binding.txtFrom.text = getDate(it)
                }
            }

            R.id.conToDate -> {
                val datePicker = CommonMethods.selectDatePrevios(null)
                datePicker.show(supportFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                    binding.txtTo.text = getDate(it)
                }
            }

            R.id.btnSubmit->{
                if(binding.txtFrom.text.toString().isNotEmpty()&&binding.txtTo.text.toString().isNotEmpty())

                    viewModel.getAllLeaveManagerDates(binding.txtFrom.text.toString(),binding.txtTo.text.toString())

            }


        }
    }

    override fun onErrorMessage(message: String) {
        CommonMethods.showToast(this, message)
    }

    override fun onResponseSuccess() {

    }

    override fun noListData() {

    }

    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }

    override fun selectedItem(from: String, mObj: LeaveManagerData) {
        /*CommonMethods.showAlertYesNoMessage(this, "Are you sure you want to $from this record") {

            viewModel.approvRejectLeave(from, mObj.id!!)
        }*/

        CommonMethods.showCustomDialog(this,"Are you sure you want to $from this leave",
            onYesClickListener = { remarks ->
                // Handle "Yes" click with remarks
                viewModel.approvRejectLeave(from, mObj.id!!,remarks)

            },
            onNoClickListener = {

            }
        )
    }
}