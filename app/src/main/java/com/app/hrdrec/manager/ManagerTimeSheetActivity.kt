package com.app.hrdrec.manager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityManagerTimeSheetBinding
import com.app.hrdrec.manager.response.all_submitted_response.GetSubmitedManagerModel
import com.app.hrdrec.timesheet.AddTimeSheetActivity
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.utils.CommonMethods.showCustomDialog
import com.app.hrdrec.utils.getDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagerTimeSheetActivity : AppCompatActivity(), View.OnClickListener, CommonInterface,
    ManagerTimeSheetAdapter.FeatureCallBack {

    private val binding by lazy { ActivityManagerTimeSheetBinding.inflate(layoutInflater) }

    private val viewModel: ManagerReviewViewModel by viewModels()

    lateinit var managerReviewTimeSheetAdapter: ManagerTimeSheetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.setCallBacks(this)
        setObserver()
        setUpListener()
        viewModel.getSubmittedByManager()

    }

    private fun setObserver() {
        viewModel.myResponseList.observe(this) {
            Log.e("Data", "herrrr" + it.data.size)
            managerReviewTimeSheetAdapter = ManagerTimeSheetAdapter(it.data, this)
            binding.recyclerTimeSchedule.adapter = managerReviewTimeSheetAdapter
        }

        viewModel.addClientsResponse.observe(this) {
            it.statusMessage?.let { it1 ->
                CommonMethods.showToast(this, it1)

                viewModel.getSubmittedByManager()
            }

        }


    }

    private fun setUpListener() {
        binding.apply {
            headerMain.imgBackBtn.setOnClickListener(this@ManagerTimeSheetActivity)
            btnSubmit.setOnClickListener(this@ManagerTimeSheetActivity)
            conFromDate.setOnClickListener(this@ManagerTimeSheetActivity)
            conToDate.setOnClickListener(this@ManagerTimeSheetActivity)
            btnSubmit.setOnClickListener(this@ManagerTimeSheetActivity)

        }
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.img_back_btn -> {
                finish()
            }

            R.id.btnSubmit->{

                if( binding.txtFrom.text.isNullOrEmpty()){

                }
                else if ( binding.txtTo.text.isNullOrEmpty())
                {

                }
                else
                viewModel.getSubmittedByDates(binding.txtFrom.text.toString(),binding.txtTo.text.toString())

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


        }
    }

    override fun onErrorMessage(message: String) {
        CommonMethods.showToast(this, message)
    }

    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }


    override fun onResponseSuccess() {

    }

    override fun noListData() {

    }

    override fun selectedItem(from: String, mObj: GetSubmitedManagerModel) {

        /* CommonMethods.showAlertYesNoMessage(this, "Are you sure you want to $from this record") {


        }*/

        if (from == "view") {
            val intent = Intent(this, EmpTimeStatusReviewActivity::class.java)

            intent.putExtra("timeId", mObj.id)
            startActivity(intent)

        } else {
            showCustomDialog(this, "Are you sure you want to $from this record",
                onYesClickListener = { remarks ->
                    // Handle "Yes" click with remarks
                    viewModel.approvRejectRecord(from, mObj.id!!, mObj.projectId!!, remarks)
                },
                onNoClickListener = {

                }
            )

        }
    }


}