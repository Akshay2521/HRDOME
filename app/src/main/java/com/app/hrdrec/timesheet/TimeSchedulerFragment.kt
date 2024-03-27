package com.app.hrdrec.timesheet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.hrdrec.R
import com.app.hrdrec.databinding.FragmentTimeSchedulerBinding
import com.app.hrdrec.leaves.MyDialogFragment
import com.app.hrdrec.timesheet.request_payload.AddTimeModal
import com.app.hrdrec.utils.CommonDatesInterface
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.utils.CommonMethods.getCurrentDateTime
import com.app.hrdrec.utils.CommonMethods.selectAnyDate
import com.app.hrdrec.utils.getDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeSchedulerFragment : Fragment(), CommonInterface, View.OnClickListener,
    TimeSchudeListAdapter.FeatureCallBack, CommonDatesInterface {

    private val timeSheetViewModel: TimeSheetViewModel by viewModels()
    private var productFeatureAdapter: TimeSchudeListAdapter? = null

    private val binding by lazy { FragmentTimeSchedulerBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        timeSheetViewModel.setCallBacks(this)
        setObserver()
        timeSheetViewModel.getTimesheetscheduler()
        binding.headerMain.txtTitle.text = "TimeSheet"
        setUpListerner()

        return binding.root

    }

    private fun setObserver() {
        timeSheetViewModel.myResponseList.observe(viewLifecycleOwner) {
            Log.e("Data", "herrrr" + it.data.size)
            binding.txtNoRecords.text="Number of Records:"+" "+it.data.size
            binding.recyclerTimeSchedule.visibility=View.VISIBLE
            binding.txtNoData.visibility=View.GONE
            productFeatureAdapter = TimeSchudeListAdapter(it.data, this)
            binding.recyclerTimeSchedule.adapter = productFeatureAdapter
        }

        timeSheetViewModel.addClientsResponse.observe(viewLifecycleOwner) {
            it.message?.let { it1 -> CommonMethods.showToast(requireContext(), it1)
                CommonMethods.hideLoader()
            }

        }


    }

    private fun setUpListerner() {
        binding.apply {
            headerMain.imgBackBtn.setOnClickListener(this@TimeSchedulerFragment)
            addTimeSheet.setOnClickListener(this@TimeSchedulerFragment)
            conFromDate.setOnClickListener(this@TimeSchedulerFragment)
            conToDate.setOnClickListener(this@TimeSchedulerFragment)
            btnSubmit.setOnClickListener(this@TimeSchedulerFragment)
            imgFilter.setOnClickListener(this@TimeSchedulerFragment)
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 121) {
                timeSheetViewModel.getTimesheetscheduler()
            } else {
                Log.e("Location Result", "Location Is Not Fetched")
            }
        }
    override fun onErrorMessage(message: String) {
        CommonMethods.showToast(requireContext(), message)
    }

    override fun onResponseSuccess() {

    }

    override fun noListData() {
        binding.recyclerTimeSchedule.visibility=View.GONE
        binding.txtNoData.visibility=View.VISIBLE
    }

    override fun showLoader() {
        CommonMethods.showLoader(requireContext())
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.imgFilter->{

                val dialogFragment = MyDialogFragment(this)
                dialogFragment.show(childFragmentManager, "MyDialogFragment")
            }

            R.id.addTimeSheet -> {
//                val intent = Intent(requireContext(), AddTimeSheetActivity::class.java)
//                intent.putExtra("selectedWeek", "")
//                startActivity(intent)
                val current=getCurrentDateTime()
                val model= AddTimeModal()
                model.weekendDate=current
                model.totalHours=0.0
                model.status="Saved"
                val data = timeSheetViewModel.sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                model.employeeId=empId
                model.organizationId=data?.organizationId
                model.locationId=data?.locationId
                Log.e("modell","ss"+model)
                timeSheetViewModel.addTimesheetscheduler(model)
            }

            R.id.img_back_btn -> {
                findNavController().popBackStack()
            }

            R.id.conFromDate -> {

                val datePicker = selectAnyDate(null)
                datePicker.show(childFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                    binding.txtFrom.text = getDate(it)
                }
            }

            R.id.conToDate -> {

                if(binding.txtFrom.text.toString().isNotEmpty()) {
                    val datePicker = selectAnyDate(null)
                    datePicker.show(childFragmentManager, "DatePicker")
                    datePicker.addOnPositiveButtonClickListener {
                        //  binding.txtTo.text = getDate(it)

                        if (CommonMethods.validateDate(
                                binding.txtFrom.text.toString(),
                                getDate(it)
                            )
                        ) {

                            binding.txtTo.text = getDate(it)
                        } else {
                            println("End date is not after start date.")
                            CommonMethods.showToast(requireContext(), "To date is not after start date.")
                        }
                    }
                }
                else{
                    CommonMethods.showToast(requireContext(), "Please select from first")
                }



            }

            R.id.btnSubmit -> {


                if(binding.txtFrom.text.toString().isNotEmpty()&&binding.txtTo.text.toString().isNotEmpty())

                timeSheetViewModel.getTimesheetschedulerDates(binding.txtFrom.text.toString(),binding.txtTo.text.toString())
            }

        }
    }

    override fun selectedItem(selectedWeek: String, id: Int?) {
        val intent = Intent(requireContext(), AddTimeSheetActivity::class.java)
        intent.putExtra("selectedWeek", selectedWeek)
        intent.putExtra("timeId", id)
      //  startActivity(intent)
        resultLauncher.launch(intent)
    }

    override fun sendDates(from: String, to: String) {
        timeSheetViewModel.getTimesheetschedulerDates(from,to)
    }
}
