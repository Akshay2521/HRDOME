package com.app.hrdrec.manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityAddTimeSheetBinding
import com.app.hrdrec.databinding.ActivityEmpTimeStatusReviewBinding
import com.app.hrdrec.timesheet.AddWeekTimeAdapter
import com.app.hrdrec.timesheet.request_payload.TimesheetRowDTOs
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmpTimeStatusReviewActivity : AppCompatActivity(), CommonInterface {

    private val binding by lazy { ActivityEmpTimeStatusReviewBinding.inflate(layoutInflater) }


    private val viewModel: ManagerReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.setCallBacks(this)
        setObserver()
        if (intent.hasExtra("timeId")) {
            viewModel.getSavedData(intent.getIntExtra("timeId", 0))
        }
    }


    private fun setObserver() {
        viewModel.mySavedResponse.observe(this) {
            Log.e("Data", "herrrr" + it)
            Log.e("getSavedData", "observe()" + it.timesheetRowDTOs.size)


           val addWeekTimeAdapter = MangerEmpWeekTimeAdapter( it.timesheetRowDTOs,object : MangerEmpWeekTimeAdapter.AddTimeCallBack{
                override fun checkHours(it: Int) {

                    }


                override fun removeRow(it: TimesheetRowDTOs) {

                }

            })
            binding.recyclerAddTime.adapter = addWeekTimeAdapter
            // }

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


}