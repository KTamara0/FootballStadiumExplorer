package com.example.footballstadiumexplorer.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


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
