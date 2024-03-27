package com.app.hrdrec.leaves

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityAddLeaveBinding
import com.app.hrdrec.leaves.response.AddLeave
import com.app.hrdrec.leaves.response.LeaveModel
import com.app.hrdrec.leaves.response.LeaveType
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.utils.CommonMethods.getCurrentDateTime
import com.app.hrdrec.utils.CommonMethods.getDaysDifference
import com.app.hrdrec.utils.SAVED
import com.app.hrdrec.utils.getDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLeaveActivity : AppCompatActivity(), View.OnClickListener, CommonInterface {

    private val binding by lazy { ActivityAddLeaveBinding.inflate(layoutInflater) }
    private val viewModel: LeavesViewModel by viewModels()

    var leaveType: String = ""

    var fromSession: String = "FirstSession"
    var toSession: String = "SecondSession"


    var leaveTypeId: Int = 0

    private lateinit var employeeSpinner: LeaveTypeSpinnerAdapter

    var mListEmployees = arrayListOf<LeaveType>()

    var leaveApplyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.headerMain.txtTitle.text = "Apply Leave"

        val from = intent.getStringExtra("from")!!

        if (from == "edit") {

            val mObj = intent.getSerializableExtra("mObj") as LeaveModel

            fromSession = mObj.fromSession!!
            toSession = mObj.fromSession!!

           /* if (fromSession != "FirstSession") {
                binding.radioGroupFirst.visibility = View.VISIBLE

                if (fromSession.equals("FirstSession"))
                    binding.radioButtonFomMor.isChecked = true
                else
                    binding.radioButtonFomAft.isChecked = true
            }

            if (toSession != "FirstSession") {
                binding.radioGroupSecond.visibility = View.VISIBLE

                if (toSession.equals("FirstSession"))
                    binding.radioButtonSecondMor.isChecked = true
                else
                    binding.radioButtonSecondAft.isChecked = true
            }*/

            binding.edtReason.setText(mObj.reason)
            binding.txtFrom.text = mObj.fromDate
            binding.txtTo.text = mObj.toDate
            leaveType = mObj.leavetypeName.toString()
            leaveTypeId = mObj.leavetypeId!!
            leaveApplyId = mObj.id!!


        }


        setObserver()
        setListerner()
        viewModel.setCallBacks(this)
        viewModel.getLeaveTypes()


        setSpinner()

    }

    private fun setSpinner() {
        binding.spinnerLeaveType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long
                ) {

                    Log.e("mallal", "dds " + mListEmployees[position].name)
                    //    Log.e("mallal","dds "+ mListEmployees[position].employeeId)
                    mListEmployees[position].id?.let {
                        //employeeId = it

                        leaveTypeId = it
                        Log.e("leave", "ss " + leaveTypeId)

                    }

                    mListEmployees[position].name?.let {
                        //employeeId = it

                        leaveType = it
                        Log.e("leave", "ss " + leaveTypeId)

                    }
                    // employeeId= mListEmployees[position].employeeId!!
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
    }

    private fun setObserver() {


        viewModel._allLeaveTpyeList.observe(this) {
            Log.e("Data", "herrrr" + it.size)

            mListEmployees.add(LeaveType(name = "Select Leave Type"))
            mListEmployees.addAll(it)
            Log.e("sajkjdss ", " " + mListEmployees.size)
            employeeSpinner = LeaveTypeSpinnerAdapter(mListEmployees)
            binding.spinnerLeaveType.adapter = employeeSpinner

            if (leaveType != "") {
                for (i in 0 until mListEmployees.size) {

                    if (mListEmployees[i].name == leaveType) {
                        binding.spinnerLeaveType.setSelection(i)
                    }
                }
            }

        }

        viewModel.addClientsResponse.observe(this) {
            it.statusMessage?.let { it1 ->
                CommonMethods.hideLoader()
                CommonMethods.showAlertMessage(
                    this,
                    it1
                ) {
                    val intent = Intent()
                    setResult(121, intent)
                    finish()
                }
            }

        }
        viewModel.mLeaveBalance.observe(this) {

            var balance: Double = 0.0
            if (it.data.size > 0) {

             /*   it.data.forEach {
                    balance = balance + it.remaining!!
                }
                binding.txtBalance.visibility = View.VISIBLE
                binding.txtBalance.text = "You have $balance left"*/

                val bottomSheetFragment = LeaveShowBottomSheet(it.data)
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }

        }


    }

    private fun setListerner() {
        binding.apply {
            btnSave.setOnClickListener(this@AddLeaveActivity)
            btnSubmit.setOnClickListener(this@AddLeaveActivity)
            btnCancel.setOnClickListener(this@AddLeaveActivity)
            conFromDate.setOnClickListener(this@AddLeaveActivity)
            conToDate.setOnClickListener(this@AddLeaveActivity)
            headerMain.imgBackBtn.setOnClickListener(this@AddLeaveActivity)
            txtHalfFistDay.setOnClickListener(this@AddLeaveActivity)
            txtHalfSecondDay.setOnClickListener(this@AddLeaveActivity)
            txtCheckBalance.setOnClickListener(this@AddLeaveActivity)

            binding.radioGroupFirst.setOnCheckedChangeListener { group, checkedId ->
                // Get the selected RadioButton's text
                val selectedRadioButton = findViewById<RadioButton>(checkedId)
                when (selectedRadioButton.id) {
                    R.id.radioButtonFomMor -> {
                        Log.e("Selec", "assasa")
                        fromSession = "FirstSession"
                    }

                    R.id.radioButtonFomAft -> {
                        Log.e("Selec", "assazzsa")
                        fromSession = "SecondSession"
                    }
                }

                binding.radioGroupSecond.setOnCheckedChangeListener { group, checkedId ->
                    // Get the selected RadioButton's text
                    val selectedRadioButton = findViewById<RadioButton>(checkedId)
                    when (selectedRadioButton.id) {
                        R.id.radioButtonSecondMor -> {
                            Log.e("Selec", "assazzzzsa")
                            toSession = "FirstSession"
                        }

                        R.id.radioButtonSecondAft -> {
                            toSession = "SecondSession"
                            Log.e("Selec", "assasa")
                        }

                    }
                }
                //val selectedValue = selectedRadioButton.text.toString()

                // Now 'selectedValue' contains either "Yes" or "No"
                // You can use this value as needed
            }
        }
    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.txtCheckBalance -> {
                viewModel.checkLeaveBalance()
            }

            R.id.img_back_btn -> {
                finish()
            }

            R.id.txtHalfFistDay -> {

                binding.radioGroupFirst.visibility = View.VISIBLE
                if (binding.txtHalfFistDay.text.equals(getString(R.string.half_day))) {
                    binding.radioGroupFirst.visibility = View.VISIBLE
                    binding.txtHalfFistDay.text = getString(R.string.full_day)
                } else {
                    binding.radioGroupFirst.visibility = View.GONE
                    binding.txtHalfFistDay.text = getString(R.string.half_day)
                    fromSession="FirstSession"
                }
            }

            R.id.txtHalfSecondDay -> {

                if (binding.txtHalfSecondDay.text.equals(getString(R.string.half_day))) {
                    binding.txtHalfSecondDay.text = getString(R.string.full_day)
                    binding.radioGroupSecond.visibility = View.VISIBLE
                } else {
                    binding.radioGroupSecond.visibility = View.GONE
                    binding.txtHalfSecondDay.text = getString(R.string.half_day)
                    toSession = "FirstSession"



                }
            }

            R.id.conFromDate -> {

                val datePicker = CommonMethods.selectDate(null)
                datePicker.show(supportFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                    binding.txtFrom.text = getDate(it)
                }
            }

            R.id.conToDate -> {
                val datePicker = CommonMethods.selectDate(null)
                datePicker.show(supportFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                    binding.txtTo.text = getDate(it)
                }
            }


            R.id.btnSave -> {
                val model = AddLeave()
                model.fromDate = binding.txtFrom.text.toString()
                model.toDate = binding.txtTo.text.toString()

                val daysDifference = getDaysDifference(
                    binding.txtFrom.text.toString(),
                    binding.txtTo.text.toString()
                )
                Log.e("daysDiffernce", "ss" + daysDifference)
                if (daysDifference >= 0) {
                    model.numberofDays = daysDifference.toInt()
                } else {
                    model.numberofDays = 0
                }
                model.reason = binding.edtReason.text.toString()
                model.status = SAVED


                model.leavetypeId = leaveTypeId

                val data = viewModel.sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                model.employeeId = empId
                model.organizationId = data?.organizationId
                model.fromSession = fromSession
                model.toSession = toSession
                if (data != null) {
                    model.employeeName = data.firstName
                }
                model.leavetypeName = leaveType
                model.organizationName = data?.organizationName
                model.submittedDate= getCurrentDateTime()

                Log.e("data", "model " + model)
                viewModel.applyLeave(model)


            }


            R.id.btnSubmit -> {
                val model = AddLeave()

                model.fromDate = binding.txtFrom.text.toString()
                model.toDate = binding.txtTo.text.toString()

                val daysDifference = getDaysDifference(
                    binding.txtFrom.text.toString(),
                    binding.txtTo.text.toString()
                )
                Log.e("daysDiffernce", "ss" + daysDifference)
                if (daysDifference >= 0) {
                    model.numberofDays = daysDifference.toInt()
                } else {
                    model.numberofDays = 0
                }


                model.reason = binding.edtReason.text.toString()
                model.status = "Submitted"


                model.leavetypeId = leaveTypeId

                val data = viewModel.sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                model.employeeId = empId
                model.organizationId = data?.organizationId
                model.fromSession = fromSession
                model.toSession = toSession
                if (data != null) {
                    model.employeeName = data.firstName
                }
                model.leavetypeName = leaveType
                model.organizationName = data?.organizationName
                model.id = leaveApplyId
                model.submittedDate= getCurrentDateTime()
                Log.e("data", "model " + model)
                viewModel.applyLeave(model)

            }

            R.id.btnCancel -> {

                finish()
            }


        }

    }

    override fun onErrorMessage(message: String) {
        CommonMethods.hideLoader()
        CommonMethods.showToast(this,message)
    }

    override fun onResponseSuccess() {
        CommonMethods.hideLoader()
    }

    override fun noListData() {
        CommonMethods.hideLoader()
    }

    override fun showLoader() {
        CommonMethods.showLoader(this)

    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }
}