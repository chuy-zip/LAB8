package com.example.ejemplofirebaseuvg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class PcDetailsActivity : AppCompatActivity() {
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
        tvPcModel.text = intent.getStringExtra("pcModel")
        tvPcBrand.text = intent.getStringExtra("pcBrand")
        tvPcProcessor.text = intent.getStringExtra("pcProcessor")
        tvPcRAM.text = intent.getStringExtra("pcRAM")
        tvPcROM.text = intent.getStringExtra("pcROM")
    }
}