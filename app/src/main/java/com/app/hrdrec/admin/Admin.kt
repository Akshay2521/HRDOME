package com.app.hrdrec.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.admin.roles.Roles
import com.app.hrdrec.admin.users.Users
import com.app.hrdrec.databinding.ActivityAdminBinding
import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.home.getallmodules.Paths
import com.app.hrdrec.organization.ClickInterfaceOrgan
import com.app.hrdrec.organization.OrganizationDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Admin : AppCompatActivity() {

    lateinit var mObj: ModuleData

    private val binding by lazy { ActivityAdminBinding.inflate(layoutInflater) }

    @Inject
    lateinit var albumDataAdapter: OrganizationDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_admin)
        setContentView(binding.root)

        mObj = intent.getSerializableExtra("mObj") as ModuleData
        Log.e("data", "$mObj")
        binding.recyclerView.adapter = albumDataAdapter
        albumDataAdapter.updateAlbumData(mObj.paths)

        albumDataAdapter.setItemClick(object : ClickInterfaceOrgan<Paths> {
            override fun onClick(data: Paths) {
                //AlbumDetailActivity.launchActivity(this@ShowAlbumActivity,data)
                when (data.name) {
                    "Roles" -> {
                        val intent = Intent(this@Admin, Roles::class.java)
                        intent.putExtra("id", mObj.id)
                        startActivity(intent)

                    }

                    "Users" -> {
                        val admin = Intent(this@Admin, Users::class.java)
                        intent.putExtra("mObj", mObj)
                        startActivity(admin)

                    }


                    else -> {

//                        val intent = Intent(this@Organization, Organization::class.java)
//                        intent.putExtra("mObj",data)
//                        startActivity(intent)
                    }


                }

            }
        })


        /*  val roles = findViewById<ImageButton>(R.id.roles)
          roles.setOnClickListener {
              val intent = Intent(this, Roles::class.java)
              startActivity(intent)
          }

          val users = findViewById<ImageButton>(R.id.users)
          users.setOnClickListener {
              val intent = Intent(this, Users::class.java)
              startActivity(intent)
          }*/
    }
}