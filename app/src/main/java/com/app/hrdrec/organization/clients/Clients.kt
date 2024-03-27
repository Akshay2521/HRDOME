package com.app.hrdrec.organization.clients

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityClientsBinding
import com.app.hrdrec.organization.clients.addclients.AddNewClients
import com.app.hrdrec.organization.clients.get_clients_response.AllClientsData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Clients : AppCompatActivity(), CommonInterface {
    private val binding by lazy { ActivityClientsBinding.inflate(layoutInflater) }

    //    private lateinit var locationRepository: LocationRepository
//    private lateinit var locationViewModelFactory: LocationViewModelFactory
    //private lateinit var locationViewModel: LocationViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: ClientsAdapter
    var id: Int = 1
    private val clientsViewModel: ClientsViewModel by viewModels()


    var mList = arrayListOf<AllClientsData>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //  setContentView(R.layout.activity_location)
        id = intent.getIntExtra("id", 1)
        clientsViewModel.setCallBacks(this)

        val addNewClients = findViewById<FloatingActionButton>(R.id.createNewClients)
        addNewClients.setOnClickListener {
            val intent = Intent(this, AddNewClients::class.java)
            intent.putExtra("from", "add")
            //startActivity(intent)
            resultLauncher.launch(intent)
        }

        initRecyclerView()



        clientsViewModel.getAllClients()

        clientsViewModel.myResponseList.observe(this) {
            mList = it
            postAdapter.setClientsData(mList)
            // it.data.let { it1 -> Log.d("main", it1.toString()) }
            binding.recyclerView.visibility = View.VISIBLE
        }

        clientsViewModel.addClientsResponse.observe(this) {
            CommonMethods.showToast(this, "Deleted Successfully")
            clientsViewModel.getAllClients()
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
        val temp: ArrayList<AllClientsData> = ArrayList()


        mList.forEach { d ->

            if (d.name != null) {
                if (d.name!!.lowercase().contains(query!!)) {
                    temp.add(d)
                }
            }
        }
        // if (temp.size == 0)
        postAdapter.setClientsData(temp)

    }


    private fun initRecyclerView() {
        //recyclerView = findViewById(R.id.recyclerView)
        postAdapter = ClientsAdapter(mList) { mObj, from ->

            if (from == "delete") {
                CommonMethods.showAlertYesNoMessage(
                    this,
                    "Are you sure you want to delete this record"
                ) {

                    clientsViewModel.deleteAddress(mObj.id!!)
                }
            } else {
                val intent = Intent(this, AddNewClients::class.java)
                intent.putExtra("from", "edit")
                intent.putExtra("mObj", mObj)
                //startActivity(intent)
                resultLauncher.launch(intent)
            }


        }
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@Clients)
            adapter = postAdapter
        }
    }


    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            Log.e("sasdsads", "Ixzx m massa" + result.resultCode)

            if (result.resultCode == 121) {
                // There are no request codes
                // val data: Intent? = result.data
                //  doSomeOperations()
                Log.e("sasdsads", "Im massa")
                try {
                    clientsViewModel.getAllClients()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            } else {
                Log.e("sasdsads", "Im else massa")
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
