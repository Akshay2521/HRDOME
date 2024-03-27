package com.app.hrdrec.manager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.hrdrec.databinding.FragmentManagerAuthorisedBinding
import com.app.hrdrec.home.ClickInterface
import com.app.hrdrec.home.ModuleDataAdapter
import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.leaves.AllLeavesFragment
import com.app.hrdrec.timesheet.TimeSchedulerFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ManagerAuthorisedFragment : Fragment() {
    var from:String=""

    @Inject
    lateinit var albumDataAdapter: ModuleDataAdapter

    private val binding by lazy { FragmentManagerAuthorisedBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        from=intent.getStringExtra("from")!!
//        val mObj = intent.getSerializableExtra("mObj") as ModuleData
        val args = arguments
        val mObj = args?.getSerializable("moduleData") as ModuleData
        from= args.getString("from", "") ?: ""
        setAdapter(mObj)
        binding.headerMain.imgBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

return binding.root
        Log.e("mObj","mObj "+mObj)

    }

    private fun setAdapter(mObj: ModuleData) {
        binding.recyclerView.adapter = albumDataAdapter
        var list= ArrayList<ModuleData>()
        mObj.paths.forEach {
            list.add(ModuleData(name = it.name))
        }

       /* if(from == "authLeave") {
            list.add(ModuleData(name = "Authorized"))
            list.add(ModuleData(name = "Leaves"))
        }
        else{
            list.add(ModuleData(name = "Authorized"))
            list.add(ModuleData(name = "Timesheets"))
        }*/
        albumDataAdapter.updateAlbumData(list)


        albumDataAdapter.setItemClick(object : ClickInterface<ModuleData> {
            override fun onClick(data: ModuleData) {
                Log.e("Data","onClick"+data.name)
                //AlbumDetailActivity.launchActivity(this@ShowAlbumActivity,data)
                when (data.name) {


                   "Submit", "Leaves" -> {
                       if(from == "authLeave") {
                         /*  val intent =
                               Intent(requireContext(), AllLeavesFragment::class.java)
                           intent.putExtra("mObj", data)
                           startActivity(intent)*/
                           findNavController().navigate(ManagerAuthorisedFragmentDirections.actionManagerAuthorisedFragmentToAllLeavesFragment())

                       }
                       else{
                          /* val intent =
                               Intent(requireContext(), TimeSchedulerFragment::class.java)
                           intent.putExtra("mObj", data)
                           startActivity(intent)*/
                           findNavController().navigate(ManagerAuthorisedFragmentDirections.actionManagerAuthorisedFragmentToTimeSchedulerFragment())
                       }

                    }
                    "Authorize Timesheets", "Authorize Leaves", "Authorized" -> {

                        if(from == "authLeave") {
                            val intent =
                                Intent(
                                    requireContext(),
                                    MangerLeaveReviewActivity::class.java
                                )
                            intent.putExtra("mObj", data)
                            startActivity(intent)
                        }
                        else{
                            val intent =
                                Intent(
                                    requireContext(),
                                    ManagerTimeSheetActivity::class.java
                                )
                            intent.putExtra("mObj", data)
                            startActivity(intent)
                        }

                    }

                   "Review"  -> {
                        val intent =
                            Intent(requireContext(), ReviewActivity::class.java)
                        intent.putExtra("mObj", data)
                        startActivity(intent)

                    }


                }

            }
        })


    }
}