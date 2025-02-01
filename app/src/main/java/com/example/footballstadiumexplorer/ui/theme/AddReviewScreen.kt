package com.example.footballstadiumexplorer.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.footballstadiumexplorer.R
import com.example.footballstadiumexplorer.data.StadiumViewModel


@Composable
fun AddReviewScreen(stadiumId: Int,
                    viewModel: StadiumViewModel,
                    navigation: NavController)
{
    var userName by remember { mutableStateOf("") }
    var reviewText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.reviewbackground),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Add a review",
                style = TextStyle(fontSize = 24.sp, color = LightBlue200, fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text(text = "Your Name") },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.LightGray),
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                label = { Text(text = "Your Review") },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.LightGray),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (userName.isNotEmpty() && reviewText.isNotEmpty()) {
                        val newReview = Review(user = userName, text = reviewText)
                        viewModel.addReviewToStadium(stadiumId, newReview)
                        navigation.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Indigo900)
            ) {
                Text("Submit review", color = Color.White)
            }
        }
    }
}
