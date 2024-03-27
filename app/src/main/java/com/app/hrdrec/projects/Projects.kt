package com.app.hrdrec.projects

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.hrdrec.databinding.ActivityProjectsBinding
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Projects : Fragment(), CommonInterface {


    private val binding by lazy { ActivityProjectsBinding.inflate(layoutInflater) }

    @Inject
    lateinit var albumDataAdapter: ProjectDataAdapter
    var mList = arrayListOf<ProjectData>()

    private val projectViewModel: ProjectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        projectViewModel.setCallBacks(this)
        // id=intent.getIntExtra("id",1)
        binding.recyclerView.adapter = albumDataAdapter
        setObserver()
        projectViewModel.getProjectRoles()

        binding.assignProject.setOnClickListener {
            val intent = Intent(requireContext(), AssignProjects::class.java)
            intent.putExtra("from","add")
            resultLauncher.launch(intent)
            // startActivity(intent)
        }

        albumDataAdapter.setItemClick(object : ClickInterfaceProjects<ProjectData> {
            override fun onClick(data: ProjectData) {
                val intent = Intent(requireContext(), AssignProjects::class.java)
                intent.putExtra("from","edit")
                intent.putExtra("mObj",data)
                resultLauncher.launch(intent)

            }

            override fun onClickDelete(data: ProjectData) {
                CommonMethods.showAlertYesNoMessage(requireContext(),"Are you sure you want to delete this record"){

                    data.projectClientId?.let { projectViewModel.deleteProject(it) }
                }
            }

        } )



        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) {
                    filtered(s.toString())
                } else {
                    filtered(s.toString())
                }
            }
        })

        return binding.root
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == 121) {
            try {
                projectViewModel.getProjectRoles()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }



    }

  /*  @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_users)
        setContentView(binding.root)

    }*/

    fun filtered(text: String?) {

        Log.e("Check",""+ text)
        val query = text?.lowercase()
        val temp: ArrayList<ProjectData> = ArrayList()


        mList.forEach { d ->
            if (d.projectName!!.lowercase().contains(query!!)) {
                temp.add(d)
            }
        }
        // if (temp.size == 0)
        albumDataAdapter.updateList(temp)

    }

    private fun setObserver() {
        projectViewModel.myResponseList.observe(viewLifecycleOwner) {
            Log.e("data", "xccc ")
            mList = it
            albumDataAdapter.updateAlbumData(it)
//            // it.data.let { it1 -> Log.d("main", it1.toString()) }
//            recyclerView.visibility = View.VISIBLE
        }

        projectViewModel.addLocaResponse.observe(viewLifecycleOwner) {

            CommonMethods.showToast(requireContext(), "Deleted Successfully")
            projectViewModel.getProjectRoles()
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