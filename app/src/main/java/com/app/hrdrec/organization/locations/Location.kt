package com.app.hrdrec.organization.locations

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityLocationBinding
import com.app.hrdrec.organization.locations.addlocation.AddNewLocation
import com.app.hrdrec.organization.locations.get_location_response.AllLocationData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Location : AppCompatActivity(), CommonInterface {

    private val binding by lazy { ActivityLocationBinding.inflate(layoutInflater) }

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: LocationAdapter
    var id: Int = 1
    private val locationViewModel: LocationViewModel by viewModels()
    var mList = arrayListOf<AllLocationData>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //  setContentView(R.layout.activity_location)
        id = intent.getIntExtra("id", 1)
        locationViewModel.setCallBacks(this)

        val addNewLocation = findViewById<FloatingActionButton>(R.id.createNewLocation)
        addNewLocation.setOnClickListener {
            val intent = Intent(this, AddNewLocation::class.java)
            intent.putExtra("from", "add")
            //startActivity(intent)
            resultLauncher.launch(intent)
        }

        initRecyclerView()



        locationViewModel.getAllLocation()

        locationViewModel.myResponseList.observe(this) {
            mList = it
            postAdapter.setLocationData(mList)
            // it.data.let { it1 -> Log.d("main", it1.toString()) }
            binding.recyclerView.visibility = View.VISIBLE
        }

        locationViewModel.addLocaResponse.observe(this) {
            CommonMethods.showToast(this, "Deleted Successfully")
            locationViewModel.getAllLocation()
        }

        //direct getting arraylist values... see return it values as per send viewmodel
//        locationViewModel.myResponseList.observe(this, Observer {
//            postAdapter.setLocationData(it as ArrayList<MainData>)
//            recyclerView.visibility = View.VISIBLE
//        })

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    filtered(s.toString())
                } else {
                    filtered(s.toString())
                }


            }
        })
    }

    fun filtered(text: String?) {

        // Log.e("Check",""+mList.size)
        val query = text?.lowercase()
        val temp: ArrayList<AllLocationData> = ArrayList()


        mList.forEach { d ->

            if (d.locationName != null) {
                if (d.locationName!!.lowercase().contains(query!!)) {
                    temp.add(d)
                }
            }
        }
        // if (temp.size == 0)
        postAdapter.setLocationData(temp)

    }


    private fun initRecyclerView() {
        //recyclerView = findViewById(R.id.recyclerView)
        postAdapter = LocationAdapter(mList) { mObj, from ->

            if (from == "delete") {
                CommonMethods.showAlertYesNoMessage(
                    this,
                    "Are you sure you want to delete this record"
                ) {

                    locationViewModel.deleteAddress(mObj.id!!)
                }
            } else {
                val intent = Intent(this, AddNewLocation::class.java)
                intent.putExtra("from", "edit")
                intent.putExtra("mObj", mObj)
                //startActivity(intent)
                resultLauncher.launch(intent)
            }
        }
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@Location)
            adapter = postAdapter
        }
    }


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.e("Location Result", "Location Fetched" + result.resultCode)
            if (result.resultCode == 121) {
                // There are no request codes
                // val data: Intent? = result.data
                //  doSomeOperations()
                Log.e("Location", "Success")
                try {
                    locationViewModel.getAllLocation()
               } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            } else {
                Log.e("Location Result", "Location Is Not Fetched")
            }
        }

    override fun onResponseSuccess() {

    }

    override fun noListData() {
        mList.clear()
        postAdapter.notifyDataSetChanged()

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