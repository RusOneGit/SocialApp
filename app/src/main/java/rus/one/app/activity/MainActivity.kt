package rus.one.app.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import rus.one.app.NavigationItem
import rus.one.app.R
import rus.one.app.card.CardItem
import rus.one.app.components.bar.BottomBarMain
import rus.one.app.components.button.ProfileButton
import rus.one.app.viewmodel.ViewModelCard
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import kotlin.getValue


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ViewModelCard by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen(viewModel)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(viewModel: ViewModelCard) {

    Log.d("кнопка", "перерисовка")
    val message = remember{mutableStateOf("")}

    val postsState = viewModel.posts.collectAsState()
    val posts = postsState.value

    Log.d("MainScreen", "Recomposition: ${posts.size} posts")

    val eventsState = viewModel.events.collectAsState(initial = emptyList())
    val events = eventsState.value
    val context = LocalContext.current
    // Предположим, что у вас есть события

    val selectedItemPosition = remember { mutableStateOf(0) }

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
            selectedItemPosition, items = listOf(
                NavigationItem.Posts,
                NavigationItem.Events,
                NavigationItem.Users
            )
        )
    }) { paddingValues ->  // добавляем параметр padding
        // используем padding при создании содержимого
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            when (selectedItemPosition.value) {
                0 -> items(posts, key = { post -> post.id }) { post ->
                    CardItem(viewModel, post) // Отображение постов

                }

                1 -> items(events) { event ->
                    CardItem(viewModel, event) // Отображение событий
                }
                2 -> {
                    // Контент для пользователей
                }
            }

        }

    }

}

