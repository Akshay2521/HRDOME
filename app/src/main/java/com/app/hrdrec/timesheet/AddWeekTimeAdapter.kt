package com.app.hrdrec.timesheet


import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.LayoutAddWeekBinding
import com.app.hrdrec.timesheet.request_payload.TimesheetRowDTOs
import com.app.hrdrec.timesheet.response.project_response.ProjectEmployee


class AddWeekTimeAdapter(
    var mList: ArrayList<TimesheetRowDTOs>, /*private val spinnerClickListener: SpinnerClickListener*/
   var mProjectList: ArrayList<ProjectEmployee>, val callBack: AddTimeCallBack
) : RecyclerView.Adapter<AddWeekTimeAdapter.FeatureHolder>() {

    var isUserInput: Boolean = true
    class FeatureHolder(val binding: LayoutAddWeekBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureHolder {
        val mBinding = LayoutAddWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeatureHolder(mBinding)

    }

    override fun getItemCount(): Int {

       return mList.size

    }



    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: FeatureHolder, @SuppressLint("RecyclerView") position: Int) {
       // val mObj = mList[position]
        val timesheetRow = mList[position]

        holder.binding.edProjectName.setText(timesheetRow.projectName)
       // holder.binding.edtManager.setText(timesheetRow.projectManagerName)
        holder.binding.txtStatus.text = timesheetRow.status
        Log.e("isUserInput","uppp "+isUserInput)
        val mondayTextWatcher = createTextWatcher { value ->
            timesheetRow.monday = value.toIntOrNull() ?: 0
Log.e("isUserInput","ss "+isUserInput)
            if (isUserInput) {
                value.toIntOrNull()?.let {
                    if(it<23)
                    callBack.checkHours(it)
                    else
                        holder.binding.edtMonday.setText("")

                }
            }
        }

        holder.binding.edtMonday.removeTextChangedListener(mondayTextWatcher)
        holder.binding.edtSunday.setText(timesheetRow.sunday?.toString() ?: "0")
        holder.binding.edtMonday.setText(timesheetRow.monday?.toString()?: "0")
        holder.binding.edtTuesday.setText(timesheetRow.tuesday?.toString()?: "0")
        holder.binding.edtWednesday.setText(timesheetRow.wednesday?.toString()?: "0")
        holder.binding.edtThursday.setText(timesheetRow.thursday?.toString()?: "0")
        holder.binding.edtFriday.setText(timesheetRow.friday?.toString()?: "0")
        holder.binding.edtSaturday.setText(timesheetRow.saturday?.toString()?: "0")

        holder.binding.edtMonday.addTextChangedListener(mondayTextWatcher)
        
        holder.binding.imgClose.setOnClickListener {
            callBack.removeRow(timesheetRow)
        }

        // Add TextChangedListeners for each day


        holder.binding.edtSunday.addTextChangedListener(createTextWatcher { value ->
            timesheetRow.sunday = value.toIntOrNull() ?: 0
            if (isUserInput) {
                value.toIntOrNull()?.let {
                    if(it<23)
                        callBack.checkHours(it)
                    else
                        holder.binding.edtSunday.setText("")


                }
            }


        })

      /*  holder.binding.edtMonday.addTextChangedListener(createTextWatcher { value ->
            timesheetRow.monday = value.toIntOrNull() ?: 0
            if (isUserInput) {
                value.toIntOrNull()?.let { callBack.checkHours(it) }
            }
        })*/

        holder.binding.edtTuesday.addTextChangedListener(createTextWatcher { value ->
            timesheetRow.tuesday = value.toIntOrNull() ?: 0
            if (isUserInput) {
                value.toIntOrNull()?.let {
                    if(it<23)
                        callBack.checkHours(it)
                    else
                        holder.binding.edtTuesday.setText("")

                }
            }
        })

        holder.binding.edtWednesday.addTextChangedListener(createTextWatcher { value ->
            timesheetRow.wednesday = value.toIntOrNull() ?: 0
            if (isUserInput) {
                value.toIntOrNull()?.let {

                    if(it<23)
                        callBack.checkHours(it)
                    else
                        holder.binding.edtWednesday.setText("")

                }
            }
        })

        holder.binding.edtThursday.addTextChangedListener(createTextWatcher { value ->
            timesheetRow.thursday = value.toIntOrNull() ?: 0
            if (isUserInput) {
                value.toIntOrNull()?.let {
                    if(it<23)
                        callBack.checkHours(it)
                    else
                        holder.binding.edtThursday.setText("")

                }
            }
        })

        holder.binding.edtFriday.addTextChangedListener(createTextWatcher { value ->
            timesheetRow.friday = value.toIntOrNull() ?: 0
            if (isUserInput) {
                value.toIntOrNull()?.let {
                    if(it<23)
                        callBack.checkHours(it)
                    else
                        holder.binding.edtFriday.setText("")

                }
            }
        })

        holder.binding.edtSaturday.addTextChangedListener(createTextWatcher { value ->
            timesheetRow.saturday = value.toIntOrNull() ?: 0
            if (isUserInput) {
                value.toIntOrNull()?.let {

                    if(it<23)
                        callBack.checkHours(it)
                    else
                        holder.binding.edtSaturday.setText("")

                }
            }
        })




        holder.binding.edProjectName.addTextChangedListener(createTextWatcher { value ->
            timesheetRow.projectName = value
        })

        holder.binding.edtTaskName.addTextChangedListener(createTextWatcher { value ->
            timesheetRow.task = value
        })

        holder.binding.edtManager.addTextChangedListener(createTextWatcher { value ->
            timesheetRow.projectManagerName = value
        })
        holder.binding.edtRemarks.addTextChangedListener(createTextWatcher { value ->
            timesheetRow.remarks = value
        })


        val spinnerAdapter = ProjectSpinnerAdapter(mProjectList)
        holder.binding.spinnerProjectName.adapter = spinnerAdapter

        // Handle Spinner item selection
        holder.binding.spinnerProjectName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, selectedItemPosition: Int, id: Long) {

              if(mProjectList[selectedItemPosition].projectId!=-1) {
                  val selectedItem = mProjectList[selectedItemPosition].projectName
                  val projectId = mProjectList[selectedItemPosition].projectId
                  // spinnerClickListener.onSpinnerItemSelected(position, selectedItem)
                  timesheetRow.projectName = selectedItem
                  holder.binding.edtManager.setText(mProjectList[selectedItemPosition].projectManagerName)
                  timesheetRow.projectId = projectId
              }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }




    private fun createTextWatcher(onTextChanged: (String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isUserInput) {
                    onTextChanged.invoke(s.toString())
                }

            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }

    interface AddTimeCallBack {
         fun checkHours(it: Int)
        fun removeRow(it: TimesheetRowDTOs)
    }
}









