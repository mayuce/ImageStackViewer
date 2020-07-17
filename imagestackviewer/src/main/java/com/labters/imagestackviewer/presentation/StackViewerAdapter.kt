package com.labters.imagestackviewer.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.labters.imagestackviewer.R
import com.labters.imagestackviewer.data.ImageData
import com.labters.imagestackviewer.databinding.ItemImageViewerBinding

class StackViewerAdapter(
    private val itemList: ArrayList<ImageData>
) : RecyclerView.Adapter<StackViewerAdapter.StackViewHolder>() {

    class StackViewHolder(private val binding: ItemImageViewerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataItem: ImageData?) {
            binding.run {
                item = dataItem
                executePendingBindings()
            }
        }
    }

    fun insertItem(item: ImageData) {
        itemList.add(item)
        notifyItemRangeInserted(itemCount, 1)
    }

    fun insertList(item: List<ImageData>) {
        itemList.addAll(item)
        notifyItemRangeInserted(itemCount, item.count())
    }

    fun submitList(item: List<ImageData>) {
        itemList.clear()
        itemList.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackViewHolder {
        return StackViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_image_viewer,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = itemList.count()

    override fun onBindViewHolder(holder: StackViewHolder, position: Int) {
        holder.bind(itemList.getOrNull(position))
    }
}