package com.example.barcodescanner.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.barcodescanner.ui.screen.HomeScreen
import com.example.barcodescanner.ui.screen.RawNutritionShowScreen

@Composable
fun NavGraph(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HomeRoute
    ){
        composable<Route.HomeRoute> {
            HomeScreen(
                onScanClick = { code ->
                    navController.navigate(code)
                }
            )
        }

        composable<Route.RawNutritionShowRoute> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.RawNutritionShowRoute>()
            RawNutritionShowScreen(
                args.productCode
            )
        }
    }
}