package com.example.ejemplofirebaseuvg

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var pcList: ArrayList<PcModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        empRecyclerView = findViewById(R.id.rvPc)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.LoadingData)

        pcList = arrayListOf<PcModel>()

        getEmployeesData()
    }

    private fun getEmployeesData() {

        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pcList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(PcModel::class.java)
                        pcList.add(empData!!)
                    }
                    val mAdapter = PcAdapter(pcList)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : PcAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, PcDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("pcID", pcList[position].pcID)
                            intent.putExtra("pcModel", pcList[position].pcModel)
                            intent.putExtra("pcBrand", pcList[position].pcBrand)
                            intent.putExtra("pcProcessor", pcList[position].pcProcessor)
                            intent.putExtra("pcRAM", pcList[position].pcRAM)
                            intent.putExtra("pcROM", pcList[position].pcROM)
                            startActivity(intent)
                        }

                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}