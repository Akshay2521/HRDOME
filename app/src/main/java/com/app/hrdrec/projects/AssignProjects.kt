package com.app.hrdrec.projects

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.Url
import com.app.hrdrec.databinding.ActivityAssignProjectsBinding
import com.app.hrdrec.leaves.MyDialogFragment
import com.app.hrdrec.projects.clients_response.ClientListResponse

import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AssignProjects : AppCompatActivity(), CommonInterface {

    private val binding by lazy { ActivityAssignProjectsBinding.inflate(layoutInflater) }

    private val projectViewModel: ProjectViewModel by viewModels()

    var mListClients = arrayListOf<ClientListResponse>()

    private lateinit var clientSpinner: ClientSpinnerAdapter

    @Inject
    lateinit var albumDataAdapter: ProjectResourceDataAdapter
    var mList = arrayListOf<ProjectData>()

    var clientId: Int = 0
    var id: Int = 0
    var from = "add"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.adapter = albumDataAdapter
        binding.headerMain.txtTitle.text = "Assign Role to Particular Employee"
        projectViewModel.setCallBacks(this)
        binding.headerMain.imgBackBtn.setOnClickListener {
            finish()
        }
        from = intent.getStringExtra("from")!!
        if (from == "add") {
            binding.btnSaveProject.text = "Save"
        } else {
            binding.btnSaveProject.text = "Update"

            val mObj = intent.getSerializableExtra("mObj") as ProjectData
            projectViewModel.getProjectResourceRoles()
            binding.apply {
                edtProjectstatus.setText(mObj.projectStatusName)
                edtName.setText(mObj.projectName)
                edtDescription.setText(mObj.description)
                edtTechnology.setText(mObj.technology)
                edtExpectedstartdate
                edtExpectedenddate
                edtActualstartdate
                edtActualenddate
                try {
                    id = mObj.projectId!!
                    clientId = mObj.projectClientId!!

                } catch (_: Exception) {

                }
            }
        }


        setObserver()
        projectViewModel.getAllClients()

        binding.btnSaveProject.setOnClickListener {

            if (binding.edtProjectstatus.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Project status")
                return@setOnClickListener
            }

            if (binding.edtName.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Name")
                return@setOnClickListener
            }

            if (binding.edtDescription.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Description")
                return@setOnClickListener
            }

            if (binding.edtTechnology.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Technology")
                return@setOnClickListener
            }

            if (binding.edtExpectedstartdate.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Expected startdate")
                return@setOnClickListener
            }

            if (binding.edtExpectedenddate.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Expected enddate")
                return@setOnClickListener
            }

            if (binding.edtActualstartdate.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Actual startdate")
                return@setOnClickListener
            }

            if (binding.edtActualenddate.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Actual enddate")
                return@setOnClickListener
            }

            if (from == "add") {
                val mObj = AddProjectPayload(
                    binding.edtProjectstatus.text.toString(),
                    binding.edtName.text.toString(),
                    binding.edtDescription.text.toString(),
                    binding.edtTechnology.text.toString(),
                    binding.edtExpectedstartdate.text.toString(),
                    binding.edtExpectedenddate.text.toString(),
                    binding.edtActualstartdate.text.toString(),
                    binding.edtActualenddate.text.toString(),
                    clientId,
                    projectViewModel.sharedPreferences.getInt(
                        Url.ORGANISATIONID
                    )
                )

                projectViewModel.addProject(mObj)
            } else {
                val mObj = UpdateProjectPayload(
                    binding.edtProjectstatus.text.toString(),
                    binding.edtName.text.toString(),
                    binding.edtDescription.text.toString(),
                    binding.edtTechnology.text.toString(),
                    binding.edtExpectedstartdate.text.toString(),
                    binding.edtExpectedenddate.text.toString(),
                    binding.edtActualstartdate.text.toString(),
                    binding.edtActualenddate.text.toString(),
                    clientId,
                    projectViewModel.sharedPreferences.getInt(
                        Url.ORGANISATIONID
                    ),
                    id

                )
                projectViewModel.UpdateProject(mObj)
            }
        }

        binding.client.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {

                Log.e("mallal", "dds " + mListClients[position].name)
                //    Log.e("mallal","dds "+ mListEmployees[position].employeeId)
                mListClients[position].id?.let {
                    clientId = it
                }
                // employeeId= mListEmployees[position].employeeId!!
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        binding.imdAddResource.setOnClickListener {

            val dialogFragment = AssignProjectDailogFragment(from)
            dialogFragment.show(supportFragmentManager, "MyDialogFragment")
        }
    }


    private fun setObserver() {

        projectViewModel.allClientsResponseList.observe(this) {
            //mListEmployees = it
            mListClients.add(ClientListResponse(name = "Select client"))
            mListClients.addAll(it)
            Log.e("sajkjdss ", " " + mListClients.size)
            clientSpinner = ClientSpinnerAdapter(mListClients)
            binding.client.adapter = clientSpinner

            try {
                if (from == "edit") {
                    for (i in 0 until mListClients.size) {

                        if (mListClients[i].id == clientId) {
                            binding.client.setSelection(i)
                        }
                    }

                }
            } catch (_: Exception) {

            }

        }

        projectViewModel.addLocaResponse.observe(this) {

            CommonMethods.showToast(this, "Added Successfully")
            val intent = Intent()
            setResult(121, intent)
            finish()

        }


        projectViewModel.updateProjectResponse.observe(this) {

            CommonMethods.showToast(this, "Updated successfully")
            val intent = Intent()
            setResult(121, intent)
            finish()
        }

        projectViewModel.projectResourceList.observe(this) {
            Log.e("data", "xccc ")
            mList = it
            albumDataAdapter.updateAlbumData(it)
//            // it.data.let { it1 -> Log.d("main", it1.toString()) }
//            recyclerView.visibility = View.VISIBLE
        }

    }

    override fun onResponseSuccess() {

    }

    override fun noListData() {

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
}