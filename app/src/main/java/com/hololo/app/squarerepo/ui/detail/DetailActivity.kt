package com.hololo.app.squarerepo.ui.detail

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.hololo.app.squarerepo.R
import com.hololo.app.squarerepo.core.BaseActivity
import com.hololo.app.squarerepo.core.Constants
import com.hololo.app.squarerepo.databinding.ActivityDetailBinding
import com.hololo.app.squarerepo.db.entities.ProjectEntity
import com.hololo.app.squarerepo.db.entities.StargazersEntity

class DetailActivity : BaseActivity<DetailActivityViewModel, ActivityDetailBinding>(DetailActivityViewModel::class.java) {

    private var menu: Menu? = null
    private var item: ProjectEntity? = null

    override fun initViewModel(viewModel: DetailActivityViewModel) {
        binding.viewModel = viewModel
    }

    override fun getLayoutRes() = R.layout.activity_detail

    override fun init() {
        super.init()
        binding.stargazersRecycler.layoutManager = GridLayoutManager(this, 2)
        parseIntent()
    }

    private fun parseIntent() {
        item = intent.getParcelableExtra<ProjectEntity>(Constants.IntentNames.PROJECT_ENTITY)
        supportActionBar?.title = item?.fullName
        supportActionBar?.setHomeButtonEnabled(true)

        binding.stargazersRecycler.adapter = StargazersAdapter()

        binding.viewModel?.getStargazers(item?.fullName ?: "")?.observe(this,
                object : Observer<PagedList<StargazersEntity>> {
                    override fun onChanged(t: PagedList<StargazersEntity>?) {
                        binding.stargazersRecycler.visibility = if (t?.size ?: 0 > 0) View.VISIBLE else View.GONE
                        binding.emptyView.visibility = if (t?.size ?: 0 > 0) View.GONE else View.VISIBLE
                        (binding.stargazersRecycler.adapter as StargazersAdapter).setList(t)
                    }
                })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.add_bookmark ->
                binding.viewModel?.addBookmark(this.item?.githubId ?: -1L)
            R.id.remove_bookmark ->
                binding.viewModel?.removeBookmark(this.item?.githubId ?: -1L)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_detail, menu)
        getBookmarkStatus()
        return true;
    }

    private fun getBookmarkStatus() {
        binding.viewModel?.getBookmarkStatus(item?.githubId
                ?: -1L)?.observe(this, object : Observer<Int> {
            override fun onChanged(t: Int?) {
                t?.let { count ->
                    val add = menu?.findItem(R.id.add_bookmark)
                    val remove = menu?.findItem(R.id.remove_bookmark)

                    add?.isVisible = count < 1
                    remove?.isVisible = count > 0
                }
            }

        })
    }
}