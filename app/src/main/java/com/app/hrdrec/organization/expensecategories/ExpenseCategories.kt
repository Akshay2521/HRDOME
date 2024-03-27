package com.app.hrdrec.organization.expensecategories

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityExpenseCategoriesBinding
import com.app.hrdrec.organization.expensecategories.dataclass.AllExpenseCategoriesData
import com.app.hrdrec.organization.locations.LocationAdapter
import com.app.hrdrec.organization.locations.addlocation.AddNewLocation
import com.app.hrdrec.organization.locations.get_location_response.AllLocationData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpenseCategories : AppCompatActivity(), CommonInterface {

    private val binding by lazy { ActivityExpenseCategoriesBinding.inflate(layoutInflater) }
    private lateinit var recyclerView: RecyclerView
    private lateinit var expenseCategoriesAdapter: ExpenseCategoriesAdapter
    var id: Int = 1
    private val expenseCategoriesViewModel: ExpenseCategoriesViewModel by viewModels()
    var mList = arrayListOf<AllExpenseCategoriesData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        id = intent.getIntExtra("id", 1)
        expenseCategoriesViewModel.setCallBacks(this)

//        val addNewLocation = findViewById<FloatingActionButton>(R.id.createNewLocation)
//        addNewLocation.setOnClickListener {
//            val intent = Intent(this, AddNewLocation::class.java)
//            intent.putExtra("from", "add")
//            resultLauncher.launch(intent)
//        }

        initRecyclerView()
        expenseCategoriesViewModel.getAllExpenseCategories()
        expenseCategoriesViewModel.myResponseList.observe(this) {
            mList = it
            expenseCategoriesAdapter.setExpenseCategoriesData(mList)
            binding.expenseCategoriesRecyclerView.visibility = View.VISIBLE
        }

        expenseCategoriesViewModel.addExpenseCategoriesResponse.observe(this) {
            CommonMethods.showToast(this, "Deleted Successfully")
            expenseCategoriesViewModel.getAllExpenseCategories()
        }
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

    override fun onErrorMessage(message: String) {
        CommonMethods.showToast(this, message)
    }

    override fun onResponseSuccess() {
        TODO("Not yet implemented")
    }

    override fun noListData() {
        mList.clear()
        expenseCategoriesAdapter.notifyDataSetChanged()
    }

    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }

    fun filtered(text: String?) {
        val query = text?.lowercase()
        val temp: ArrayList<AllExpenseCategoriesData> = ArrayList()
        mList.forEach { d ->
            if (d.categoryName != null) {
                if (d.categoryName!!.lowercase().contains(query!!)) {
                    temp.add(d)
                }
            }
        }
        expenseCategoriesAdapter.setExpenseCategoriesData(temp)
    }

    private fun initRecyclerView() {
        expenseCategoriesAdapter = ExpenseCategoriesAdapter(mList) { mObj, from ->
            if (from == "delete") {
                CommonMethods.showAlertYesNoMessage(
                    this, "Are you sure you want to delete this record"
                ) {
//                    expenseCategoriesViewModel.deleteAddress(mObj.id!!)
                }
            } else {
                val intent = Intent(this, AddNewLocation::class.java)
                intent.putExtra("from", "edit")
                intent.putExtra("mObj", mObj)
                resultLauncher.launch(intent)
            }
        }
        binding.expenseCategoriesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ExpenseCategories)
            adapter = expenseCategoriesAdapter
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.e("Expense Categories Result", "Expense Categories Fetched" + result.resultCode)
            if (result.resultCode == 121) {
                Log.e("Expense Categories", "Success")
                try {
                    expenseCategoriesViewModel.getAllExpenseCategories()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            } else {
                Log.e("Expense Categories Result", "Expense Categories Is Not Fetched")
            }
        }
}