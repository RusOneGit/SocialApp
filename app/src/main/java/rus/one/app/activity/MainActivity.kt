package rus.one.app.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import dagger.hilt.android.AndroidEntryPoint
import rus.one.app.R
import rus.one.app.card.CardJob
import rus.one.app.card.UserCard
import rus.one.app.components.bar.BottomBarMain
import rus.one.app.components.button.ProfileButton
import rus.one.app.navigation.AppNavGraph
import rus.one.app.navigation.NavigationItem
import rus.one.app.navigation.rememberNavigationState
import rus.one.app.posts.Post
import rus.one.app.posts.PostScreen
import rus.one.app.posts.ui.activity.NewPost
import rus.one.app.profile.User
import rus.one.app.profile.UserViewModel
import rus.one.app.viewmodel.EventViewModel
import rus.one.app.viewmodel.PostViewModel


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

            val currentUserId by userViewModel.userId.collectAsState(initial = 0)

            Log.d("PostSending", "currentUser: $currentUserId")

            MainScreen(postViewModel, eventViewModel, userViewModel, currentUserId)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    postViewModel: PostViewModel,
    eventViewModel: EventViewModel,
    userViewModel: UserViewModel,
    currentUserId: Long,
) {
    val navigationState = rememberNavigationState()
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current

    val userState = userViewModel.users.collectAsState()
    val users = userState.value


    val expanded = remember { mutableStateOf(false) }

    var selectedUser by remember { mutableStateOf<User?>(null) }
    var showJobs by remember { mutableStateOf(false) }
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            when (currentRoute) {
                NavigationItem.Posts.screen.route -> navigationState.navHostController.navigate("new_post")
                NavigationItem.Events.screen.route -> navigationState.navHostController.navigate("new_event")
                else -> navigationState.navHostController.navigate("new_post")
            }
        }) {
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = when (currentRoute) {
                    NavigationItem.Events.screen.route -> stringResource(R.string.add_event)
                    else -> stringResource(R.string.add_post)

                }
            )
        }
    }, topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFEF7FF)),
            title = { Text(stringResource(R.string.App_name)) },
            actions = { ProfileButton(onClick = {  expanded.value = true }) })
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
                        val post = item
                        val intent = Intent(context, PostDetailActivity::class.java)
                        intent.putExtra("postId", post.id)
                        context.startActivity(intent)
                    },
                    currentUserId = currentUserId
                )
            },
            eventsScreenContent = {
                PostScreen(
                    viewModel = eventViewModel,
                    paddingValues = paddingValues,
                    onClick = {},
                    currentUserId = currentUserId
                )
            },
            usersScreenContent = {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    items(users, key = { it.id }) { user ->
                        val isSelected = true
                        UserCard(
                            user = user,
                            isSelected = !isSelected,
                            onClick = {
                                selectedUser = user
                                showJobs = true
                                userViewModel.loadJobs(user.id) // Загружаем работы при клике
                            }
                        )

                    }
                }

            },
            newPostScreen = {
                NewPost(
                    viewModel = postViewModel,
                    userViewModel = userViewModel,
                    onClose = { navigationState.navHostController.popBackStack() }
                )

            },
            newEventScreen = {
                NewPost(
                    viewModel = eventViewModel,
                    userViewModel = userViewModel,
                    onClose = { navigationState.navHostController.popBackStack() }
                )
            }
        )


    }






    if (selectedUser != null && showJobs) {
        val jobs = userViewModel.jobs.collectAsState().value
        val isLoading = userViewModel.jobsLoading.collectAsState().value
        val error = userViewModel.jobsError.collectAsState().value
        AlertDialog(
            onDismissRequest = {
                selectedUser = null
                showJobs = false
            },
            title = { Text("Работы ${selectedUser?.name}") },
            text = {
                when {
                    isLoading -> CircularProgressIndicator()
                    !error.isNullOrEmpty() -> Text(error)
                    jobs.isEmpty() -> Text("Нет данных о работах")
                    else -> LazyColumn() {
                        items(jobs) { job ->
                            CardJob(job)
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    selectedUser = null
                    showJobs = false
                }) {
                    Text("Закрыть")
                }
            }
        )
    }

        AuthMenu(userViewModel, expanded = expanded.value, onDismiss =  {expanded.value = false} ,context)
    }


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthMenu(viewModel: UserViewModel, expanded: Boolean, onDismiss: () -> Unit, context: Context) {
    // Получаем токен и userId из viewModel
    val token by viewModel.token.collectAsState()
    val userId by viewModel.userId.collectAsState()

    val isAuthorized by viewModel.isAuthorized.collectAsState()

    Log.d("AuthMenu", "token = $token, userId = $userId, isAuthorized = $isAuthorized")

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        if (isAuthorized) {
            DropdownMenuItem(
                text = { Text("Выйти") },
                onClick = {
                    onDismiss()
                    viewModel.logout()
                    Toast.makeText(context, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            DropdownMenuItem(
                text = { Text("Авторизоваться") },
                onClick = {
                    onDismiss()
                    val intent = Intent(context, AuthorizeActivity::class.java)
                    context.startActivity(intent)
                }
            )
            DropdownMenuItem(
                text = { Text("Зарегистрироваться") },
                onClick = {
                    onDismiss()
                    val intent = Intent(context, RegisterActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
    }
}

