package com.example.baseproject.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.data.ProductResponse
import com.example.baseproject.databinding.ItemProductBinding
import com.example.baseproject.utils.load

class ProductAdaptor : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: ArrayList<ProductResponse.ProductResponseItem> = ArrayList()

    class EmployeeHolder(var binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        if (holder is EmployeeHolder) {
            holder.binding.txtPrice.text = "$".plus((item.price ?: 0.0).toString())
            holder.binding.txtRating.text = (item.rating?.rate ?: 0.0).toString()
            holder.binding.txtCategory.text = item.category
            holder.binding.txtCount.text = "( ".plus((item.rating?.count ?: 0).toString()).plus(" Ratings)")
            holder.binding.txtTitle.text = item.title
            holder.binding.imgThumb.load(item.image)
        }
    }

    fun setDataList(list: ArrayList<ProductResponse.ProductResponseItem>) {
        dataList = list
    }
}