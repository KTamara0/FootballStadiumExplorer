package com.example.footballstadiumexplorer.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.footballstadiumexplorer.R
import com.example.footballstadiumexplorer.Routes


@Composable
fun FootballStadiumScreen(navigation: NavController) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(colors = listOf(white, LightBlue200)))
    ) {
        ScreenTitle(title = "Football Stadium Explorer", subtitle = "\"From dreams to destinations: explore iconic stadiums.\"")
        FavoritesButton(currentScreen="FootballScreen")
        SearchBar(iconResource = R.drawable.ic_search, labelText = "Search")
        StadiumCategories()
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(stadiums.size) { index ->
                StadiumCard(
                    imageResource = stadiums[index].image,
                    title = stadiums[index].name,
                    subtitle =
                        stadiums[index].location.joinToString(", ") { "${it.city}, ${it.state}" },
                    function = {
                        navigation.navigate(
                            Routes.getStadiumDetailsPath(stadiumId =index)
                        )
                    }

                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        // Ovdje ne pozivaj StadiumDetailsScreen direktno, nego koristi navigaciju!
        // StadiumDetailsScreen(navigation, stadiumId=0) -> OVO TREBA UKLONITI!
        IconButtonSpecifier()
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
                fontFamily = FontFamily(Font(R.font.merienda_regular))
            ),
            modifier = Modifier
                .padding(bottom = 8.dp, top = 4.dp),
        )
        Text(
            text = subtitle,
            style = TextStyle(color = Color.Black, fontSize = 15.sp,
                fontStyle = FontStyle.Italic),
            modifier = Modifier.padding(top = 8.dp, bottom = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    @DrawableRes iconResource: Int,
    labelText: String,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent, //boja pozadine tog pravokutnika
        unfocusedTextColor = Grey800, // tekst
        focusedTextColor = Grey800, //tekst koji pisem
        unfocusedLabelColor = Indigo900,  //Search prije klika
        focusedLabelColor = Indigo900, //Search dok pisem pretrazivanje
        focusedIndicatorColor = Color.Transparent, //linija ispod search, preklapa se s donjim borderom
        unfocusedIndicatorColor = Color.Transparent, //isto ta linija
    )
) {
    var searchInput by remember {
        mutableStateOf("")
    }
    TextField(
        value = searchInput,
        onValueChange = { searchInput = it },
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
        colors = if (isActive) ButtonDefaults.buttonColors(contentColor =
        Color.White, containerColor = Indigo900) else
            ButtonDefaults.buttonColors(contentColor = Indigo900, containerColor =
            Color.White),
        modifier = Modifier.fillMaxHeight(),
        onClick = { onClick() }
    ) {
        Text(text)
    }
}

@Composable
fun FavoritesButton(currentScreen: String){
    var currentActiveButton by remember {
        mutableStateOf(if(currentScreen == "FootballScreen") 0 else 1)
    }
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
            if(currentScreen != "FavoritesScreen")
                currentActiveButton = 1
        }
    }
}

