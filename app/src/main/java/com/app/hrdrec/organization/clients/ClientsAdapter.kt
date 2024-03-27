package com.app.hrdrec.organization.clients

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.organization.clients.get_clients_response.AllClientsData

class ClientsAdapter(
    private var clientsList: ArrayList<AllClientsData>,
    val callBack: (mObj: AllClientsData, from: String) -> Unit
) :
    RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder =
        ClientsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.clients_recycler_row_list, parent, false)
        )


    //Make nulls as blank.
    // Remove Id's
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val clients = clientsList[position]
        holder.name.text = " Name : ${clients.name}"
        holder.email.text = "Email : ${clients.email}"
        holder.locationName.text = "Address 2 : ${clients.locationName}"

        holder.imgDelete.setOnClickListener {
            callBack(clients, "delete")
        }

        holder.imgEdit.setOnClickListener {
            callBack(clients, "edit")
        }
    }

    override fun getItemCount(): Int = clientsList.size

    class ClientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textViewName)
        val email: TextView = itemView.findViewById(R.id.textViewEmail)
        val locationName: TextView = itemView.findViewById(R.id.textViewLocationName)

        val imgDelete: ImageView = itemView.findViewById(R.id.delete)
        val imgEdit: ImageView = itemView.findViewById(R.id.edit)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setClientsData(clientsList: ArrayList<AllClientsData>) {
        this.clientsList = clientsList
        notifyDataSetChanged()
    }

    /* fun updateList(temp: ArrayList<AllLocationData>) {
     locationList = temp

     notifyDataSetChanged()
 }*/
}