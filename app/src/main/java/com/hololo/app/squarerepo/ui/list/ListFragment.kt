package com.hololo.app.squarerepo.ui.list

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.hololo.app.squarerepo.R
import com.hololo.app.squarerepo.core.BaseFragment
import com.hololo.app.squarerepo.core.BasePagedListAdapter
import com.hololo.app.squarerepo.core.Constants
import com.hololo.app.squarerepo.databinding.FragmentListBinding
import com.hololo.app.squarerepo.db.entities.ProjectEntity
import com.hololo.app.squarerepo.db.views.ProjectEntityView
import com.hololo.app.squarerepo.ui.detail.DetailActivity

class ListFragment : BaseFragment<ListFragmentViewModel, FragmentListBinding>(ListFragmentViewModel::class.java) {

    companion object {
        fun getInstance() : ListFragment{
            return ListFragment()
        }
    }

    override fun getLayoutRes() = R.layout.fragment_list

    override fun init() {
        binding.viewModel = viewModel

        binding.projectRecyler.adapter = ProjectAdapter(object : (ProjectEntity?) -> Unit {
            override fun invoke(projectEntity: ProjectEntity?) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(Constants.IntentNames.PROJECT_ENTITY, projectEntity)
                startActivity(intent)
            }
        })

        binding.viewModel?.getProjects()?.observe(this, object : Observer<PagedList<ProjectEntityView>> {
            override fun onChanged(t: PagedList<ProjectEntityView>?) {
                t?.let { data ->
                    (binding.projectRecyler.adapter as BasePagedListAdapter).setList(data)
                }
            }
        })
    }
}
