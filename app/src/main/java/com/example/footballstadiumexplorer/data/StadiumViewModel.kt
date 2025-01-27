package com.example.footballstadiumexplorer.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.footballstadiumexplorer.R
import com.example.footballstadiumexplorer.ui.theme.Location
import com.example.footballstadiumexplorer.ui.theme.Review
import com.example.footballstadiumexplorer.ui.theme.Stadium

class StadiumViewModel : ViewModel(){
    val stadiumsData = mutableStateListOf<Stadium>()
    val stadiums: List<Stadium> get() = stadiumsData

    init {
        stadiumsData.addAll(
            listOf(
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
        )
    }

    fun filterStadiumsByState(state: String): List<Stadium> {
        return stadiumsData.filter { stadium ->
            stadium.location.any { it.state == state }
        }
    }

    fun searchStadiums(query: String): List<Stadium> {
        return if (query.isBlank()) {
            stadiums
        } else {
            stadiums.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    fun addReviewToStadium(stadiumId: Int, review: Review) {
        val stadium = stadiumsData.find { it.stadiumId == stadiumId }
        stadium?.reviews?.add(review)
    }

    // Dodavanje novog stadiona
    fun addStadium(stadium: Stadium) {
        val newId = (stadiumsData.maxOfOrNull { it.stadiumId } ?: 0) + 1
        stadiumsData.add(stadium.copy(stadiumId = newId))
    }

    // Dohvaćanje stadiona po ID-u
    fun getStadiumById(id: Int): Stadium? {
        return stadiumsData.find { it.stadiumId == id }
    }

}