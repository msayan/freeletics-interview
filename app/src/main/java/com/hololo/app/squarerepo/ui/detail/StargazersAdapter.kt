package com.hololo.app.squarerepo.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.hololo.app.squarerepo.R
import com.hololo.app.squarerepo.core.BaseEntity
import com.hololo.app.squarerepo.core.BasePagedListAdapter
import com.hololo.app.squarerepo.databinding.ItemStargazersBinding
import com.hololo.app.squarerepo.db.entities.StargazersEntity


class StargazersAdapter() : BasePagedListAdapter(object : DiffUtil.ItemCallback<BaseEntity>() {
    override fun areItemsTheSame(oldItem: BaseEntity, newItem: BaseEntity): Boolean {
        return oldItem is StargazersEntity && newItem is StargazersEntity && oldItem.githubId == newItem.githubId
    }

    override fun areContentsTheSame(oldItem: BaseEntity, newItem: BaseEntity): Boolean {
        return oldItem == newItem
    }

}) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val viewModel = StargazersAdapterViewModel()

        val binding: ItemStargazersBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_stargazers, parent, false)
        binding.viewModel = viewModel

        return binding
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        (binding as ItemStargazersBinding).viewModel?.item?.set(getItem(position) as StargazersEntity)
        binding.executePendingBindings()
    }

}