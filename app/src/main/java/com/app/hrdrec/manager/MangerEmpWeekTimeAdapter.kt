package com.app.hrdrec.manager


import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.LayoutAddWeekBinding
import com.app.hrdrec.timesheet.request_payload.TimesheetRowDTOs
import com.app.hrdrec.timesheet.response.project_response.ProjectEmployee


class MangerEmpWeekTimeAdapter(
    var mList: ArrayList<TimesheetRowDTOs>, /*private val spinnerClickListener: SpinnerClickListener*/
    val callBack: AddTimeCallBack
) : RecyclerView.Adapter<MangerEmpWeekTimeAdapter.FeatureHolder>() {

    var isUserInput: Boolean = true
    class FeatureHolder(val binding: LayoutAddWeekBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureHolder {
        val mBinding = LayoutAddWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeatureHolder(mBinding)

    }

    override fun getItemCount(): Int {

       return mList.size
       // return 1
    }

    // Define a map to associate each EditText with its corresponding property in TimesheetRowDTOs



    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: FeatureHolder, @SuppressLint("RecyclerView") position: Int) {
       // val mObj = mList[position]
        val timesheetRow = mList[position]

        // Set the project name
        holder.binding.edProjectName.setText(timesheetRow.projectName)
        holder.binding.edtManager.setText(timesheetRow.projectManagerName)
        holder.binding.txtStatus.text = timesheetRow.status
        holder.binding.edProjectName.visibility=View.VISIBLE
        // Set the day values
        holder.binding.edtTaskName.setText(timesheetRow.task)
        holder.binding.edtRemarks.setText(timesheetRow.remarks)
        holder.binding.edtTaskName.isEnabled=false
        holder.binding.edProjectName.isEnabled=false
        holder.binding.edtRemarks.isEnabled=false

        holder.binding.edtSunday.isEnabled=false
        holder.binding.edtMonday.isEnabled=false
        holder.binding.edtTuesday.isEnabled=false
        holder.binding.edtWednesday.isEnabled=false
        holder.binding.edtThursday.isEnabled=false
        holder.binding.edtFriday.isEnabled=false
        holder.binding.edtSaturday.isEnabled=false

        holder.binding.edtSunday.setText(timesheetRow.sunday?.toString() ?: "0")
        holder.binding.edtMonday.setText(timesheetRow.monday?.toString()?: "0")
        holder.binding.edtTuesday.setText(timesheetRow.tuesday?.toString()?: "0")
        holder.binding.edtWednesday.setText(timesheetRow.wednesday?.toString()?: "0")
        holder.binding.edtThursday.setText(timesheetRow.thursday?.toString()?: "0")
        holder.binding.edtFriday.setText(timesheetRow.friday?.toString()?: "0")
        holder.binding.edtSaturday.setText(timesheetRow.saturday?.toString()?: "0")

        holder.binding.imgClose.visibility=View.GONE
        holder.binding.spinnerProjectName.visibility=View.GONE

        
        holder.binding.imgClose.setOnClickListener {
            callBack.removeRow(timesheetRow)
        }













    }





  /*  private fun createTextWatcher(onTextChanged: (String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged.invoke(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }*/

    interface AddTimeCallBack {
         fun checkHours(it: Int)
        fun removeRow(it: TimesheetRowDTOs)
    }
}









