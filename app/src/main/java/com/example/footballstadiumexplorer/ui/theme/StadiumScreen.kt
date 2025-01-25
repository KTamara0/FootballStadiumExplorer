package com.example.footballstadiumexplorer.ui.theme

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.footballstadiumexplorer.R
import com.example.footballstadiumexplorer.Routes


@Composable
fun FootballStadiumScreen(navigation: NavController) {
    var searchInput by remember { mutableStateOf("") }
    var currentActiveButton by remember { mutableStateOf(0) }

    val filteredByName = stadiums.filter {
        it.name.contains(searchInput, ignoreCase = true )
    }

    val filteredStadiums = when (currentActiveButton) {
        0 -> filteredByName
        1 -> filteredByName.filter { it.location.any { location -> location.state == "Spain" } }
        2 -> filteredByName.filter { it.location.any { location -> location.state == "Germany" } }
        else -> filteredByName
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
        SearchBar(iconResource = R.drawable.ic_search,
            labelText = "Search by stadium name",
            onValueChange = { newInput -> searchInput = newInput })
        StadiumCategories(currentActiveButton = currentActiveButton,
            onCategorySelect = { selectedCategory ->
            currentActiveButton = selectedCategory
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
                        val stadiumIndex = stadiums.indexOf(stadium)
                        navigation.navigate(
                            Routes.getStadiumDetailsPath(stadiumId = stadiumIndex)
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
        containerColor = Color.Transparent, //boja pozadine tog pravokutnika
        unfocusedTextColor = Grey800, // tekst
        focusedTextColor = Grey800, //tekst koji pisem
        unfocusedLabelColor = Indigo900,  //Search prije klika
        focusedLabelColor = Indigo900, //Search dok pisem pretrazivanje
        focusedIndicatorColor = Color.Transparent, //linija ispod search, preklapa se s donjim borderom
        unfocusedIndicatorColor = Color.Transparent, //isto ta linija
    ),
    onValueChange: (String) -> Unit
) {
    var searchInput by remember {
        mutableStateOf("")
    }
    TextField(
        value = searchInput,
        onValueChange = {
            searchInput = it
            onValueChange(it)  // Pozovi onValueChange kad se input promijeni
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

    IconButton(iconResource =R.drawable.ic_plus, text = "Add new stadium", isActive = currentActiveButton == 0) {
        currentActiveButton = 0
        navController.navigate("AddStadium")
    }
}

data class Stadium(
    @DrawableRes val image: Int = 0,
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

val stadiums: MutableList<Stadium> = mutableListOf(
    Stadium(
        image = R.drawable.allianzarenabm,
        stadiumId = 1,
        name = "Allianz Arena",
        location = listOf(Location(city="Munich", state="Germany")),
        capacity = 69901,
        year = 2005,
        description = "Allianz Arena is one of the most modern stadiums in the world. " +
                "The company Allianz bought the right to have the stadium bear its name for the next 30 years. " +
                "When the Champions League matches are played, the stadium is called Munich Arena, and during the World Cup, " +
                "the stadium was called FIFA WM-Stadion München, due to the FIFA ban on stadiums using the names of sponsors. " +
                "FC Bayern, German national football team and Audi Cup matches are played at the stadium.",
        reviews = mutableListOf(Review(user="Karlo", text="One of the most beautiful stadiums I have visited.")),
        isFavorited = false,
        isVisited = false
    ),
    Stadium(
        image = R.drawable.timsaharena,
        stadiumId = 2,
        name = "Timsah Arena",
        location = listOf(Location(city = "Bursa", state="Turkey")),
        capacity = 43877,
        year=2015,
        description = "Timsah Arena is the stadium where Bursaspor plays its home matches, " +
                "which replaced its new home with Bursa Atatürk Stadium. The stadium meets UEFA and FIFA criteria. " +
                "The stadium has 70 apartments and 84 entrances. Capacity for 207 disabled spectators is also provided. " +
                "The parking lot has a capacity for 1,551 vehicles and 256 buses. " +
                "The exterior of the stadium is designed to be in the shape of the body and head of a crocodile.",
        reviews = mutableListOf(Review(user="Marko", text="Beautiful stadium!")),
        isFavorited = false,
        isVisited = false
    ),
    Stadium(
        image = R.drawable.acmilan,
        stadiumId = 3,
        name = "San Siro Stadium",
        location = listOf(Location(city="Milan", state = "Italy")),
        capacity = 75817,
        year =1925,
        description = "San Siro is home to one of the most successful European teams, Milan and city rivals Inter, " +
                "and is one of the most famous football stadiums in the world. It was made exclusively for football, so there is no athletics track. " +
                "The construction of the stadium began in 1925, at the initiative of the then president of AC Milan, Piero Pirelli. The first match, " +
                "played on September 19, 1926, in which Inter defeated Milan with a score of 6:3, was attended by 35,000 spectators. In the beginning, " +
                "only Milan played in the stadium, and since 1947 it has been shared with Inter. The Italian national football team sometimes plays matches " +
                "at the stadium, and the finals of the Champions League and the finals of the UEFA Cup have also been played. The stadium was renovated in 1989 " +
                "for the needs of the 1990 World Cup.",
        reviews = mutableListOf(Review(user = "Alex", text= "Amazing stadium. I am impressed.")),
        isFavorited = false,
        isVisited = false
    ),
    Stadium(
        image = R.drawable.bvbstadium,
        stadiumId = 4,
        name = "Signal Iduna Park",
        location = listOf(Location(city = "Dortmund", state="Germany")),
        capacity = 81365,
        year = 1974,
        description = "Signal Iduna Park is a stadium in the German city of Dortmund. It was built in 1974 for the World Cup, which was held in Germany that year, and six " +
                "matches of the 2006 World Cup were played there. The Borussia Dortmund football club plays its home games at the stadium. Signal Iduna Park has 65,718 seats " +
                "(in international matches) and even 80,552 when standing is allowed. It is Germany's largest stadium, the sixth-largest in Europe, and the third-largest home to " +
                "a top-flight European club after Camp Nou and Santiago Bernabéu Stadium. It holds the European record for average fan attendance, set in the 2011–12 season with " +
                "almost 1.37 million spectators over 17 games at an average of 80,588 per game.",
        reviews = mutableListOf(Review(user = "Luka", text = "It is a wonderful experience to attend a match at a stadium like this.")),
        isFavorited = false,
        isVisited = false
    ),
    Stadium(
        image = R.drawable.campnou,
        stadiumId = 5,
        name = "Camp Nou",
        location = listOf(Location(city = "Barcelona", state = "Spain")),
        capacity = 99354,
        year = 1957,
        description = "Camp Nou is a football stadium in Barcelona. FC Barcelona plays home matches on it. Camp Nou means new playground in Catalan. With a capacity of 99,354, " +
                "it is now the biggest stadium in Europe. However, the total capacity has varied over the years owing to different modifications. In the 1998-99 season, UEFA " +
                "recognised the services and facilities at Camp Nou by awarding it five star status. In 2010, in line with the new UEFA regulations, this category was replaced by " +
                "the new 'Category 4' title which corresponds to the stadiums which fulfill the most demanding requirements with regards to facilities, services and capacity such as " +
                "FC Barcelona's ground.",
        reviews = mutableListOf(Review(user = "Emanuel", text = "Camp Nou is an incredible experience – the atmosphere, history and size of the stadium are unrepeatable!")),
        isFavorited = false,
        isVisited = false
    ),
    Stadium(
        image = R.drawable.santiagobernabeu,
        stadiumId = 6,
        name = "Santiago Bernabeu",
        location = listOf(Location(city = "Madrid", state = "Spain")),
        capacity = 78297,
        year = 1947,
        description = "The Santiago Bernabéu Stadium is home to the Spanish giants Real Madrid. The popular El Bernabeu, named after the former president of Real Santiago Bernabeu Yeste, " +
                "is one of the most famous and prestigious football stadiums in Europe. It hosted the European Champions Cup final three times (1957, 1969, 1980), and the Champions League " +
                "final for the 2009/10 season. The finals of the 1964 European Football Championship and the World Cup were also held at this stadium. In 2007, the 1000th jubilee match was " +
                "played at the Santiago Bernabeu. UEFA declared the Santiago Bernabeu an elite European stadium after the Champions League match against Olympiacos was played on October 27. " +
                "A month after that, the stadium celebrated the 60th anniversary of its presentation in 1947.",
        reviews = mutableListOf(Review(user = "Leo", text = "Santiago Bernabeu is an impressive stadium with a rich history and a great atmosphere.")),
        isFavorited = false,
        isVisited = false
    )
)

@Composable
fun StadiumCard(
    @DrawableRes imageResource: Int,
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
            // Dodavanje prozirne pozadine za tekst
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(8.dp) // Unutarnji razmak unutar pozadine
            ){
                Column {
                    Text(title, color = Blue300, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(subtitle, color = Blue300, style = TextStyle(fontWeight = FontWeight.Bold))
                }
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
    @DrawableRes coverImage: Int,
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
                    onClick = { navigation.navigate(Routes.SCREEN_ALL_STADIUMS) })
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
                        stadium.isFavorited = isFavorited })
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
                fontStyle = FontStyle.Italic))
            if (reviews.isEmpty()) {
                Text(
                    text = "No reviews yet",
                    style = TextStyle(color = Color.Gray)
                )
            } else {
                reviews.forEach { review ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp) // Razmak između recenzija
                    ) {
                        // Korisničko ime (bold, crna boja)
                        Text(
                            text = "${review.user}: ",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        // Tekst recenzije (siva boja)
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




@Composable
fun StadiumDetailsScreen(
    navigation: NavController,
    stadiumId: Int
) {
    val stadium = stadiums[stadiumId]
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
                .background(Brush.linearGradient(colors = listOf(LightBlue100, white),
                    start= Offset.Zero,
                    end = Offset(0f, Float.POSITIVE_INFINITY)))
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
                NewReviewButton(navigation = navigation, stadiumId = stadiumId)
                Reviews(stadium.reviews)
            }
        }
    }
}


// SCREEN ZA FAVORITE:

@Composable
fun FavoritesScreen(currentScreen: String, navigation: NavController) {
    var currentActiveButton by remember {
        mutableStateOf(if(currentScreen == "FavoritesScreen") 1 else 0)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Pozadina - slika
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
                    if(currentScreen != "FavoriteStadiums")
                        currentActiveButton = 1
                    navigation.navigate(Routes.FAVORITE_STADIUMS_SCREEN)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp), // Povećano za više prostora između gumba i naslova
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FavoritesScreenTitle(
                title = "The more you explore, the more you enjoy!"
            )
        }

        // Ostali sadržaj ekrana, kao što je popis omiljenih stadiona
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 170.dp, bottom = 15.dp),
            contentPadding = PaddingValues(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(stadiums.filter { it.isFavorited }) { stadium ->
                FavoriteStadiumItem(stadium = stadium, navigation = navigation)
            }
        }
    }
}

