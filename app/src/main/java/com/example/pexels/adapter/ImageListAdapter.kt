package com.example.pexels.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pexels.R
import com.example.pexels.data.PhotosItem
import com.example.pexels.databinding.ItemListBinding

class ImageListAdapter(
    private var listData: MutableList<PhotosItem?>,
    private var onItemClickCallback: OnItemClickCallback
): RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listData: MutableList<PhotosItem?>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(data: PhotosItem) {
            with(binding) {
                imgList.load(data.src?.portrait) {
                    crossfade(true)
                    crossfade(1000)
                    placeholder(android.R.color.darker_gray)
                    error(R.drawable.nodata)
                }
                txtAuthor.text = itemView.context.getString(R.string.author, data.photographer)
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClick(data)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position]!!)
    }

    override fun getItemCount(): Int = listData.size

    interface OnItemClickCallback {
        fun onItemClick(data: PhotosItem)
    }

}