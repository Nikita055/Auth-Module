package com.wama.libsignup.utility.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wama.libsignup.databinding.CountryCodeSpinnerListItemBinding
import com.wama.libsignup.utility.model.CallingCodeListItem

class CallingCodesListAdapter(private val callingCodeList: List<CallingCodeListItem>, private val onItemClick: OnItemClick) : RecyclerView.Adapter<CallingCodesListAdapter.CodesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodesViewHolder {
        val itemBinding = CountryCodeSpinnerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CodesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CodesViewHolder, position: Int) {
        val callingCode = callingCodeList[position]
        holder.itemBinding.countryCode.text = callingCode.callingCode
        holder.itemBinding.countryName.text = callingCode.country
        holder.itemView.setOnClickListener { v: View? -> onItemClick.onItemClick(position) }
    }

    override fun getItemCount(): Int {
        return callingCodeList.size
    }

    inner class CodesViewHolder(val itemBinding: CountryCodeSpinnerListItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
    interface OnItemClick {
        fun onItemClick(position: Int)
    }
}