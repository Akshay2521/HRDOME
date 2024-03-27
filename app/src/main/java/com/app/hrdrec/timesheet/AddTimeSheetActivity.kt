package com.app.hrdrec.timesheet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityAddTimeSheetBinding
import com.app.hrdrec.timesheet.request_payload.AddTimeModal
import com.app.hrdrec.timesheet.request_payload.TimesheetRowDTOs
import com.app.hrdrec.timesheet.response.project_response.ProjectEmployee
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTimeSheetActivity : AppCompatActivity(), View.OnClickListener, CommonInterface {

    private val timeSheetViewModel: TimeSheetViewModel by viewModels()

    lateinit var addWeekTimeAdapter: AddWeekTimeAdapter

    private val binding by lazy { ActivityAddTimeSheetBinding.inflate(layoutInflater) }


    var mList = arrayListOf<TimesheetRowDTOs>()

    var mProjectList = arrayListOf<ProjectEmployee>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        timeSheetViewModel.setCallBacks(this)
        //mProjectList.add(ProjectEmployee(na))
        setObserver()
    //    setweekTimeAdapter()
          timeSheetViewModel.getAllProjects()

        setUpListerner()


    }

    private fun setObserver() {
        timeSheetViewModel.mySavedResponse.observe(this) {
            Log.e("Data", "herrrr" + it)
            Log.e("getSavedData", "observe()" + it.timesheetRowDTOs.size)


            Log.e("getSavedData", "projectList()" + mProjectList.size)
           // if (it.timesheetRowDTOs.size > 0) {
            mList = if (it.timesheetRowDTOs.size > 0) {
                it.timesheetRowDTOs
            } else {
                getEmptyFirst()
            }


                addWeekTimeAdapter = AddWeekTimeAdapter(mList,mProjectList,object :AddWeekTimeAdapter.AddTimeCallBack{
                    override fun checkHours(it: Int) {
                       if (it > 8) {
                            CommonMethods.showAlertMessage(
                                this@AddTimeSheetActivity,
                                "Are you sure you have worked for more than 8 hours"
                            ) {

                            }
                        }
                    }

                    override fun removeRow(it: TimesheetRowDTOs) {
                        mList.remove(it)
                        addWeekTimeAdapter.isUserInput = false
                        addWeekTimeAdapter.notifyDataSetChanged()
                        Handler(Looper.getMainLooper()).postDelayed({
                            Log.e("ddddd","sss "+addWeekTimeAdapter.isUserInput)
                            addWeekTimeAdapter.isUserInput=true
                        }, 2000)


                    }

                })
                binding.recyclerAddTime.adapter = addWeekTimeAdapter
           // }

        }

        timeSheetViewModel.projectEmployeeList.observe(this) {
            Log.e("Data", "herrrr" + it)
            mProjectList.add(ProjectEmployee(projectName = "Select Project",projectId=-1))
            mProjectList.addAll(it)

            if (intent.hasExtra("timeId")) {
                timeSheetViewModel.getSavedData(intent.getIntExtra("timeId", 0))
            }
            }


        timeSheetViewModel.addClientsResponse.observe(this) {
            it.statusMessage?.let { it1 -> CommonMethods.showToast(this, it1)
                CommonMethods.hideLoader()
                val intent = Intent()
                setResult(121, intent)
                finish()
            }

        }

        }
    private fun setUpListerner() {
        binding.apply {
            btnSave.setOnClickListener(this@AddTimeSheetActivity)
            btnSubmit.setOnClickListener(this@AddTimeSheetActivity)
            btnCancel.setOnClickListener(this@AddTimeSheetActivity)
            btnAddMore.setOnClickListener(this@AddTimeSheetActivity)
            headerMain.imgBackBtn.setOnClickListener(this@AddTimeSheetActivity)

        }
    }

   /* private fun setweekTimeAdapter() {

        mList = getEmptyFirst()
        addWeekTimeAdapter = AddWeekTimeAdapter(mList, mProjectList,object :AddWeekTimeAdapter.AddTimeCallBack{
            override fun checkHours(it: Int) {
                if (it > 8) {
                    CommonMethods.showAlertMessage(
                        this@AddTimeSheetActivity,
                        "Are you sure you have worked for more than 8 hours"
                    ) {

                    }
                }
            }

            override fun removeRow(it: TimesheetRowDTOs) {
                mList.remove(it)
                addWeekTimeAdapter.isUserInput=false
                addWeekTimeAdapter.notifyDataSetChanged()
                Handler(Looper.getMainLooper()).postDelayed({
                    Log.e("ddddd","sss "+addWeekTimeAdapter.isUserInput)
                    addWeekTimeAdapter.isUserInput=true
                }, 2000)

            }

        })
        binding.recyclerAddTime.adapter = addWeekTimeAdapter
    }*/

    private fun getEmptyFirst(): ArrayList<TimesheetRowDTOs> {

        val list = arrayListOf<TimesheetRowDTOs>()

        val mObj = TimesheetRowDTOs()
        mObj.sunday = 0
        mObj.monday = 0
        list.add(mObj)
        return list
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.btnSave -> {
                val model = AddTimeModal()
                model.weekendDate = intent.getStringExtra("selectedWeek")
                model.totalHours = getTotalsHours()
                model.status = "Saved"
                val data = timeSheetViewModel.sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                model.employeeId = empId
                model.organizationId = data?.organizationId

                model.locationId = data?.locationId

//               model.createdBy=data?.employeeId
//               model.modifiedBy=data?.employeeId

                model.timesheetRowDTOs = mList

                Log.e("Data", "ddd" + model)
                timeSheetViewModel.submitTimeSheet(model)

            }

            R.id.btnSubmit -> {

                val model = AddTimeModal()
                model.weekendDate = intent.getStringExtra("selectedWeek")
                model.totalHours = getTotalsHours()
                model.status = "Submitted"
                val data = timeSheetViewModel.sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                model.employeeId = empId
                model.organizationId = data?.organizationId
                model.locationId = data?.locationId
                Log.e("mList", "ss" + mList)
//               model.createdBy=data?.employeeId
//               model.modifiedBy=data?.employeeId
                mList.forEach { it.status = "Submitted" }
                model.timesheetRowDTOs = mList
                model.id = intent.getIntExtra("timeId", 0)
                Log.e("Data", "ddd" + model)
                timeSheetViewModel.submitTimeSheet(model)


            }

            R.id.btnCancel -> {

                finish()
            }
            R.id. btnAddMore -> {
                addWeekTimeAdapter.isUserInput=false
                mList.addAll(getEmptyFirst())
                addWeekTimeAdapter.notifyDataSetChanged()

                Handler(Looper.getMainLooper()).postDelayed({
                    Log.e("ddddd","sss "+addWeekTimeAdapter.isUserInput)
                    addWeekTimeAdapter.isUserInput=true
                }, 1000)

               // addWeekTimeAdapter.isUserInput=true


            }

            R.id.img_back_btn->{
                finish()
            }



        }
    }

    private fun getTotalsHours(): Double? {
        var total = 0.0
        mList.forEach {
            total = (it.monday?.toDouble() ?: 0.0) + (it.tuesday?.toDouble()
                ?: 0.0) + (it.wednesday?.toDouble() ?: 0.0) + (it.thursday?.toDouble()
                ?: 0.0) + (it.friday?.toDouble() ?: 0.0) + (it.saturday?.toDouble()
                ?: 0.0) + (it.sunday?.toDouble() ?: 0.0)
        }

        return total
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


