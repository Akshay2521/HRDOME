package com.app.hrdrec.organization

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.hrdrec.databinding.FragmentOrganizationBinding
import com.app.hrdrec.home.HomeViewModel
import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.home.getallmodules.Paths
import com.app.hrdrec.organization.clients.Clients
import com.app.hrdrec.organization.HolidayCalendars.HolidayCalendar
import com.app.hrdrec.organization.expensecategories.ExpenseCategories
import com.app.hrdrec.organization.leavetypes.LeaveTypes
import com.app.hrdrec.organization.locations.Location
import com.app.hrdrec.organization.organizationprofile.OrganizationProfile
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrganizationFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    lateinit var mObj: ModuleData
    private lateinit var binding: FragmentOrganizationBinding

    @Inject
    lateinit var albumDataAdapter: OrganizationDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        binding = FragmentOrganizationBinding.inflate(inflater, container, false)

        val args = arguments
        val mObj = args?.getSerializable("moduleData") as ModuleData

        binding.recyclerView.adapter = albumDataAdapter

        albumDataAdapter.updateAlbumData(mObj.paths)

        albumDataAdapter.setItemClick(object : ClickInterfaceOrgan<Paths> {
            override fun onClick(data: Paths) {
                Log.e("Data", "org" + data.name)
                //AlbumDetailActivity.launchActivity(this@ShowAlbumActivity,data)
                when (data.name) {

                    "Locations" -> {
                        val intent = Intent(requireContext(), Location::class.java)
                        intent.putExtra("id", mObj.id)
                        startActivity(intent)

                    }

                    "Holiday Calendars" -> {
                        val admin = Intent(requireContext(), HolidayCalendar::class.java)
                        admin.putExtra("mObj", data)
                        startActivity(admin)
                    }

                    "Clients" -> {
                        val intent = Intent(requireContext(), Clients::class.java)
                        intent.putExtra("id", mObj.id)
                        startActivity(intent)
                    }

                    "Expense Categories" -> {
                        val intent = Intent(requireContext(), ExpenseCategories::class.java)
                        intent.putExtra("id", mObj.id)
                        startActivity(intent)
                    }

                    "Projects" -> {
                        findNavController().navigate(OrganizationFragmentDirections.actionOrganizationToProject())
                    }

                    "Leave Types" -> {
                        val intent = Intent(requireContext(), LeaveTypes::class.java)
                        intent.putExtra("id", mObj.id)
                        startActivity(intent)

                    }

                    "Organization Profile" -> {
                        val intent = Intent(requireContext(), OrganizationProfile::class.java)
                        intent.putExtra("id", mObj.id)
                        startActivity(intent)

                    }


                    else -> {
                        // Handle other cases if needed
                    }
                }
            }
        })

        return binding.root
    }


}




