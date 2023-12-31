package com.dleskovic.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dleskovic.myapp.data.RecipeViewModel
import com.dleskovic.myapp.ui.RecipeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = RecipeViewModel()
        setContent {
            NavigationController(viewModel)
        }
    }
}

