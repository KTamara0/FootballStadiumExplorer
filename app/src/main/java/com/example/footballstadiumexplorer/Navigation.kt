package com.example.footballstadiumexplorer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.footballstadiumexplorer.ui.theme.FootballStadiumScreen
import com.example.footballstadiumexplorer.ui.theme.StadiumDetailsScreen
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.footballstadiumexplorer.data.StadiumViewModel
import com.example.footballstadiumexplorer.ui.theme.AddReviewScreen
import com.example.footballstadiumexplorer.ui.theme.AddStadiumScreen
import com.example.footballstadiumexplorer.ui.theme.FavoritesScreen


object Routes {
    const val SCREEN_ALL_STADIUMS = "stadiumsList"
    const val SCREEN_STADIUM_DETAILS = "stadiumDetails/{stadiumId}"
    const val FAVORITE_STADIUMS_SCREEN = "FavoriteStadiums"
    const val ADD_REVIEW_SCREEN = "AddReview/{stadiumId}"
    const val ADD_STADIUM_SCREEN = "AddStadium"
    fun getStadiumDetailsPath(stadiumId: Int?) : String {
        if (stadiumId != null && stadiumId != -1) {
            return "stadiumDetails/$stadiumId"
        }
        return "stadiumDetails/0"
    }

    fun getAddReviewPath(stadiumId: Int): String {
        if(stadiumId != null && stadiumId != -1) {
            return "AddReview/$stadiumId" // Generiranje URL-a za stranicu za unos recenzije
        }
        return "stadiumDetails/0"
    }
}
@Composable
fun NavigationController(viewModel: StadiumViewModel) {
    val navController = rememberNavController()

    val stadiums = viewModel.stadiums

    NavHost(navController = navController,
        startDestination = Routes.SCREEN_ALL_STADIUMS
    ) {
        composable(Routes.SCREEN_ALL_STADIUMS) {
            FootballStadiumScreen(navigation = navController, viewModel = viewModel)
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
            StadiumDetailsScreen(navigation = navController, stadiumId = stadiumId, viewModel = viewModel)
        }
        composable(Routes.FAVORITE_STADIUMS_SCREEN) {
            FavoritesScreen("FavoritesScreen",navigation = navController, imageResource = String.toString(), viewModel)
        }

        composable(
            Routes.ADD_REVIEW_SCREEN,
            arguments = listOf(navArgument("stadiumId") { type = NavType.IntType })
        )
        { backStackEntry ->
            val stadiumId = backStackEntry.arguments?.getInt("stadiumId") ?: 0
                AddReviewScreen(
                    navigation = navController,
                    onReviewAdded ={ review ->
                        // Dodaj recenziju za stadion
                        val stadium = viewModel.getStadiumById(stadiumId)
                        stadium?.reviews?.add(review)
                    }
                )
        }

        composable(Routes.ADD_STADIUM_SCREEN){
            AddStadiumScreen(navigation = navController, viewModel)
        }
    }
}