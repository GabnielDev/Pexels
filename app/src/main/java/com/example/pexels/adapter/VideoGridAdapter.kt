package com.example.pexels.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pexels.R
import com.example.pexels.data.PhotosItem
import com.example.pexels.data.VideoFilesItem
import com.example.pexels.data.VideosItem
import com.example.pexels.databinding.ItemGridBinding

class VideoGridAdapter(
    private var listData: MutableList<VideosItem?>,
    private var onItemClickCallback: OnItemClickCallback
): RecyclerView.Adapter<VideoGridAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listData: MutableList<VideosItem?>) {
        this.listData = listData
        notifyDataSetChanged()
    }



    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGridBinding.bind(itemView)
        fun bind(data: VideosItem) {
            with(binding) {
                imgGrid.load(data.image) {
                    crossfade(true)
                    crossfade(1000)
                    placeholder(android.R.color.darker_gray)
                    error(R.drawable.ic_launcher_background)
                }

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClick(data)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position]!!)
    }

    override fun getItemCount(): Int = listData.size

    interface OnItemClickCallback {
        fun onItemClick(data: VideosItem)
    }
}