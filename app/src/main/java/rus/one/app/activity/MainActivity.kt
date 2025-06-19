package rus.one.app.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import rus.one.app.R
import rus.one.app.card.CardItem
import rus.one.app.card.UserCard
import rus.one.app.components.bar.BottomBarMain
import rus.one.app.components.button.ProfileButton
import rus.one.app.navigation.AppNavGraph
import rus.one.app.navigation.NavigationItem
import rus.one.app.navigation.rememberNavigationState
import rus.one.app.posts.ContentScreen
import rus.one.app.posts.ui.activity.NewPostActivity
import rus.one.app.posts.ui.activity.PostDetailActivity
import rus.one.app.viewmodel.EventViewModel
import rus.one.app.viewmodel.PostViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val postViewModel: PostViewModel by viewModels()
    private val eventViewModel: EventViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen(postViewModel, eventViewModel)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    postViewModel: PostViewModel,
    eventViewModel: EventViewModel,
) {
    val navigationState = rememberNavigationState()

    val eventsState = eventViewModel.events.collectAsState()
    val events = eventsState.value
    val context = LocalContext.current

//    val userState = postViewModel.users.collectAsState()
//    val users = userState.value
    // Предположим, что у вас есть события

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            val intent = Intent(context, NewPostActivity::class.java)
            context.startActivity(intent)
        }) {
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = stringResource(R.string.add)
            )
        }
    }, topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFEF7FF)),
            title = { Text(stringResource(R.string.App_name)) },
            actions = { ProfileButton(onClick = {}) })
    }, bottomBar = {
        BottomBarMain(
            items = listOf(
                NavigationItem.Posts,
                NavigationItem.Events,
                NavigationItem.Users
            ), navigationState
        )
    }) { paddingValues ->  // добавляем параметр padding

        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent = {
                ContentScreen(
                    viewModel = postViewModel,
                    paddingValues = paddingValues,
                    onClick = { }
                )
            },
            eventsScreenContent = {
                ContentScreen(
                    viewModel = eventViewModel,
                    paddingValues = paddingValues,
                    onClick = { }
                )
            },
            usersScreenContent = {
//                LazyColumn(
//                    modifier = Modifier.padding(paddingValues)
//                ) {
//
//                  items(users){ user->
//                      UserCard(user)
//                  }
//                }

            }
        )
    }

}


