package com.example.footballstadiumexplorer

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
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
import com.example.footballstadiumexplorer.ui.theme.AddNewStadiumScreen
import com.example.footballstadiumexplorer.ui.theme.AddReviewScreen
import com.example.footballstadiumexplorer.ui.theme.FavoritesScreen
import com.example.footballstadiumexplorer.ui.theme.Review
import com.example.footballstadiumexplorer.ui.theme.stadiums


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
        composable(Routes.FAVORITE_STADIUMS_SCREEN) {
            FavoritesScreen("FavoritesScreen",navigation = navController)
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
                        stadiums[stadiumId].reviews.add(review)
                    }
                )
        }

        composable(Routes.ADD_STADIUM_SCREEN){
            AddNewStadiumScreen(navigation = navController, onStadiumAdded = {
                newStadium -> stadiums.add(newStadium)
                navController.popBackStack()
            })
        }
    }
}