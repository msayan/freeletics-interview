package com.hololo.app.squarerepo.ui.main

import com.hololo.app.squarerepo.R
import com.hololo.app.squarerepo.core.BaseActivity
import com.hololo.app.squarerepo.databinding.ActivityMainBinding
import com.hololo.app.squarerepo.ui.list.ListFragment

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>(MainActivityViewModel::class.java) {
    override fun initViewModel(viewModel: MainActivityViewModel) {
        binding.viewModel = viewModel
    }

    override fun getLayoutRes() = R.layout.activity_main


    override fun init() {
        supportFragmentManager.beginTransaction().replace(R.id.rootView, ListFragment.getInstance()).commit()
    }
}
