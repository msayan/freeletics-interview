package com.hololo.app.squarerepo.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.hololo.app.squarerepo.R
import com.hololo.app.squarerepo.core.BaseEntity
import com.hololo.app.squarerepo.core.BasePagedListAdapter
import com.hololo.app.squarerepo.databinding.ItemProjectBinding
import com.hololo.app.squarerepo.db.entities.ProjectEntity
import com.hololo.app.squarerepo.db.views.ProjectEntityView

class ProjectAdapter(val callback: (ProjectEntity?) -> Unit) : BasePagedListAdapter(object : DiffUtil.ItemCallback<BaseEntity>() {
    override fun areItemsTheSame(oldItem: BaseEntity, newItem: BaseEntity): Boolean {
        return oldItem is ProjectEntityView && newItem is ProjectEntityView &&
                oldItem.project?.githubId == newItem.project?.githubId
    }

    override fun areContentsTheSame(oldItem: BaseEntity, newItem: BaseEntity): Boolean {
        return oldItem == newItem
    }

}) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val viewModel = ProjectAdapterViewModel()

        val binding: ItemProjectBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_project, parent, false)
        binding.viewModel = viewModel
        binding.root.setOnClickListener {
            binding.viewModel?.item?.get()?.let { item ->
                callback.invoke(item.project)
            }
        }

        return binding
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        (binding as ItemProjectBinding).viewModel?.item?.set(getItem(position) as ProjectEntityView?)
        binding.executePendingBindings()
    }
}