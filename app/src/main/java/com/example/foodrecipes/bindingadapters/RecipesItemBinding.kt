package com.example.foodrecipes.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foodrecipes.R

@BindingAdapter("imageFromUrl")
fun setImageFromUrl(imageView: ImageView, imageUrl: String){
    imageView.load(imageUrl){
        crossfade(600)
    }
}
@BindingAdapter("veganImageColor")
fun setVeganImageColor(imageView: ImageView, isVegan: Boolean){
    if(isVegan){
        imageView.setColorFilter(ContextCompat.getColor(imageView.context, R.color.green));
    }
}

@BindingAdapter("veganTextColor")
fun setVeganTextColor(textView: TextView, isVegan: Boolean){
    if(isVegan){
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.green))
    }
}