@Composable
fun StadiumCategories() {
    var currentActiveButton by remember {
        mutableStateOf(0)
    }
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
            currentActiveButton = 0
        }
        Spacer(modifier = Modifier.width(6.dp))
        Spacer(modifier = Modifier.height(10.dp))
        TabButton(text = "Turkey", isActive = currentActiveButton == 1) {
            currentActiveButton = 1
        }
        Spacer(modifier = Modifier.width(6.dp))
        Spacer(modifier = Modifier.height(10.dp))
        TabButton(text = "Germany", isActive = currentActiveButton == 2) {
            currentActiveButton = 2
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
fun IconButtonSpecifier() {
    var currentActiveButton by remember { mutableStateOf(0) }

    IconButton(iconResource =R.drawable.ic_plus, text = "Add new stadium", isActive = currentActiveButton == 0) {
        currentActiveButton = 0
    }
}

data class Stadium(
    @DrawableRes val image: Int = 0,
    var name: String = "",
    var location: List<Location> = listOf(),
    var capacity: Number,
    var year: Number,
    var description: String = "",
    var review: List<Review> = listOf(),
    var isFavorited: Boolean
)

data class Location(
    var city: String = "",
    var state: String = ""
)

data class Review(
    var user: String = "",
    var text: String = ""
)

val stadiums: List<Stadium> = listOf(
    Stadium(
        image = R.drawable.allianzarenabm,
        name = "Allianz Arena",
        location = listOf(Location(city="Munich", state="Germany")),
        capacity = 69901,
        year = 2005,
        description = "Allianz Arena is one of the most modern stadiums in the world. " +
                "The company Allianz bought the right to have the stadium bear its name for the next 30 years. " +
                "When the Champions League matches are played, the stadium is called Munich Arena, and during the World Cup, " +
                "the stadium was called FIFA WM-Stadion München, due to the FIFA ban on stadiums using the names of sponsors. " +
                "FC Bayern, German national football team and Audi Cup matches are played at the stadium.",
        review = listOf(Review(user="Karlo", text="One of the most beautiful stadiums I have visited.")),
        isFavorited = false
    ),
    Stadium(
        image = R.drawable.timsaharena,
        name = "Timsah Arena",
        location = listOf(Location(city = "Bursa", state="Turkey")),
        capacity = 43877,
        year=2015,
        description = "Timsah Arena is the stadium where Bursaspor plays its home matches, " +
                "which replaced its new home with Bursa Atatürk Stadium. The stadium meets UEFA and FIFA criteria. " +
                "The stadium has 70 apartments and 84 entrances. Capacity for 207 disabled spectators is also provided. " +
                "The parking lot has a capacity for 1,551 vehicles and 256 buses. " +
                "The exterior of the stadium is designed to be in the shape of the body and head of a crocodile.",
        review = listOf(Review(user="Marko", text="Beautiful stadium!")),
        isFavorited = false
    )
)

@Composable
fun StadiumCard(
    @DrawableRes imageResource: Int,
    title: String,
    subtitle: String,
    function: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .clickable(onClick = function)
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        Image(
            painter = painterResource(imageResource),
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
            Text(title, color = Blue300)
            Text(subtitle, color = Blue300)
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
    @DrawableRes coverImage: Int,
    navigation: NavController,
    stadium: Stadium
) { Box(
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
                    onClick = { navigation.navigate(Routes.SCREEN_ALL_STADIUMS) })
                CircularButton(
                    R.drawable.ic_favorite,
                    color = if (stadium.isFavorited) Red else Color.LightGray,
                    onClick = { stadium.isFavorited = !stadium.isFavorited })
            }
        }
    }
}

@Composable
fun ScreenInfo(
    @DrawableRes imageResource: Int,
    title: String,
    location: List<Location>
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Image(
            painter = painterResource(imageResource),
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
                fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Text(
            text = location.joinToString(", ") { "${it.city}, ${it.state}" },
            style = TextStyle(color = LightBlue600, fontSize = 15.sp,
                fontWeight = FontWeight.Light),
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
            tint = Blue300,
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
        InfoColumn(R.drawable.ic_star, stadium.capacity.toString())
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
fun Reviews(stadium: Stadium) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 16.dp))
    ) {
        Column {
            Text(text = "Reviews", style = TextStyle(fontSize = 16.sp,
                fontWeight = FontWeight.Bold))
            Text(text = stadium.review.joinToString(": ") { "${it.user}, ${it.text}" }, color = Color.DarkGray)
        }
    }
}

@Composable
fun NewReviewButton() {
    Button(
        onClick = { /*TODO*/ },
        elevation = null,
        colors = ButtonDefaults.buttonColors(
            containerColor = Indigo900,
            contentColor = white
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Add new review", modifier =
        Modifier.padding(8.dp))
    }
}



@Composable
fun OtherStadiums() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.timsaharena),
            contentDescription = "Timsah Arena",
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = R.drawable.acmilan),
            contentDescription = "AC Milan",
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
        )
    }
}

@Composable
fun StadiumDetailsScreen(
    navigation: NavController,
    stadiumId: Int
) {
    val stadium = stadiums[stadiumId]
    val scrollState = rememberLazyListState()
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            TopImageAndBar(
                coverImage = stadium.image,
                navigation = navigation,
                stadium = stadium
            )
            ScreenInfo(stadium.image, stadium.name, stadium.location)
            BasicInfo(stadium)
            Description(stadium)
            NewReviewButton()
            Reviews(stadium)
            OtherStadiums()
        }
    }
}

