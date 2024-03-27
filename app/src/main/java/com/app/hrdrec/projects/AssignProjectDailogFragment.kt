package com.app.hrdrec.projects

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.app.hrdrec.R
import com.app.hrdrec.Url
import com.app.hrdrec.databinding.FragmentReasorceDialogBinding
import com.app.hrdrec.projects.employee_response.EmployeeListResponse
import com.app.hrdrec.projects.manager_response.ManagerListResponse
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssignProjectDailogFragment(val from:String) : DialogFragment(), View.OnClickListener, CommonInterface {
    private var _binding: FragmentReasorceDialogBinding? = null
    private val binding get() = _binding!!

    private val projectResourcesViewModel: ProjectViewModel by viewModels()

    var mListEmployees = arrayListOf<EmployeeListResponse>()

    var mListManagers = arrayListOf<ManagerListResponse>()

    private lateinit var employeeSpinner: EmployeeSpinnerAdapter
    private lateinit var managerSpinner: ManagerSpinnerAdapter

    var employeeId: Int = 0
    var managerId: Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentReasorceDialogBinding.inflate(inflater, container, false)
        binding.imgClose.setOnClickListener(this)
        binding.constCross.setOnClickListener(this)

        projectResourcesViewModel.setCallBacks(this)
        projectResourcesViewModel.getAllEmployees()

        projectResourcesViewModel.getAllManagers()


        if (from == "add") {
            binding.btnSaveProjectresource.text = "Save"
        } else {
            binding.btnSaveProjectresource.text = "Update"

         /*   val mObj = intent.getSerializableExtra("mObj") as ProjectData

            binding.apply {
                edtRole.setText(mObj.role)
                edtStartdate.setText(mObj.startDate)
                edtEnddate.setText(mObj.endDate)
                edtRemarks.setText(mObj.remarks)
                try {
                    id = mObj.projectId!!
                    employeeId = mObj.employeeId!!
                    managerId = mObj.projectManagerId!!

                } catch (_: Exception) {

                }
            }*/
        }


        setObserver()
        projectResourcesViewModel.getAllEmployees()

        projectResourcesViewModel.getAllManagers()

        binding.btnSubmit.setOnClickListener {

            if (binding.edtRole.text.toString().isEmpty()) {
                CommonMethods.showToast(requireContext(), "Please enter Role")
                return@setOnClickListener
            }

            if (binding.edtStartdate.text.toString().isEmpty()) {
                CommonMethods.showToast(requireContext(), "Please enter Startdate")
                return@setOnClickListener
            }

            if (binding.edtEnddate.text.toString().isEmpty()) {
                CommonMethods.showToast(requireContext(), "Please enter Enddate")
                return@setOnClickListener
            }

            if (binding.edtRemarks.text.toString().isEmpty()) {
                CommonMethods.showToast(requireContext(), "Please enter Remarks")
                return@setOnClickListener
            }

            if (from == "add") {
                val mObj = AddProjectResourcesPayload(
                    binding.edtRole.text.toString(),
                    binding.edtStartdate.text.toString(),
                    binding.edtEnddate.text.toString(),
                    binding.edtRemarks.text.toString(),
                    employeeId,
                    managerId,
                    projectResourcesViewModel.sharedPreferences.getInt(
                        Url.ORGANISATIONID
                    )
                )

                projectResourcesViewModel.addProjectResource(mObj)
            } else {
                val mObj = UpdateProjectResourcesPayload(
                    binding.edtRole.text.toString(),
                    binding.edtStartdate.text.toString(),
                    binding.edtEnddate.text.toString(),
                    binding.edtRemarks.text.toString(),
                    employeeId,
                    managerId,
                    projectResourcesViewModel.sharedPreferences.getInt(
                        Url.ORGANISATIONID
                    ),
                    id

                )
                projectResourcesViewModel.UpdateProjectResources(mObj)
            }
        }

        binding.employee.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {

                Log.e("mallal", "dds " + mListEmployees[position].employeeName)
                //    Log.e("mallal","dds "+ mListEmployees[position].employeeId)
                mListEmployees[position].employeeId?.let {
                    employeeId = it
                }
                // employeeId= mListEmployees[position].employeeId!!
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        binding.manager.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {

                Log.e("mallal", "dds " + mListManagers[position].managerName)
                //    Log.e("mallal","dds "+ mListEmployees[position].employeeId)
                mListManagers[position].managerId?.let {
                    managerId = it
                }
                // employeeId= mListEmployees[position].employeeId!!
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        return binding.root
    }

    private fun setObserver() {

        projectResourcesViewModel.allEmployeesResponseList.observe(this) {
            //mListEmployees = it
            mListEmployees.add(EmployeeListResponse(employeeName = "Select Employee"))
            mListEmployees.addAll(it)
            Log.e("sajkjdss ", " " + mListEmployees.size)
            employeeSpinner = EmployeeSpinnerAdapter(mListEmployees)
            binding.employee.adapter = employeeSpinner

            try {
                if (from == "edit") {
                    for (i in 0 until mListEmployees.size) {

                        if (mListEmployees[i].employeeId == employeeId) {
                            binding.employee.setSelection(i)
                        }
                    }

                }
            } catch (_: Exception) {

            }

        }

        projectResourcesViewModel.allManagerResponseList.observe(this) {
            //mListEmployees = it
            mListManagers.add(ManagerListResponse(managerName = "Select Manager"))
            mListManagers.addAll(it)
            Log.e("sajkjdss ", " " + mListManagers.size)
            managerSpinner = ManagerSpinnerAdapter(mListManagers)
            binding.manager.adapter = managerSpinner

            try {
                if (from == "edit") {
                    for (i in 0 until mListManagers.size) {

                        if (mListManagers[i].managerId == managerId) {
                            binding.manager.setSelection(i)
                        }
                    }

                }
            } catch (_: Exception) {

            }

        }

        projectResourcesViewModel.addLocaResponse.observe(this) {

            CommonMethods.showToast(requireContext(), "Added Successfully")
            /*val intent = Intent()
            setResult(121, intent)
            finish()*/
            dismiss()

        }


        projectResourcesViewModel.updateProjectResourcesResponse.observe(this) {

            CommonMethods.showToast(requireContext(), "Updated successfully")
          /*  val intent = Intent()
            setResult(121, intent)
            finish()*/
            dismiss()
        }

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
          R.id.imgClose->{

              dismiss()
          }


          R.id.constCross->{
              dismiss()
          }



      }
    }



    override fun onResponseSuccess() {

    }

    override fun noListData() {

    }
    override fun onErrorMessage(message: String) {
        CommonMethods.showToast(requireContext(), message)
    }

    override fun showLoader() {
        CommonMethods.showLoader(requireContext())
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }
}

