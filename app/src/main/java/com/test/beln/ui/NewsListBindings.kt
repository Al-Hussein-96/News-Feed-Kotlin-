package com.test.beln.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.beln.R
import com.test.beln.data.News
import timber.log.Timber


@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<News>?) {
    items?.let {
        (listView.adapter as NewsAdapter).submitList(items)
    }
}

@BindingAdapter(value = ["app:imageUrl"], requireAll = false)
fun setImageUrl(imageView: ImageView, url: String?) {
    Timber.tag("Mohammad").i("imageUrl: %s",url)
    if (url == null) {
        imageView.setImageResource(R.drawable.flower)
    } else {
        Glide.with(imageView.context).load(url).placeholder(R.drawable.flower).into(imageView);

//        Glid.loadInto(imageView, url, placeHolder)
    }
}

//@BindingAdapter("app:completedTask")
//fun setStyle(textView: TextView, enabled: Boolean) {
//    if (enabled) {
//        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//    } else {
//        textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
//    }
//}