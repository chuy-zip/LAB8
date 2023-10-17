package com.example.ejemplofirebaseuvg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
    class PcAdapter(private val pcList: ArrayList<PcModel>) :
        RecyclerView.Adapter<PcAdapter.ViewHolder>() {

        private lateinit var mListener: OnItemClickListener

        interface OnItemClickListener{
            fun onItemClick(position: Int)
        }

        fun setOnItemClickListener(clickListener: OnItemClickListener){
            mListener = clickListener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.emp_list_item, parent, false)
            return ViewHolder(itemView, mListener)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentPc = pcList[position]
            holder.pcModel.text = currentPc.pcModel
        }

        override fun getItemCount(): Int {
            return pcList.size
        }

        class ViewHolder(itemView: View, clickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

            val pcModel : TextView = itemView.findViewById(R.id.pcName)

            init {
                itemView.setOnClickListener {
                    clickListener.onItemClick(adapterPosition)
                }
            }

        }

    }