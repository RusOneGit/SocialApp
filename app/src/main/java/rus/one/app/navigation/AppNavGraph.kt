package rus.one.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    eventsScreenContent: @Composable () -> Unit,
    usersScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.PostsScreen.route
    ) {
        composable(Screen.PostsScreen.route) {
            homeScreenContent()
        }
        composable(Screen.EventsScreen.route) {
            eventsScreenContent()
        }
        composable(Screen.UsersScreen.route) {
            usersScreenContent()
        }
    }
}