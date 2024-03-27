package com.app.hrdrec.expenses.ExpenseAdd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.app.hrdrec.expenses.ExpenseData

class SpinnerDataAdapter(context: Context, private val resourceLayout: Int, private val expenseDataList: List<ExpenseData>, private val type: String) :
    ArrayAdapter<ExpenseData>(context, resourceLayout, expenseDataList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(resourceLayout, parent, false)
        val expenseData = expenseDataList[position]
        val textView = view.findViewById<TextView>(android.R.id.text1)
        if(type == "type"){
            textView.text = expenseData.typeName
        }else if(type == "currency"){
            textView.text = expenseData.currencyCode
        }else{
            textView.text = expenseData.categoryName
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}
