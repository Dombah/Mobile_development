package com.dleskovic.myapp.data
import androidx.annotation.DrawableRes

data class Ingredient (
    @DrawableRes val image: Int,
    var name: String = "",
    var subtitle: String = ""
) {

}