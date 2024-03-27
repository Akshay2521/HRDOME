package com.app.hrdrec.organization.expensecategories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.organization.expensecategories.dataclass.AllExpenseCategoriesData

class ExpenseCategoriesAdapter(
    private var expenseCategoriesList: ArrayList<AllExpenseCategoriesData>,
    val callBack: (mObj: AllExpenseCategoriesData, from: String) -> Unit
) : RecyclerView.Adapter<ExpenseCategoriesAdapter.ExpenseCategoriesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ExpenseCategoriesViewHolder = ExpenseCategoriesViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.expensecategories_row_list, parent, false)
    )

    override fun getItemCount(): Int = expenseCategoriesList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ExpenseCategoriesAdapter.ExpenseCategoriesViewHolder, position: Int
    ) {
        val expenseCategories = expenseCategoriesList[position]
        holder.categoryName.text = "Category Name : ${expenseCategories.categoryName}"
        holder.categoryId.text = "Category ID : ${expenseCategories.categoryId}"
        holder.typeName.text = "Type Name : ${expenseCategories.typeName}"
        holder.typeId.text = "Type ID : ${expenseCategories.typeId}"

        holder.imgDelete.setOnClickListener {
            callBack(expenseCategories, "delete")
        }

        holder.imgEdit.setOnClickListener {
            callBack(expenseCategories, "edit")
        }
    }

    class ExpenseCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val categoryId: TextView = itemView.findViewById(R.id.categoryId)
        val typeName: TextView = itemView.findViewById(R.id.typeName)
        val typeId: TextView = itemView.findViewById(R.id.typeId)

        //        val deleted: TextView = itemView.findViewById(R.id.textViewdeleted)
        val imgDelete: ImageView = itemView.findViewById(R.id.delete)
        val imgEdit: ImageView = itemView.findViewById(R.id.edit)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setExpenseCategoriesData(expenseCategoriesList: ArrayList<AllExpenseCategoriesData>) {
        this.expenseCategoriesList = expenseCategoriesList
        notifyDataSetChanged()
    }
}