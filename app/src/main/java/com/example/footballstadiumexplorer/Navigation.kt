package com.example.footballstadiumexplorer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.footballstadiumexplorer.ui.theme.FootballStadiumScreen
import com.example.footballstadiumexplorer.ui.theme.StadiumDetailsScreen
import androidx.navigation.NavType
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.navArgument


object Routes {
    const val SCREEN_ALL_STADIUMS = "stadiumsList"
    const val SCREEN_STADIUM_DETAILS = "stadiumDetails/{stadiumId}"
    fun getStadiumDetailsPath(stadiumId: Int?) : String {
        if (stadiumId != null && stadiumId != -1) {
            return "stadiumDetails/$stadiumId"
        }
        return "stadiumDetails/0"
    }
}
@Composable
fun NavigationController(navController: NavHostController) {

    NavHost(navController = navController, startDestination =
    Routes.SCREEN_ALL_STADIUMS) {
        composable(Routes.SCREEN_ALL_STADIUMS) {
            FootballStadiumScreen(navigation = navController)
        }
        composable(
            Routes.SCREEN_STADIUM_DETAILS,
            arguments = listOf(
                navArgument("stadiumId") {
                    type = NavType.IntType
                }
            )
        ) {backStackEntry ->
            val stadiumId = backStackEntry.arguments?.getInt("stadiumId") ?: 0
            StadiumDetailsScreen(navigation = navController, stadiumId = stadiumId)
        }
    }
}