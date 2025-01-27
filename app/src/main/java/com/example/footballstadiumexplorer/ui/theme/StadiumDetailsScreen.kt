package com.example.footballstadiumexplorer.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.footballstadiumexplorer.R
import com.example.footballstadiumexplorer.Routes
import com.example.footballstadiumexplorer.data.StadiumViewModel


@Composable
fun StadiumDetailsScreen(
    navigation: NavController,
    stadiumId: Int,
    viewModel: StadiumViewModel
) {
    val stadium = viewModel.getStadiumById(stadiumId)

    if (stadium == null) {
        Text(text = "Stadium not found", modifier = Modifier.fillMaxSize())
        return
    }

    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(colors = listOf(LightBlue100, white),
                    start= Offset.Zero,
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                    ))
        ) {
            item {
                TopImageAndBar(
                    navigation = navigation,
                    stadium = stadium
                )
                ScreenInfo(imageResource = stadium.image,
                    stadium.name,
                    stadium.location)
                BasicInfo(stadium)
                Description(stadium)
                NewReviewButton(navigation = navigation, stadiumId = stadiumId)
                Reviews(stadium.reviews)
            }
        }
    }
}



@Composable
fun CircularButton(
    @DrawableRes iconResource: Int,
    color: Color = LightBlue300,
    elevation: ButtonElevation? =
        ButtonDefaults.buttonElevation(defaultElevation = 12.dp),
    onClick: () -> Unit = {}
) {
    Button(
        contentPadding = PaddingValues(),
        elevation = elevation,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = white,
            contentColor = color),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
        )
    }
}

@Composable
fun TopImageAndBar(
    navigation: NavController,
    stadium: Stadium
) {
    var isVisited by remember { mutableStateOf(stadium.isVisited) }
    var isFavorited by remember { mutableStateOf(stadium.isFavorited) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(56.dp)
                    .padding(horizontal = 16.dp)
            ) {
                CircularButton(
                    R.drawable.ic_arrow_back,
                    onClick = { navigation.navigate(Routes.SCREEN_ALL_STADIUMS) }
                )
                CircularButton(
                    iconResource = if(isVisited) R.drawable.visited else R.drawable.visited,
                    color = if (stadium.isVisited) LightBlue500 else Color.LightGray,
                    onClick = { isVisited = !isVisited
                        stadium.isVisited = isVisited}
                )
                CircularButton(
                    iconResource = if(isFavorited) R.drawable.love else R.drawable.love,
                    color = if (stadium.isFavorited) Red else Color.LightGray,
                    onClick = { isFavorited = !isFavorited
                        stadium.isFavorited = isFavorited }
                )
            }
        }
    }
}


@Composable
fun ScreenInfo(
    imageResource: Any,
    title: String,
    location: List<Location>
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)
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
                .height(400.dp)
                .width(500.dp)
                .clip(RoundedCornerShape(12.dp))
                .padding(bottom = 6.dp)

        )
        Text(
            text = title,
            style = TextStyle(color = Color.Black, fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Text(
            text = location.joinToString(", ") { "${it.city}, ${it.state}" },
            style = TextStyle(color = LightBlue600, fontSize = 17.sp,
                fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun InfoColumn(
    @DrawableRes iconResource: Int,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
            tint = Blue400,
            modifier = Modifier.height(24.dp)
        )
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}
@Composable
fun BasicInfo(stadium: Stadium) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        InfoColumn(R.drawable.seat, stadium.capacity.toString())
        InfoColumn(R.drawable.ic_clock, stadium.year.toString())
    }
}

@Composable
fun Description(
    stadium: Stadium
) {
    Text(
        text = stadium.description,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 20.dp)
    )
}

@Composable
fun Reviews(reviews: List<Review>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 16.dp))
            .padding(bottom = 50.dp)
    ) {
        Column {
            Text(text = "Reviews", style = TextStyle(fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic)
            )
            if (reviews.isEmpty()) {
                Text(
                    text = "No reviews yet",
                    style = TextStyle(color = Color.Gray)
                )
            } else {
                reviews.forEach { review ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "${review.user}: ",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        Text(
                            text = review.text,
                            style = TextStyle(color = Color.DarkGray)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NewReviewButton(navigation: NavController, stadiumId: Int){
    Button(
        onClick = { navigation.navigate("AddReview/$stadiumId") },
        elevation = null,
        colors = ButtonDefaults.buttonColors(
            containerColor = Indigo900,
            contentColor = white
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Add new review",
            modifier = Modifier.padding(8.dp))
    }
}

