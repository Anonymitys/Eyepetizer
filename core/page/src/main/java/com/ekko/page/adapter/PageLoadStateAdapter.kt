package com.ekko.page.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekko.page.databinding.NetworkStateItemBinding

class PageLoadStateAdapter(private val adapter: PageAdapter) :
    LoadStateAdapter<PageLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState
    ): PageLoadStateViewHolder = PageLoadStateViewHolder.create(parent) { adapter.retry() }

    override fun onBindViewHolder(holder: PageLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}


class PageLoadStateViewHolder(private val binding: NetworkStateItemBinding, retry: () -> Unit) :
    ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retry.invoke()
        }
    }


    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible =
            !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        binding.errorMsg.text = (loadState as? LoadState.Error)?.error?.message
    }


    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PageLoadStateViewHolder {
            val binding =
                NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PageLoadStateViewHolder(binding, retry)
        }
    }

}