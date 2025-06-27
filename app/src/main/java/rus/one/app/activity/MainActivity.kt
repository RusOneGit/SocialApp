package rus.one.app.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import rus.one.app.R
import rus.one.app.card.UserCard
import rus.one.app.components.bar.BottomBarMain
import rus.one.app.components.button.ProfileButton
import rus.one.app.events.Event
import rus.one.app.navigation.AppNavGraph
import rus.one.app.navigation.NavigationItem
import rus.one.app.navigation.rememberNavigationState
import rus.one.app.posts.Post
import rus.one.app.posts.PostScreen
import rus.one.app.posts.ui.activity.NewPostActivity
import rus.one.app.profile.UserViewModel
import rus.one.app.viewmodel.BaseFeedViewModel
import rus.one.app.viewmodel.EventViewModel
import rus.one.app.viewmodel.PostViewModel
import kotlin.jvm.java


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val postViewModel: PostViewModel by viewModels()
    private val eventViewModel: EventViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launchWhenStarted {
            postViewModel.uiMessage.collect { message ->
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
        setContent {
            MainScreen(postViewModel, eventViewModel, userViewModel)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    postViewModel: BaseFeedViewModel<Post>,
    eventViewModel: BaseFeedViewModel<Event>,
    userViewModel: UserViewModel,
) {
    val navigationState = rememberNavigationState()

    val context = LocalContext.current

    val userState = userViewModel.users.collectAsState()
    val users = userState.value

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
            actions = { ProfileButton(onClick = { userViewModel.authorization(login = "russone", password = "danilka95") }) })
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
                PostScreen(
                    viewModel = postViewModel,
                    paddingValues = paddingValues,
                    onClick = {
                            item ->
                        val post = item as? Post ?: return@PostScreen
                        val intent = Intent(context, PostDetailActivity::class.java)
                        intent.putExtra("postId", post.id)
                        context.startActivity(intent)
                    }
                )
            },
            eventsScreenContent = {
                PostScreen(
                    viewModel = eventViewModel,
                    paddingValues = paddingValues,
                    onClick = {}
                )
            },
            usersScreenContent = {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    items(users, key = { it.id }) { user ->
                        UserCard(user)

                    }
                }

            }
        )
    }

}


