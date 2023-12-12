package com.dleskovic.myapp.data


data class Recipe (
    var id: String = "",
    var isFavorite: Boolean = false,
    val image: String = "",
    var title: String = "",
    var category: String = "",
    var cookingTime: String = "",
    var energy: String = "",
    var rating: String = "",
    var description: String = "",
    var reviews: String = "",
    var ingredients: List<Ingredient> = listOf()
)

