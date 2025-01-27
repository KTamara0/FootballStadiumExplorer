package com.example.footballstadiumexplorer.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.footballstadiumexplorer.R
import com.example.footballstadiumexplorer.Routes
import com.example.footballstadiumexplorer.data.StadiumViewModel


@Composable
fun FootballStadiumScreen(navigation: NavController, viewModel: StadiumViewModel) {
    var searchInput by remember { mutableStateOf("") }
    var currentActiveCategory by remember { mutableStateOf(0) }

    val stadiums = viewModel.stadiums

    val filteredStadiums = viewModel.stadiums.filter { stadium ->
        val matchesName = stadium.name.contains(searchInput, ignoreCase = true)
        val matchesCategory = when (currentActiveCategory) {
            1 -> stadium.location.any { it.state == "Spain" }
            2 -> stadium.location.any { it.state == "Germany" }
        else -> true
    }
        matchesName && matchesCategory
    }


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(colors = listOf(white, LightBlue200)))
    ) {
        ScreenTitle(title = "Football Stadium Explorer", subtitle = "\"From dreams to destinations: explore iconic stadiums.\"")
        FavoritesButton(currentScreen="FootballScreen", navigation)
        SearchBar(
            iconResource = R.drawable.ic_search,
            labelText = "Search by stadium name",
            onValueChange = { newInput -> searchInput = newInput })
        StadiumCategories(currentActiveButton = currentActiveCategory,
            { selectedCategory -> currentActiveCategory = selectedCategory
        })
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(filteredStadiums) { stadium ->
                StadiumCard(
                    imageResource = stadium.image,
                    title = stadium.name,
                    subtitle =
                        stadium.location.joinToString(", ") { "${it.city}, ${it.state}" },
                    function = {
                        navigation.navigate(
                            Routes.getStadiumDetailsPath(stadiumId = stadium.stadiumId)
                        )
                    }

                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        IconButtonSpecifier(navController = navigation)
    }
}

@Composable
fun ScreenTitle(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = Indigo900,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.merienda_regular)),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(bottom = 8.dp, top = 4.dp),
        )
        Text(
            text = subtitle,
            style = TextStyle(color = Color.Black, fontSize = 15.sp,
                fontStyle = FontStyle.Italic),
            modifier = Modifier.padding(top = 8.dp, bottom = 10.dp),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    @DrawableRes iconResource: Int,
    labelText: String,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        unfocusedTextColor = Grey800,
        focusedTextColor = Grey800,
        unfocusedLabelColor = Indigo900,
        focusedLabelColor = Indigo900,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
    ),
    onValueChange: (String) -> Unit
) {
    var searchInput by remember { mutableStateOf("") }

    TextField(
        value = searchInput,
        onValueChange = {
            searchInput = it
            onValueChange(it)
        },
        label = {
            Text(labelText,
                fontSize = 18.sp)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = labelText,
                tint = Indigo900,
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp)
            )
        },
        colors = colors,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(width = 2.dp, Blue100, RoundedCornerShape(8.dp))
    )
}

@Composable
fun TabButton(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Button(
        shape = RoundedCornerShape(12.dp),
        elevation = null,
        colors = if (isActive) ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Indigo900)
        else
            ButtonDefaults.buttonColors(
                contentColor = Indigo900,
                containerColor = Color.White),
        modifier = Modifier.fillMaxHeight(),
        onClick = { onClick() }
    ) {
        Text(text)
    }
}

@Composable
fun FavoritesButton(currentScreen: String,
                    navigation: NavController){

    var currentActiveButton by remember { mutableStateOf(if(currentScreen == "FootballScreen") 0 else 1) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(44.dp),
    ) {
        TabButton(text = "Explorer", isActive = currentActiveButton == 0
        ){
                currentActiveButton = 0
        }
        Spacer(modifier = Modifier.width(6.dp))
        TabButton(text = "Favorites", isActive = currentActiveButton == 1){
            if(currentScreen != "FavoriteStadiums")
                currentActiveButton = 1
            navigation.navigate(Routes.FAVORITE_STADIUMS_SCREEN)
        }
    }
}

@Composable
fun StadiumCategories(currentActiveButton: Int,
                      onCategorySelect: (Int) -> Unit)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(44.dp),
    ) {
        TabButton(text = "All", isActive = currentActiveButton == 0) {
            onCategorySelect(0)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Spacer(modifier = Modifier.height(10.dp))
        TabButton(text = "Spain", isActive = currentActiveButton == 1) {
            onCategorySelect(1)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Spacer(modifier = Modifier.height(10.dp))
        TabButton(text = "Germany", isActive = currentActiveButton == 2) {
            onCategorySelect(2)
        }
    }
}

@Composable
fun IconButton(
    @DrawableRes iconResource: Int,
    text: String,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    Button(
        colors = if (isActive) ButtonDefaults.buttonColors(contentColor =
        Color.White, containerColor = LightBlue800) else
            ButtonDefaults.buttonColors(contentColor = LightBlue800, containerColor =
            Color.White),
        onClick = { onClick() },
        modifier = Modifier
            .padding(vertical = 5.dp)
            .height(40.dp)
            .shadow(2.dp, RoundedCornerShape(60.dp))
    ) {
        Row {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = text,
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 18.sp,
                )
            )
        }
    }
}

@Composable
fun IconButtonSpecifier(navController: NavController) {
    var currentActiveButton by remember { mutableStateOf(0) }

    IconButton(iconResource =R.drawable.ic_plus, text = "Add new stadium", isActive = currentActiveButton == 0)
    {
        currentActiveButton = 0
        navController.navigate("AddStadium")
    }
}

data class Stadium(
    var image: Any,
    var stadiumId: Int,
    var name: String = "",
    var location: List<Location> = listOf(),
    var capacity: Number,
    var year: Number,
    var description: String = "",
    var reviews: MutableList<Review> = mutableListOf(),
    var isFavorited: Boolean,
    var isVisited: Boolean
)

data class Location(
    var city: String = "",
    var state: String = ""
)

data class Review(
    var user: String = "",
    var text: String = ""
)

@Composable
fun StadiumCard(
    imageResource: Any,
    title: String,
    subtitle: String,
    function: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(400.dp)
            .height(300.dp)
            .clickable(onClick = function)
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        val painter = when (imageResource) {
            is Int -> painterResource(id = imageResource)
            is String -> rememberAsyncImagePainter(
                model = imageResource,
                error = painterResource(R.drawable.addimage),
                placeholder = painterResource(R.drawable.addimage)
            )
            else -> painterResource(R.drawable.addimage)
        }
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(8.dp)
            ){
                Column {
                    Text(title, color = Blue300, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(subtitle, color = Blue300, style = TextStyle(fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}



