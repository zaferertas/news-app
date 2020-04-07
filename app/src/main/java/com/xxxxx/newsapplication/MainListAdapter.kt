package com.xxxxx.newsapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xxxxx.newsapplication.data.NewsItem
import com.xxxxx.newsapplication.databinding.ItemListBinding

class MainListAdapter(val clickListener: ItemClickListener) :
    RecyclerView.Adapter<MainListAdapter.ItemViewHolder>() {

    private val items: ArrayList<NewsItem> = arrayListOf()

    fun setItems(newItems: List<NewsItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsItem) {
            binding.item = item
            binding.clickListener = clickListener
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater)
        return ItemViewHolder(binding)
    }
}

class ItemClickListener(val clickListener: (item: NewsItem) -> Unit) {
    fun onClick(item: NewsItem) = clickListener(item)
}
