package com.example.footballstadiumexplorer.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.footballstadiumexplorer.data.StadiumViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStadiumScreen(navigation: NavController, viewModel: StadiumViewModel){

    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isFavorited by remember { mutableStateOf(false) }
    var isVisited by remember { mutableStateOf(false) }
    var stadiumLink by remember { mutableStateOf("") }

    val stadiums = viewModel.stadiums

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
            .verticalScroll(scrollState)
            .background(Brush.linearGradient(colors = listOf(white, LightBlue200)))
    ){
        Text(text = "Add new stadium", style = TextStyle(
            fontSize = 24.sp,
            color = Indigo900,
            fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

           TextField(
               value = stadiumLink,
               onValueChange = { stadiumLink = it},
               label = { Text ("Image URL") },
               colors = textFieldColors,
               modifier = Modifier.fillMaxWidth()
                   .padding(horizontal = 16.dp)
           )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Stadium name") },
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state,
            onValueChange = { state = it },
            label = { Text("State") },
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = capacity,
            onValueChange = { capacity = it },
            label = { Text("Capacity") },
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
                        isVisited = isVisited,
                        image = stadiumLink
                    )
                    viewModel.addStadium(newStadium)
                    navigation.popBackStack() // Vrati se na prethodnu stranicu
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo900)
        ) {
            Text(text = "Add stadium", color = Color.White)
        }
    }
}

