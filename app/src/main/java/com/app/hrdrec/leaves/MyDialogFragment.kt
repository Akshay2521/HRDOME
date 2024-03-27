package com.app.hrdrec.leaves

import androidx.fragment.app.DialogFragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.app.hrdrec.R
import com.app.hrdrec.databinding.FragmentMyDialogBinding
import com.app.hrdrec.utils.CommonDatesInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.utils.getDate


class MyDialogFragment(val listerner: CommonDatesInterface) : DialogFragment(), View.OnClickListener {
    private var _binding: FragmentMyDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyDialogBinding.inflate(inflater, container, false)
        binding.imgClose.setOnClickListener(this)
        binding.constCross.setOnClickListener(this)
        binding.conFromDate.setOnClickListener(this)
        binding.conToDate.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyDialogFragmentStyle)
    }

    override fun onResume() {
        super.onResume()
        val window = dialog?.window
        val params = window?.attributes
        params?.gravity = Gravity.TOP or Gravity.FILL_HORIZONTAL
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = params
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
      when(v.id){
          R.id.btnSubmit->{
              if(binding.txtFrom.text.toString().isNotEmpty()&&binding.txtTo.text.toString().isNotEmpty())

                  listerner.sendDates(binding.txtFrom.text.toString(),binding.txtTo.text.toString())
              dismiss()
          }


          R.id.constCross->{
              dismiss()
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

      }
    }
}

