package com.test.beln.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.test.beln.data.News
import com.test.beln.databinding.ItemNewsBinding

class NewsAdapter(private val viewModel: NewsViewModel) :
    ListAdapter<News, NewsAdapter.ViewHolder>(NewsDiffCallback()) {


    class ViewHolder private constructor(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NewsViewModel, item: News) {

            binding.title.text = item.title
            binding.content.text = item.description


        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }
}

class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}