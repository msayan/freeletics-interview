package com.hololo.app.squarerepo.core

import android.view.View
import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("app:setDrawableLink")
    fun setDrawableLink(view: ImageView, link: String?) {
        if (link.isNullOrEmpty())
            return
        Picasso.get().cancelRequest(view)
        Picasso.get().load(link).into(view)
    }

    @JvmStatic
    @BindingAdapter("app:visibility")
    fun setVisibilty(view: View, isVisible: Boolean) {
        view.visibility = View.GONE
        if (isVisible) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}