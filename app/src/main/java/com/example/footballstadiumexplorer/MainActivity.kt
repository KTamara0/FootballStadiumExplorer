package com.example.footballstadiumexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.footballstadiumexplorer.data.StadiumViewModel
import com.example.footballstadiumexplorer.ui.theme.FootballStadiumExplorerTheme

class MainActivity : ComponentActivity() {

    private val stadiumViewModel: StadiumViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FootballStadiumExplorerTheme {
                NavigationController(viewModel = stadiumViewModel)
            }
        }
    }
}


