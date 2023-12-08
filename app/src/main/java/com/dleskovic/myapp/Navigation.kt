package com.dleskovic.myapp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import com.dleskovic.myapp.ui.RecipeDetailsScreen
import com.dleskovic.myapp.ui.RecipeScreen

object Routes{
    const val SCREEN_ALL_RECIPES = "recipesList"
    const val SCREEN_RECIPE_DETAILS ="recipeDetails/{recipeId}"

    fun getDetailsPath(recipeId: Int?): String{
        if(recipeId != null && recipeId != -1){
            return "recipeDetails/$pageId"
        }
    }
}

@Composable
fun NavigationController() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Routes.SCREEN_ALL_RECIPES,
        ){
        composable(Routers.SCREEN_ALL_RECIPES){
            RecipeScreen(navController)
        }
        composable(
            Router.SCREEN_RECIPE_DETAILS,
            arguments = listOf(
                navArgument(
                    name = "recipeId"
                ){
                    type = NavType.IntType
                }
            )){ backStackEntry ->
                backStackEntry.arguments?getInt("recipeId")?.let{
                    RecipeDetailsScreen(navController, it)
            }
        }
    }
}