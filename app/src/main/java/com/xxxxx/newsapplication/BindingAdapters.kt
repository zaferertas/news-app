package com.xxxxx.newsapplication

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("android:src")
fun setImageUrl(view: ImageView, url: String?) {
    if (url != null) {
        Glide.with(view.context).load(url).into(view)
    }
}

@BindingAdapter("app:isVisible")
fun isVisible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:itemDate")
fun setFormattedDate(view: TextView, dateString: String?) {

    if (dateString != null) {
        val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = sourceFormat.parse(dateString)

        val f: DateFormat = DateFormat.getDateTimeInstance(
            DateFormat.SHORT,
            DateFormat.SHORT,
            Locale.getDefault()
        )
        date?.let { view.text = f.format(it) }
    }
}