@Composable
fun FavoriteStadiumItem(stadium: Stadium, navigation: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Visina pojedinačne stavke
    ) {
        // Pozadina: Slika stadiona
        Image(
            painter = painterResource(id = stadium.image),
            contentDescription = stadium.name,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)), // Zaobljeni rubovi
            contentScale = ContentScale.Crop
        )

        // Ime i lokacija na slici (dolje lijevo)
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart) // Pozicioniranje u donji lijevi kut
                .padding(10.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.6f), // Poluprozirna pozadina
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(8.dp) // Unutarnji razmak unutar pozadine
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


//STRANICA ZA DODAVANJE REVIEW-A

@Composable
fun AddReviewScreen(onReviewAdded: (Review) -> Unit, navigation: NavController) {
    var userName by remember { mutableStateOf("") }
    var reviewText by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(colors = listOf(white, LightBlue200)))
    ) {
        Text(text = "Add a Review", style = TextStyle(fontSize = 24.sp,color = Indigo900, fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.height(16.dp))

        // Unos korisničkog imena
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text(text = "Your Name") },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(LightBlue500),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Unos recenzije
        TextField(
            value = reviewText,
            onValueChange = { reviewText = it },
            label = { Text(text = "Your Review") },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(LightBlue500),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Spremi gumb
        Button(
            onClick = {
                if (userName.isNotEmpty() && reviewText.isNotEmpty()) {
                    val newReview = Review(userName, reviewText)
                    onReviewAdded(newReview) // Dodaj recenziju u listu
                    navigation.popBackStack() // Vraća nas na prethodnu stranicu
                } else {
                    // Možda želiš dodati nekakvu poruku ili validaciju
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo900)
        ) {
            Text("Submit Review", color = Color.White)
        }
    }
}

// STRANICA ZA DODAVANJE STADIONA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewStadiumScreen(navigation: NavController, onStadiumAdded: (Stadium) -> Unit){

    var name by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isFavorited by remember { mutableStateOf(false) }
    var isVisited by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }



    val textFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = Color.LightGray, // Pozadina polja
        focusedIndicatorColor = Indigo900, // Linija kad je fokusirano
        unfocusedIndicatorColor = LightBlue800, // Linija kad nije fokusirano
        unfocusedTextColor = Color.Black, // Boja teksta
        focusedTextColor = Grey800, //tekst koji pisem
        unfocusedLabelColor = Indigo900,  //Search prije klika
        focusedLabelColor = Indigo900, //Search dok pisem pretrazivanje
        cursorColor = Indigo900 // Boja kursora
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(colors = listOf(white, LightBlue200)))
    ){
        Text(text = "Add new stadium", style = TextStyle(
            fontSize = 24.sp,
            color = Indigo900,
            fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri == null) {
                Icon(
                    painter = painterResource(id = R.drawable.addimage), // Placeholder ikona
                    contentDescription = "Add Image",
                    tint = LightBlue800,
                    modifier = Modifier.size(64.dp)
                )
            } else {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Stadium name")},
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City")},
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state,
            onValueChange = { state = it },
            label = { Text("State")},
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = capacity,
            onValueChange = { capacity = it },
            label = { Text("Capacity")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Year Built") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Favorited")
            Checkbox(checked = isFavorited, onCheckedChange = { isFavorited = it })

            Text("Visited")
            Checkbox(checked = isVisited, onCheckedChange = { isVisited = it })
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                if (name.isNotEmpty() && city.isNotEmpty() && capacity.isNotEmpty() && year.isNotEmpty()) {
                    val newStadium = Stadium(
                        stadiumId = stadiums.size,
                        name = name,
                        location = listOf(Location(city, state)),
                        capacity = capacity.toIntOrNull() ?: 0,
                        year = year.toIntOrNull() ?: 0,
                        description = description,
                        isFavorited = isFavorited,
                        isVisited = isVisited
                    )
                    onStadiumAdded(newStadium) // Dodaj novi stadion
                    navigation.popBackStack() // Vrati se na prethodnu stranicu
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo900)
        ) {
            Text(text = "Add stadium", color = Color.White)
        }
    }
}


