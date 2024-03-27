package com.app.hrdrec.organization.HolidayCalendars

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityHolidayCalendarBinding
import com.app.hrdrec.organization.HolidayCalendars.addHolidayCalendar.AddHolidayCalendar
import com.app.hrdrec.organization.HolidayCalendars.dataclass.AllHolidayCalendarData
import com.app.hrdrec.organization.locations.addlocation.AddNewLocation
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HolidayCalendar : AppCompatActivity(), CommonInterface{

    private val binding by lazy { ActivityHolidayCalendarBinding.inflate(layoutInflater) }
    private lateinit var recyclerView: RecyclerView
    private lateinit var holidayCalendarAdapter: HolidayCalendarAdapter
    var id: Int = 1
    private val holidayCalendarViewModel: HolidayCalendarViewModel by viewModels()
    var mList = arrayListOf<AllHolidayCalendarData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        id = intent.getIntExtra("id", 1)
        holidayCalendarViewModel.setCallBacks(this)


        val addNewHolidayCalendar = findViewById<FloatingActionButton>(R.id.createNewCalendar)
        addNewHolidayCalendar.setOnClickListener {
            val intent = Intent(this, AddHolidayCalendar::class.java)
            intent.putExtra("from", "add")
            resultLauncher.launch(intent)
        }

        initRecyclerView()

        holidayCalendarViewModel.getAllHolidayCalendars()
        holidayCalendarViewModel.myResponseList.observe(this) {
            mList = it
            holidayCalendarAdapter.setHolidayCalendarData(mList)
            binding.holidayCalendarsRecyclerView.visibility = View.VISIBLE
        }

        holidayCalendarViewModel.addHolidayCalendarResponse.observe(this) {
            CommonMethods.showToast(this, "Deleted Successfully")
            holidayCalendarViewModel.getAllHolidayCalendars()
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


    private fun initRecyclerView() {
        val recordCountTextView = findViewById<TextView>(R.id.textViewRecordCount)
        holidayCalendarAdapter = HolidayCalendarAdapter(mList, recordCountTextView) { mObj, from ->
                val intent = Intent(this, AddNewLocation::class.java)
                intent.putExtra("from", "edit")
                intent.putExtra("mObj", mObj)
                resultLauncher.launch(intent)
        }
        binding.holidayCalendarsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HolidayCalendar)
            adapter = holidayCalendarAdapter
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.e("Holiday Calendar Result", "Holiday Calendar Fetched" + result.resultCode)
            if (result.resultCode == 121) {
                Log.e("Holiday Calendar", "Success")
                try {
                    holidayCalendarViewModel.getAllHolidayCalendars()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            } else {
                Log.e("Holiday Calendar Result", "Holiday Calendar Is Not Fetched")
            }
        }

    fun filtered(text: String?) {
        val query = text?.lowercase()
        val temp: ArrayList<AllHolidayCalendarData> = ArrayList()
        mList.forEach { d ->
            if (d.locationName != null) {
                if (d.locationName!!.lowercase().contains(query!!)) {
                    temp.add(d)
                }
            }
        }
        holidayCalendarAdapter.setHolidayCalendarData(temp)
    }

    override fun onErrorMessage(message: String) {
        CommonMethods.showToast(this, message)
    }

    override fun onResponseSuccess() {
        TODO("Not yet implemented")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun noListData() {
        mList.clear()
        holidayCalendarAdapter.notifyDataSetChanged()
    }

    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }
}