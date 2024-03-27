
package com.app.hrdrec.expenses

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.databinding.ActivityExpensesBinding
import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.home.getallmodules.Paths
import com.app.hrdrec.organization.ClickInterfaceOrgan
import com.app.hrdrec.organization.ExpensesDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Expenses : AppCompatActivity() {

    lateinit var mObj: ModuleData
    private val binding by lazy { ActivityExpensesBinding.inflate(layoutInflater) }

    @Inject
    lateinit var expenseDataAdapter: ExpensesDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mObj = intent.getSerializableExtra("mObj") as ModuleData
        binding.recyclerView.adapter = expenseDataAdapter

        expenseDataAdapter.updateAlbumData(mObj.paths)

        expenseDataAdapter.setItemClick(object : ClickInterfaceOrgan<Paths> {
            override fun onClick(data: Paths) {
                Log.e("Data","exp"+data.name)
                when (data.name) {

                    "Reimburse Expenses" -> {
                        val intent = Intent(this@Expenses, ExpenseReimburseActivity::class.java)
                        intent.putExtra("id", mObj.id)
                        startActivity(intent)

                    }

                    "Authorize Expenses" -> {
                        val admin = Intent(this@Expenses, ExpenseAuthorizeListActivity::class.java)
                        intent.putExtra("mObj", data)
                        startActivity(admin)

                    }

                    "Submit" -> {
                        val intent = Intent(this@Expenses, ExpenseListActivity::class.java)
                        intent.putExtra("id", mObj.id)
                        startActivity(intent)

                    }

                    else -> {

                    }
                }
            }
        })
    }


}



