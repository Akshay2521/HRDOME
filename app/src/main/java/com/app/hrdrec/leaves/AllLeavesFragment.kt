package com.app.hrdrec.leaves

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
import com.app.hrdrec.databinding.FragmentAllLeavesBinding
import com.app.hrdrec.leaves.response.LeaveModel
import com.app.hrdrec.utils.CommonDatesInterface
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.utils.getDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllLeavesFragment : Fragment(), CommonInterface,
    AllLeaveListAdapter.FeatureCallBack, View.OnClickListener, CommonDatesInterface {

    private val viewModel: LeavesViewModel by viewModels()
    private var productFeatureAdapter: AllLeaveListAdapter? = null

    private val binding by lazy { FragmentAllLeavesBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.setCallBacks(this);
        setObserver()
        binding.headerMain.txtTitle.text = "Leaves"
        viewModel.getAllLeaves()

        setUpListerner()

        return binding.root

    }

    private fun setUpListerner() {
        binding.apply {
            addLeaves.setOnClickListener(this@AllLeavesFragment)
            headerMain.imgBackBtn.setOnClickListener(this@AllLeavesFragment)
            conFromDate.setOnClickListener(this@AllLeavesFragment)
            conToDate.setOnClickListener(this@AllLeavesFragment)
            btnSubmit.setOnClickListener(this@AllLeavesFragment)
            imgFilter.setOnClickListener(this@AllLeavesFragment)
        }

    }

    private fun setObserver() {


        viewModel.myResponseList.observe(viewLifecycleOwner) {
                Log.e("Data", "herrrr" + it.data.size)
            binding.txtNoRecords.text="Number of Records:"+" "+it.data.size
            binding.recyclerLeaves.visibility=View.VISIBLE
            binding.txtNoData.visibility=View.GONE
                productFeatureAdapter = AllLeaveListAdapter(it.data, this)
                binding.recyclerLeaves.adapter = productFeatureAdapter
            }

    }
    override fun onClick(v: View) {
        when (v.id) {


            R.id.addLeaves -> {

                val intent = Intent(requireContext(), AddLeaveActivity::class.java)
                intent.putExtra("from","add")
                //startActivity(intent)
                resultLauncher.launch(intent)
            }

            R.id.img_back_btn->{
                findNavController().popBackStack()
            }

            R.id.conFromDate -> {

                val datePicker = CommonMethods.selectAnyDate(null)
                datePicker.show(childFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                    binding.txtFrom.text = getDate(it)
                }
            }

            R.id.conToDate -> {
                if(binding.txtFrom.text.toString().isNotEmpty()) {
                val datePicker = CommonMethods.selectAnyDate(null)
                datePicker.show(childFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                   // binding.txtTo.text = getDate(it)
                    if (CommonMethods.validateDate(binding.txtFrom.text.toString(), getDate(it))) {

                        binding.txtTo.text = getDate(it)
                    } else {
                        println("End date is not after start date.")
                        CommonMethods.showToast(requireContext(),"To date is not after start date.")
                    }
                }
                }
                else{
                    CommonMethods.showToast(requireContext(), "Please select from first")
                }

            }

            R.id.btnSubmit -> {


                if(binding.txtFrom.text.toString().isNotEmpty()&&binding.txtTo.text.toString().isNotEmpty())

                    viewModel.getAllLeavesDates(binding.txtFrom.text.toString(),binding.txtTo.text.toString())
            }

            R.id.imgFilter->{

                val dialogFragment = MyDialogFragment(this)
                dialogFragment.show(childFragmentManager, "MyDialogFragment")
            }
        }
    }

    override fun onErrorMessage(message: String) {

    }

    override fun onResponseSuccess() {

    }

    override fun noListData() {
       // mList.clear()
        binding.recyclerLeaves.visibility=View.GONE
        binding.txtNoData.visibility=View.VISIBLE
        CommonMethods.hideLoader()

    }

    override fun showLoader() {
        CommonMethods.showLoader(requireContext())
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }

    override fun selectedItem(week: LeaveModel) {
        val intent = Intent(requireContext(), AddLeaveActivity::class.java)
        intent.putExtra("from","edit")
        intent.putExtra("mObj",week)
        //startActivity(intent)
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 121) {
                viewModel.getAllLeaves()
            } else {
                Log.e("Location Result", "Location Is Not Fetched")
            }
        }

    override fun sendDates(from: String, to: String) {
        viewModel.getAllLeavesDates(from,to)
    }


}