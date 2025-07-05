package rus.one.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import rus.one.app.posts.ui.activity.NewPost

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    eventsScreenContent: @Composable () -> Unit,
    usersScreenContent: @Composable () -> Unit,
    newPostScreen: @Composable () -> Unit,
    newEventScreen: @Composable () -> Unit
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

        composable(Screen.NewPostScreen.route) {
            newPostScreen()
        }


        composable(Screen.NewEventScreen.route) {
            newEventScreen()
        }
    }
}