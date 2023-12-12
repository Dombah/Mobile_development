package com.dleskovic.myapp.ui

import android.graphics.Paint.Align
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dleskovic.myapp.R
import com.dleskovic.myapp.data.Recipe
import com.dleskovic.myapp.data.RecipeViewModel
import com.dleskovic.myapp.ui.theme.DarkGray
import com.dleskovic.myapp.ui.theme.Pink

@Composable
fun RecipeDetailsScreen(
    navigation: NavController,
    recipeId : Int,
    viewModel: RecipeViewModel
) {
    val scrollState = rememberLazyListState()
    val recipe = viewModel.recipesData[recipeId]
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = scrollState,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ){
        item{
            TopImageAndBar(
                recipe = recipe,
                navigation = navigation,
                viewModel = viewModel)
            ScreenTitle(title = recipe.title, subtitle = recipe.category)
            BasicInfo(recipe = recipe)
            Description(recipe = recipe)
            Servings()
            IngredientsHeader()
            IngredientsList(recipe = recipe)
            ShoppingListButton()
            Reviews(recipe)
            OtherRecipes()
        }
    }
}

@Composable
fun InfoColumn(
    @DrawableRes iconResource: Int,
    text: String
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(painter = painterResource(id = iconResource),
            contentDescription = null,
            tint = Pink,
            modifier = Modifier.height(24.dp)
        )
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}
@Composable
fun BasicInfo(recipe: Recipe){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ){
        InfoColumn(iconResource = R.drawable.ic_clock, text = recipe.cookingTime)
        InfoColumn(iconResource = R.drawable.ic_flame, text = recipe.energy)
        InfoColumn(iconResource = R.drawable.ic_star, text = recipe.rating)
    }
}

@Composable
fun Description(
    recipe: Recipe
){
    Text(
        text = recipe.description,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
    )
}

@Composable
fun Servings(){
    var value by remember{
        mutableStateOf(6)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 0.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.Transparent)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ){
        Text(
            text = "Servings",
            modifier = Modifier.weight(1f), // flex u css-u
            style = TextStyle(fontWeight = FontWeight.Medium)
        )
        CircularButton(iconResource = R.drawable.ic_minus,
            color = Pink, elevation = null){
            value--
        }
        Text(
            text = "$value",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        CircularButton(iconResource = R.drawable.ic_plus,
            color = Pink, elevation = null){
        }
    }
}

@Composable
fun TopImageAndBar(
    recipe : Recipe,
    navigation : NavController,
    viewModel: RecipeViewModel,
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)){
        Image(
            painter = rememberAsyncImagePainter(model = recipe.image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ){
                CircularButton(iconResource = R.drawable.ic_arrow_back, color = Pink){
                    navigation.popBackStack()
                }
                CircularButton(iconResource = R.drawable.ic_favorite){
                    recipe.isFavorite = !recipe.isFavorite
                    viewModel.updateRecipeData(recipe)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White
                            ),
                            startY = 100f
                        )
                    )
            )
        }
    }
}

@Composable
fun CircularButton(
    @DrawableRes iconResource: Int,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(12.dp),
    color: Color = DarkGray,
    onClick: () -> Unit = {}
){
    Button(
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = color),
        elevation = elevation,
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ){
        Icon(painter = painterResource(id = iconResource), contentDescription = null)
    }
}

@Composable
fun IngredientsHeader() {
    var activeButtonId by remember { mutableStateOf(0) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(44.dp)
    ){
        TabButton(
            text = "Ingredients",
            isActive = activeButtonId == 0,
            modifier = Modifier.weight(1f)
        ) {
            activeButtonId = 0
        }
        TabButton(
            text = "Tools",
            isActive = activeButtonId == 1,
            modifier = Modifier.weight(1f)
        ) {
            activeButtonId = 1
        }
        TabButton(
            text = "Lunch",
            isActive = activeButtonId == 2,
            modifier = Modifier.weight(1f)
        ) {
            activeButtonId = 2
        }
    }
}

@Composable
fun <T> EasyGrid(nColumns: Int, items: List<T>, content: @Composable (T) -> Unit){
    Column(Modifier.padding(16.dp)){
        for(i in items.indices step nColumns){
            Row{
                for(j in 0 until nColumns){
                    if(i + j < items.size){
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier.weight(1f)
                        ){
                            content(items[i+j])
                        }}else{
                            Spacer(Modifier.weight(1f,fill = true))
                        }
                    }
                }
            }
        }
    }

@Composable
fun IngredientsList(
    recipe: Recipe
) {
    EasyGrid(nColumns = 3, items = recipe.ingredients) {
        IngredientCard(image = it.image, title = it.name, subtitle = it.subtitle)
    }
}

@Composable
fun ShoppingListButton() {
    Button(
        onClick = { /*TODO*/ },
        elevation = null,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Add to shopping list", modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun IconButton(
    @DrawableRes iconResource: Int,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = Pink),
    side: Int = 0
) {
    Button(
        onClick = {},
        colors = colors,
    ){
        Row{
            if(side == 0){
                Icon(
                    painter = painterResource(id = iconResource),
                    contentDescription = text
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )
                )
            }else{
                    Text(
                        text = text,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    painter = painterResource(id = iconResource),
                    contentDescription = text
                )
            }
        }
    }
}

@Composable
fun Reviews(recipe: Recipe) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 16.dp))
    ){
        Column{
            Text(text = "Reviews", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Text(text = recipe.reviews, color = DarkGray)
        }

        IconButton(
            iconResource = R.drawable.ic_arrow_back, text = "See all",
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Pink),
            side = 1
        )
    }
    
}

@Composable
fun OtherRecipes() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.strawberry_pie_2),
            contentDescription = "Strawberry Pie",
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
        )
    }
}

