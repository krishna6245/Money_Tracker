package com.example.moneytracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytracker.dataModels.AccountModel
import com.example.moneytracker.databinding.AccountItemLayoutBinding
import com.example.moneytracker.helpers.Display

class AccountItemAdapter(private val context: Context): RecyclerView.Adapter<AccountItemAdapter.AccountItemViewHolder>(){
    private var accountList = emptyList<AccountModel>()
    inner class AccountItemViewHolder(private val binding: AccountItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                accountItemLayoutName.text = accountList[position].name
                accountItemLayoutBalance.text = accountList[position].balance.toString()
                accountItemLayoutIcon.setImageResource(accountList[position].image)

                accountItemLayoutOptionButton.setOnClickListener{
                    TODO()
//                    val popupMenu = PopupMenu(context, accountItemLayoutOptionButton)
//                    popupMenu.menuInflater.inflate(R.menu.account_item_menu, popupMenu.menu)
//                    popupMenu.show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountItemViewHolder {
        val binding = AccountItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return AccountItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    override fun onBindViewHolder(holder: AccountItemViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setAccountList(accountList: List<AccountModel>){
        val display = Display(context)
        display.toast("hello")
        this.accountList = accountList
        notifyDataSetChanged()
    }
}