package com.example.footballstadiumexplorer.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
fun FavoritesScreen(currentScreen: String, navigation: NavController, viewModel: StadiumViewModel) {

    var currentActiveButton by remember { mutableStateOf(if(currentScreen == "FavoritesScreen") 1 else 0) }

    val stadiums = viewModel.stadiums

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background1),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                TabButton(text = "Explorer", isActive = currentActiveButton == 0
                ){
                    if(currentScreen != "FootballScreen")
                        currentActiveButton = 0
                    navigation.navigate((Routes.SCREEN_ALL_STADIUMS))
                }
                Spacer(modifier = Modifier.width(6.dp))
                TabButton(text = "Favorites", isActive = currentActiveButton == 1){
                        currentActiveButton = 1
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FavoritesScreenTitle(
                title = "The more you explore, the more you enjoy!"
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 170.dp, bottom = 15.dp),
            contentPadding = PaddingValues(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(stadiums.filter { it.isFavorited }) { stadium ->
                FavoriteStadiumItem(stadium = stadium, imageResource = stadium.image)
            }
        }
    }
}

@Composable
fun FavoriteStadiumItem(stadium: Stadium, imageResource: Any) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
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
            contentDescription = stadium.name,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = stadium.name,
                color = LightBlue500,
                maxLines = 1,
            )
            Text(
                text = stadium.location.joinToString(", ") { "${it.city}, ${it.state}" },
                color = LightBlue500.copy(alpha = 0.8f),
                maxLines = 1,
            )
        }
    }
}

@Composable
fun FavoritesScreenTitle(
    title: String,
) {
    Column(modifier = Modifier
        .padding(top = 58.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = TextStyle(
                color = LightBlue300,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.merienda_regular)),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(bottom = 8.dp, top = 8.dp)
        )
    }
}
