package com.hienle.thenews.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hienle.thenews.R
import com.hienle.thenews.databinding.ItemNewsBinding
import com.hienle.thenews.ui.state.NewsItemUiState
import javax.inject.Inject

/**
 * Created by Hien Quang Le on 1/18/2022.
 * lequanghien247@gmail.com
 */

class NewsAdapter @Inject constructor(
    private val context: Context) :
    RecyclerView.Adapter<NewsItemVH>() {

    private var items = mutableListOf<NewsItemUiState>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemVH {
        val itemBinding = ItemNewsBinding.inflate(LayoutInflater.from(context), parent, false)
        return NewsItemVH(context, itemBinding)
    }

    override fun onBindViewHolder(holder: NewsItemVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    // Clean all elements of the recycler
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(list: List<NewsItemUiState>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

}

class NewsItemVH(
    private val context: Context,
    private val binding: ItemNewsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(newsItemUiState: NewsItemUiState) {
        binding.textViewItemNewsSource.text = newsItemUiState.source?.name
        binding.textViewItemNewsTitle.text = newsItemUiState.title
        binding.textViewItemNewsDescription.text = newsItemUiState.description
        binding.textViewItemNewsDate.text = newsItemUiState.publishedAt
        Glide
            .with(context)
            .load(newsItemUiState.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.avatar_1)
            .into(binding.imageViewItemNews!!);
    }
}