package com.app.hrdrec.organization.HolidayCalendars

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.organization.HolidayCalendars.dataclass.AllHolidayCalendarData
import com.app.hrdrec.organization.locations.get_location_response.AllLocationData

class HolidayCalendarAdapter(
    private var holidayCalendarList: ArrayList<AllHolidayCalendarData>,
    private val recordCountTextView: TextView,
    val callBack: (mObj: AllHolidayCalendarData, from: String) -> Unit
) :
    RecyclerView.Adapter<HolidayCalendarAdapter.HolidayCalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayCalendarViewHolder =
        HolidayCalendarViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.holidaycalendar_row_list, parent, false)
        )
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolidayCalendarViewHolder, position: Int) {
        val holidayCalendar = holidayCalendarList[position]
        holder.locName.text = "Location Name : ${holidayCalendar.locationName}"
        holder.locationId.text = "Location ID : ${holidayCalendar.locationId}"
        holder.year.text = "Year : ${holidayCalendar.year}"

//        holder.imgDelete.setOnClickListener {
//            callBack(holidayCalendar, "delete")
//        }

        holder.imgEdit.setOnClickListener {
            callBack(holidayCalendar, "edit")
        }
    }

    override fun getItemCount(): Int = holidayCalendarList.size

    class HolidayCalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locName: TextView = itemView.findViewById(R.id.holidayCalendarLocationName)
        val locationId: TextView = itemView.findViewById(R.id.holidayCalendarLocationId)
        val year: TextView = itemView.findViewById(R.id.holidayCalendarYear)

//        val imgDelete: ImageView = itemView.findViewById(R.id.delete)
        val imgEdit: ImageView = itemView.findViewById(R.id.edit)
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    fun setHolidayCalendarData(locationList: ArrayList<AllHolidayCalendarData>) {
        this.holidayCalendarList = locationList
        notifyDataSetChanged()
        recordCountTextView.text = "No. of Records: ${holidayCalendarList.size}"
    }

}