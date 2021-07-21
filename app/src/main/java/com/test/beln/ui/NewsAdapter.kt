package com.test.beln.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.test.beln.data.News
import com.test.beln.databinding.ItemNewsBinding
import timber.log.Timber

class NewsAdapter(private val viewModel: NewsViewModel) :
    ListAdapter<News, NewsAdapter.ViewHolder>(NewsDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Timber.tag("Mohammad").i("NewsAdapter onCreateViewHolder")
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NewsViewModel, item: News) {

            binding.viewModel = viewModel
            binding.news = item

            binding.executePendingBindings()


        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
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