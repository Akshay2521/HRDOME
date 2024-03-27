package com.app.hrdrec.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.app.hrdrec.R
import com.app.hrdrec.admin.Admin
import com.app.hrdrec.databinding.ActivityHomeBinding
import com.app.hrdrec.expenses.Expenses
import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.login.Login
import com.app.hrdrec.utils.CommonMethods
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), HomeViewModel.CallBackLogin, View.OnClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController

    private lateinit var navHostFragment: NavHostFragment

    @Inject
    lateinit var albumDataAdapter: HomeNavDataAdapter

    var moduleSize:Int=0
    private val homeViewModel: HomeViewModel by viewModels()

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setNavigationController()
        binding.btnDrawer.setOnClickListener(this)
        binding.navHeader.icDownArrow.setOnClickListener(this)
        binding.navHeader.txtUserName.setOnClickListener(this)
        binding.navHeader.txtSignOut.setOnClickListener(this)
        binding.navHeader.imgCloseDrawer.setOnClickListener(this)
        binding.recyclerView.adapter = albumDataAdapter
        setObserver()
        homeViewModel.setCallBacks(this)
        homeViewModel.getModuleList()
        val current= CommonMethods.getCurrentDateTime()
        Log.e("Current","ss "+current)
        binding.navHeader.txtUserName.text=
            homeViewModel.sharedPreferences.getUserInfo()?.username ?:"Name"

        albumDataAdapter.setItemClick(object : ClickInterface<ModuleData> {
            override fun onClick(data: ModuleData) {
                Log.e("Data","onClick"+data.name)
                //AlbumDetailActivity.launchActivity(this@ShowAlbumActivity,data)
                when (data.name) {
                    "Organization" -> {
                        val bundle = bundleOf("moduleData" to data)
                        navController.navigate(R.id.organizationFragment, bundle)
                    }

                    "Users", "User Administration" -> {
                        val admin = Intent(this@HomeActivity, Admin::class.java)
                        admin.putExtra("mObj", data)
                        startActivity(admin)

                    }

                    "Expenses" -> {
                        val intent = Intent(this@HomeActivity, Expenses::class.java)
                        intent.putExtra("mObj", data)
                        startActivity(intent)
                    }

                    "Leave Management" -> {
                        val bundle = bundleOf("moduleData" to data)
                        navController.navigate(R.id.organizationFragment, bundle)

                    }

                    "Leaves" -> {
                       // navController.navigate(R.id.allLeavesFragment)
                        if(moduleSize==2||moduleSize==3) {
                           // navController.navigate(R.id.allLeavesFragment)
                            val bundle = bundleOf("moduleData" to data,"from" to "authLeave")
                            navController.navigate(R.id.managerAuthorisedFragment, bundle)
                        }
                        else{
                            navController.navigate(R.id.allLeavesFragment)
                           /* val bundle = bundleOf("moduleData" to data,"from" to "authLeave")
                            navController.navigate(R.id.managerAuthorisedFragment, bundle)*/

                        }

                    }
                    "Timesheets" -> {

                      //  navController.navigate(R.id.timeSchedulerFragment)
                        if(moduleSize==2||moduleSize==3) {
                            //navController.navigate(R.id.timeSchedulerFragment)
                            val bundle = bundleOf("moduleData" to data,"from" to "authTime")
                            navController.navigate(R.id.managerAuthorisedFragment, bundle)
                        }
                        else{
                            navController.navigate(R.id.timeSchedulerFragment)
                            /*val bundle = bundleOf("moduleData" to data,"from" to "authTime")
                            navController.navigate(R.id.managerAuthorisedFragment, bundle)*/
                        }

                    }

                    else -> {
                        val bundle = bundleOf("moduleData" to data)
                        navController.navigate(R.id.organizationFragment, bundle)
                    }


                }

                closeDrawer()

            }
        } )
    }

    private fun setNavigationController() {
        try {
            navHostFragment =
                (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment)
            navController = navHostFragment.navController

        } catch (e: Exception) {
        }
    }

    private fun setObserver() {
        homeViewModel.moduleData.observe(this) { mList ->
            moduleSize=mList.size
            albumDataAdapter.updateAlbumData(mList)
            //  val adapter=HomeNavDataAdapter(mList)

        }
    }

    override fun onErrorMessage(message: String) {

    }
    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }


    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }

    override fun onClick(v: View) {
       when(v.id){

          R.id.btnDrawer ->{
               openDrawer()

           }

         R.id.txt_user_name,  R.id.ic_down_arrow ->{
               if(binding.navHeader.linOption.visibility==View.GONE)
               binding.navHeader.linOption.visibility=View.VISIBLE
               else
                   binding.navHeader.linOption.visibility=View.GONE

           }

           R.id.imgCloseDrawer->{
               closeDrawer()
           }

           R.id.txtSignOut->
           {

               CommonMethods.showAlertYesNoMessage(this,"Are you sure you want to sign out"){

                   homeViewModel.sharedPreferences.clearPreference()

                   val intent = Intent(this, Login::class.java)
                   //  val intent = Intent(this, AddRoles::class.java)
                   startActivity(intent)
                   finish()
               }
           }

       }
    }

    private fun closeDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }
}