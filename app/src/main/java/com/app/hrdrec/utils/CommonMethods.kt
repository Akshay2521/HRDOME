package com.app.hrdrec.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.widget.Toast
import com.app.hrdrec.R
import com.app.hrdrec.databinding.CustomDialogLayoutBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object CommonMethods {

    private var loader: Loader? = null

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

    fun getJsonRequestBody(jsonObject: JSONObject): RequestBody {
        val body: RequestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(), jsonObject.toString()
        )
        return body

    }

    fun isValidEmail(str: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    fun showLoader(context: Context?) {
        hideLoader()
        if (context != null) {
            try {
                loader = Loader(context)
                loader?.let { loader ->
                    loader.setCanceledOnTouchOutside(true)
                    loader.setCancelable(false)
                    loader.show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun hideLoader() {
        if (loader != null && loader?.isShowing!!) {
            loader = try {
                loader?.dismiss()
                null
            } catch (e: Exception) {
                null
            }
        }
    }

    fun showToast(mContext: Context, message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

    }


    private var networkDialog: Dialog? = null


    fun hideNetworkDialog() {
        networkDialog?.hide()
    }

    fun showCustomDialog(context: Context,message: String, onYesClickListener: (String) -> Unit, onNoClickListener: () -> Unit) {
        val binding = CustomDialogLayoutBinding.inflate(LayoutInflater.from(context))

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(binding.root)

        val alertDialog = alertDialogBuilder.create()

        binding.txtConfirmation.text=message

        binding.buttonYes.setOnClickListener {
            val remarks = binding.editTextRemarks.text.toString()
            onYesClickListener.invoke(remarks)
            alertDialog.dismiss()
        }

        binding.buttonNo.setOnClickListener {
            onNoClickListener.invoke()
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    fun showAlertMessage(mContext: Context, message: String, clickAction: () -> Unit) {

        MaterialAlertDialogBuilder(mContext).setTitle(mContext.getString(R.string.alert))
            .setMessage(message)
            .setPositiveButton(mContext.getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                clickAction()
            }.show()

    }

    fun showAlertYesNoMessage(mContext: Context, message: String, clickAction: () -> Unit) {

        MaterialAlertDialogBuilder(mContext).setTitle(mContext.getString(R.string.alert))
            .setMessage(message)
            .setPositiveButton(mContext.getString(R.string.yes)) { dialog, _ ->
                dialog.dismiss()
                clickAction()
            }

            .setNegativeButton(mContext.getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()

            }.show()

    }


    @Throws(JSONException::class)
    fun <T> getUserClassData(key: JSONObject, dataOFSET: Class<T>?): T {
        val gson = Gson()
        return gson.fromJson(key.toString(), dataOFSET as Type?)
    }

    @Throws(JSONException::class)
    fun <T> getDataAsList(jsonArray: JSONArray, dataOFSET: Class<T>?): ArrayList<T> {
        val list = ArrayList<T>()
        try {
            //val jsonArray = response.getJSONArray(key!!)
            val gson = Gson()
            for (position in 0 until jsonArray.length()) {
                list.add(gson.fromJson(jsonArray.get(position).toString(), dataOFSET))
            }
        } catch (_: Exception) {
        }
        return list
    }

    fun getResponseError(errorBody: String?): String {
        val jsonObject = errorBody?.let { JSONObject(it) }
        return jsonObject?.get("status_message").toString()
    }

    fun getResponseCode(errorBody: String?): String {
        val jsonObject = errorBody?.let { JSONObject(it) }
        return jsonObject?.get("status_code").toString()
    }

    fun showToastFrag(mConect: Context, message: String) {
//Toast.makeText()
        Toast.makeText(mConect, message, Toast.LENGTH_SHORT).show()
    }

    fun getCurrentDateTime(): String {
        try {
            val time = Calendar.getInstance()
            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            return formatter.format(time.time)
        } catch (ex: ParseException) {
            ex.printStackTrace()
            // Handle the parsing exception here if needed
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return ""
    }

    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    fun selectDatePrevios(date: String? = null): MaterialDatePicker<Long> {

        val sdf = SimpleDateFormat(DATE_FORMAT)

        // Set startDate to one year ago from today
        val startDate = MaterialDatePicker.todayInUtcMilliseconds() - TimeUnit.DAYS.toMillis(365)

        // Set endDate to today
        val endDate = MaterialDatePicker.todayInUtcMilliseconds()

        val listValidators = arrayListOf(
            DateValidatorPointForward.from(startDate),
            DateValidatorPointBackward.before(endDate)
        )

        val constraintsBuilder = CalendarConstraints.Builder().apply {
            setStart(startDate)
            setEnd(endDate)
            setOpenAt(endDate) // Show today's date by default
            setValidator(CompositeDateValidator.allOf(listValidators))
        }.build()

        return MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select a date")
            .setTheme(R.style.MaterialCalendarTheme)
            .setCalendarConstraints(constraintsBuilder)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        }


    fun getDaysDifference(startDate: String, endDate: String): Long {
        val sdf = SimpleDateFormat(DATE_FORMAT)

        try {
            // Parse the input strings to Date objects
            val startDateObj: Date = sdf.parse(startDate)!!
            val endDateObj: Date = sdf.parse(endDate)!!

            // Calculate the difference in milliseconds
            val diffInMillis: Long = endDateObj.time - startDateObj.time

            // Convert milliseconds to days
            return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS)
        } catch (e: Exception) {
            // Handle parsing exceptions
            e.printStackTrace()
        }

        // Return -1 in case of an error
        return -1
    }


    fun validateDate(startDate: String, endDate: String): Boolean {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

        try {
            val startDateObj = dateFormat.parse(startDate)
            val endDateObj = dateFormat.parse(endDate)

            return startDateObj.before(endDateObj)
        } catch (e: Exception) {
            // Handle parsing exceptions if necessary
            e.printStackTrace()
        }

        // If there is an exception or dates are equal, return false
        return false
    }


    fun selectAnyDate(date: String? = null): MaterialDatePicker<Long> {
        val sdf = SimpleDateFormat(DATE_FORMAT)
        val startDate = if (!date.isNullOrEmpty()) sdf.parse(date)?.time!!
        else MaterialDatePicker.todayInUtcMilliseconds()

        return MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select a date")
            .setTheme(R.style.MaterialCalendarTheme)
            .setSelection(startDate)
            .build()
    }


    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    fun selectDate(date: String? = null): MaterialDatePicker<Long> {
        val sdf = SimpleDateFormat(DATE_FORMAT)
        val startDate = if (!date.isNullOrEmpty()) sdf.parse(date)?.time!!
        else MaterialDatePicker.todayInUtcMilliseconds()

        val endDate = Calendar.getInstance().apply { add(Calendar.YEAR, +2) }.time.time
        val listValidators = arrayListOf(
            DateValidatorPointForward.from(startDate),
            DateValidatorPointBackward.before(endDate)
        )
        val constraintsBuilder = CalendarConstraints.Builder().apply {
            setStart(startDate)
            setEnd(endDate)
            setOpenAt(startDate)
            setValidator(CompositeDateValidator.allOf(listValidators))
        }.build()
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select a date")
            .setTheme(R.style.MaterialCalendarTheme)
            .setCalendarConstraints(constraintsBuilder)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }
}

fun getDate(l: Long) = SimpleDateFormat(DATE_FORMAT, Locale.US).format(l)!!