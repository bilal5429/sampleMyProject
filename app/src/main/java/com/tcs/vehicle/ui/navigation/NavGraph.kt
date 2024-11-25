package com.tcs.vehicle.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tcs.vehicle.ui.view.image.ImageScreen
import com.tcs.vehicle.ui.view.search.CarSearchScreen

@Preview
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(navController, startDestination = Screen.Cars.route) {
        composable(Screen.Cars.route) { CarSearchScreen { actions.goToImagesScreen.invoke(it) } }
        composable(Screen.Images.route) { ImageScreen() }
    }
}

class MainActions(private val navController: NavHostController) {

    val goToCarsScreen: () -> Unit = {
        navController.navigate(Screen.Cars.route)
    }

    val goToImagesScreen: (String) -> Unit = {
        navController.navigate(Screen.Images.createRoute(it))
    }
}