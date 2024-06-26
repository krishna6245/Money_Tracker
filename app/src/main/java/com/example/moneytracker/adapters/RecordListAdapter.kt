package com.example.moneytracker.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytracker.databinding.RecordLayoutBinding

class RecordListAdapter(): RecyclerView.Adapter<RecordListAdapter.RecordListItemHolder>(){
    inner class RecordListItemHolder(binding: RecordLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordListItemHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecordListItemHolder, position: Int) {
        TODO("Not yet implemented")
    }
}