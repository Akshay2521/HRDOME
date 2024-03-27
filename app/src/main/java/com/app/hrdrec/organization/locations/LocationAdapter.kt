package com.app.hrdrec.organization.locations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.organization.locations.get_location_response.AllLocationData

class LocationAdapter(
    private var locationList: ArrayList<AllLocationData>,
    val callBack: (mObj: AllLocationData, from: String) -> Unit
) :
    RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder =
        LocationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_list, parent, false)
        )
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locationList[position]
        holder.locName.text = "Location Name : ${location.locationName}"
        holder.address1.text = "Address 1 : ${location.address1}"
        holder.address2.text = "Address 2 : ${location.address2}"
        holder.city.text = "City : ${location.city}"
        holder.state.text = "State : ${location.stateName}"
        holder.country.text = "Country : ${location.countryName}"
        holder.zipCode.text = "ZipCode : ${location.zipCode}"
        holder.headOffice.text = "Head Office : ${location.headOffice}"
        holder.deleted.text = "Deleted : ${location.deleted}"
        holder.orgName.text = "Organization Name : ${location.organizationName}"

        holder.imgDelete.setOnClickListener {
            callBack(location, "delete")
        }

        holder.imgEdit.setOnClickListener {
            callBack(location, "edit")
        }
    }

    override fun getItemCount(): Int = locationList.size

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locName: TextView = itemView.findViewById(R.id.textViewlocationName)
        val address1: TextView = itemView.findViewById(R.id.textViewaddress1)
        val address2: TextView = itemView.findViewById(R.id.textViewaddress2)
        val city: TextView = itemView.findViewById(R.id.textViewcity)
        val state: TextView = itemView.findViewById(R.id.textViewstate)
        val country: TextView = itemView.findViewById(R.id.textViewcountry)
        val zipCode: TextView = itemView.findViewById(R.id.textViewzipCode)
        val headOffice: TextView = itemView.findViewById(R.id.textViewheadOffice)
        val deleted: TextView = itemView.findViewById(R.id.textViewdeleted)
        val orgName: TextView = itemView.findViewById(R.id.textVieworganizationName)
        val imgDelete: ImageView = itemView.findViewById(R.id.delete)
        val imgEdit: ImageView = itemView.findViewById(R.id.edit)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setLocationData(locationList: ArrayList<AllLocationData>) {
        this.locationList = locationList
        notifyDataSetChanged()
    }

    /* fun updateList(temp: ArrayList<AllLocationData>) {
         locationList = temp

         notifyDataSetChanged()
     }*/
}