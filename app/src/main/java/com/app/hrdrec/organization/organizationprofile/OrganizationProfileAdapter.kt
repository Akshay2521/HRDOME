package com.app.hrdrec.organization.organizationprofile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.organization.organizationprofile.get_organizationprofile_response.AllOrganizationProfileData

class OrganizationProfileAdapter(
    private var organizationProfileList: ArrayList<AllOrganizationProfileData>,
    val callBack: (mObj: AllOrganizationProfileData, from: String) -> Unit
) :
    RecyclerView.Adapter<OrganizationProfileAdapter.OrganizationProfileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationProfileViewHolder =
        OrganizationProfileViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.organizationprofile_recycler_row_list, parent, false)
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrganizationProfileViewHolder, position: Int) {
        val organizationProfile = organizationProfileList[position]
        holder.organizationName.text = " OrganizationName : ${organizationProfile.name}"
        holder.website.text = " Website : ${organizationProfile.website}"
        holder.foundationDate.text = " FoundationDate : ${organizationProfile.foundationDate}"
        holder.gratitudePeriod.text = " gratitudePeriod : ${organizationProfile.gratitudePeriod}"
        holder.passwordExpiryDays.text = " PasswordExpiryDays : ${organizationProfile.noOfDaysForPasswordExpiry}"
        holder.maxInvalidLoginAttempts.text = " maxInvalidLoginAttempts : ${organizationProfile.maxNoOfAllowedLoginAttempts}"
//        holder.logo.text = " logo : ${OrganizationProfile.logo}"
        holder.locationName.text = " locationName : ${organizationProfile.locationName}"
        holder.country.text = "country : ${organizationProfile.countryName}"
        holder.address1.text = " address1 : ${organizationProfile.address1}"
        holder.address2.text = "address2 : ${organizationProfile.address2}"
        holder.city.text = "city : ${organizationProfile.city}"
        holder.state.text = " state : ${organizationProfile.stateName}"
        holder.zipCode.text = "zipCode : ${organizationProfile.zipCode}"
        holder.firstName.text = "firstName : ${organizationProfile.firstName}"
        holder.lastName.text = "lastName : ${organizationProfile.lastName}"
        holder.username.text = "username : ${organizationProfile.username}"
        holder.email.text = "email : ${organizationProfile.email}"
        holder.alternateEmail.text = "alternateEmail : ${organizationProfile.alternateEmail}"
        holder.phoneNumber.text = "phoneNumber : ${organizationProfile.phoneNumber}"
        holder.alternatePhoneNumber.text = "alternatePhoneNumber : ${organizationProfile.alternatePhoneNumber}"

        holder.imgDelete.setOnClickListener {
            callBack(organizationProfile, "delete")
        }

        holder.imgEdit.setOnClickListener {
            callBack(organizationProfile, "edit")
        }
    }

    override fun getItemCount(): Int = organizationProfileList.size

    class OrganizationProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val organizationName: TextView = itemView.findViewById(R.id.textViewOrganizationName)
        val website: TextView = itemView.findViewById(R.id.textViewWebsite)
        val foundationDate: TextView = itemView.findViewById(R.id.textViewFoundationDate)
        val gratitudePeriod: TextView = itemView.findViewById(R.id.textViewGratitudePeriod)
        val passwordExpiryDays: TextView = itemView.findViewById(R.id.textViewPasswordExpiryDays)
        val maxInvalidLoginAttempts: TextView = itemView.findViewById(R.id.textViewMaxInvalidLoginAttempts)
        //        val logo: TextView = itemView.findViewById(R.id.textViewLogo)
        val locationName: TextView = itemView.findViewById(R.id.textOrganizationProfileViewLocationName)
        val country: TextView = itemView.findViewById(R.id.textViewCountry)
        val address1: TextView = itemView.findViewById(R.id.textViewAddress1)
        val address2: TextView = itemView.findViewById(R.id.textViewAddress2)
        val city: TextView = itemView.findViewById(R.id.textViewCity)
        val state: TextView = itemView.findViewById(R.id.textViewState)
        val zipCode: TextView = itemView.findViewById(R.id.textViewZipCode)
        val firstName: TextView = itemView.findViewById(R.id.textViewFirstName)
        val lastName: TextView = itemView.findViewById(R.id.textViewLastName)
        val username: TextView = itemView.findViewById(R.id.textViewUsername)
        val email: TextView = itemView.findViewById(R.id.textViewOrganizationProfileEmail)
        val alternateEmail: TextView = itemView.findViewById(R.id.textViewAlternateEmail)
        val phoneNumber: TextView = itemView.findViewById(R.id.textViewPhoneNumber)
        val alternatePhoneNumber: TextView = itemView.findViewById(R.id.textViewAlternatePhoneNumber)

        val imgDelete: ImageView = itemView.findViewById(R.id.delete)
        val imgEdit: ImageView = itemView.findViewById(R.id.edit)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOrganizationProfileData(organizationProfileList: ArrayList<AllOrganizationProfileData>) {
        this.organizationProfileList = organizationProfileList
        notifyDataSetChanged()
    }
}