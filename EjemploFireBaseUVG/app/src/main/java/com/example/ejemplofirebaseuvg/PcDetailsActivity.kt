package com.example.ejemplofirebaseuvg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class PcDetailsActivity : AppCompatActivity() {
    private lateinit var tvPcID: TextView
    private lateinit var tvPcModel: TextView
    private lateinit var tvPcBrand: TextView
    private lateinit var tvPcProcessor: TextView
    private lateinit var tvPcRAM: TextView
    private lateinit var tvPcROM: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pc_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("pcID").toString(),
                intent.getStringExtra("pcModel").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }
    }

    private fun openUpdateDialog(
        pcID: String,
        pcModel: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etPcModel = mDialogView.findViewById<EditText>(R.id.etPcModel)
        val etPcBrand = mDialogView.findViewById<EditText>(R.id.etPcBrand)
        val etPcProcessor = mDialogView.findViewById<EditText>(R.id.etPcProcessor)
        val etPcRAM = mDialogView.findViewById<EditText>(R.id.etPcRAM)
        val etPcROM = mDialogView.findViewById<EditText>(R.id.etPcROM)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etPcModel.setText(intent.getStringExtra("pcModel").toString())
        etPcBrand.setText(intent.getStringExtra("pcBrand").toString())
        etPcProcessor.setText(intent.getStringExtra("pcProcessor").toString())
        etPcRAM.setText(intent.getStringExtra("pcRAM").toString())
        etPcROM.setText(intent.getStringExtra("pcROM").toString())

        mDialog.setTitle("Updating $pcModel Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                pcID,
                etPcModel.text.toString(),
                etPcBrand.text.toString(),
                etPcProcessor.text.toString(),
                etPcRAM.text.toString(),
                etPcROM.text.toString()
            )

            Toast.makeText(applicationContext, "Pc Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvPcModel.text = etPcModel.text.toString()
            tvPcBrand.text = etPcBrand.text.toString()
            tvPcProcessor.text = etPcProcessor.text.toString()
            tvPcRAM.text = etPcRAM.text.toString()
            tvPcROM.text = etPcROM.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        pcID: String,
        pcModel: String,
        pcBrand: String,
        pcProcessor: String,
        pcRAM: String,
        pcROM: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Computers").child(pcID)
        val empInfo = PcModel(pcID, pcModel, pcBrand, pcProcessor, pcRAM, pcROM)
        dbRef.setValue(empInfo)
    }

    private fun initView() {
        tvPcModel = findViewById(R.id.tvPcModel)
        tvPcBrand = findViewById(R.id.tvPcBrand)
        tvPcProcessor = findViewById(R.id.tvPcProcessor)
        tvPcRAM = findViewById(R.id.tvPcRAM)
        tvPcROM = findViewById(R.id.tvPcROM)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvPcID.text = intent.getStringExtra("pcID")
        tvPcModel.text = intent.getStringExtra("pcModel")
        tvPcBrand.text = intent.getStringExtra("pcBrand")
        tvPcProcessor.text = intent.getStringExtra("pcProcessor")
        tvPcRAM.text = intent.getStringExtra("pcRAM")
        tvPcROM.text = intent.getStringExtra("pcROM")
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Computers").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Pc data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}