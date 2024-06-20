package com.example.moneytracker.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytracker.databinding.RecordLayoutBinding

class RecordItemAdapter(private val context: Context,
                        private val recordList: MutableList<String>) : RecyclerView.Adapter<RecordItemAdapter.RecordItemViewHolder>() {

    inner class RecordItemViewHolder(private val binding: RecordLayoutBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordItemViewHolder {
        val view = RecordLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return RecordItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    override fun onBindViewHolder(holder: RecordItemViewHolder, position: Int) {
        Log.d("RecordItemAdapter", "onBindViewHolder: called for $position")
    }
}