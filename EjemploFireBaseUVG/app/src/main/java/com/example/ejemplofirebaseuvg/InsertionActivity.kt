package com.example.ejemplofirebaseuvg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var pcModel: EditText
    private lateinit var pcBrand: EditText
    private lateinit var pcProcessor: EditText
    private lateinit var pcRAM: EditText
    private lateinit var pcROM: EditText
    private lateinit var btnSaveData: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        pcModel = findViewById(R.id.pcModel)
        pcBrand = findViewById(R.id.pcBrand)
        pcProcessor = findViewById(R.id.pcProcessor)
        pcRAM = findViewById(R.id.pcRAM)
        pcROM = findViewById(R.id.pcROM)

        dbRef = FirebaseDatabase.getInstance().getReference("Computers")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData(){
        val newPcModel = pcModel.text.toString()
        val newPcBrand = pcProcessor.text.toString()
        val newPcProcessor = pcProcessor.text.toString()
        val newPcRAM = pcRAM.text.toString()
        val newPcROM = pcROM.text.toString()

        if(newPcModel.isEmpty()){
            pcModel.error = "Please enter model"
        }
        if(newPcBrand.isEmpty()){
            pcBrand.error = "Please enter brand"
        }
        if(newPcProcessor.isEmpty()){
            pcProcessor.error = "Please enter processor"
        }
        if(newPcRAM.isEmpty()){
            pcRAM.error = "Please enter model"
        }
        if(newPcROM.isEmpty()){
            pcROM.error = "Please enter model"
        }

        val pcId = dbRef.push().key!!

        val Computer = PcModel (newPcModel, newPcBrand, newPcProcessor, newPcRAM, newPcROM)

        dbRef.child(pcId).setValue(Computer)
            .addOnCompleteListener{
                Toast.makeText(this, "Data inserted succesfully", Toast.LENGTH_LONG).show()

                pcModel.text.clear()
                pcBrand.text.clear()
                pcProcessor.text.clear()
                pcRAM.text.clear()
                pcROM.text.clear()
            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Errpr ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}