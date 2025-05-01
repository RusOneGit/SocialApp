package rus.one.app.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import rus.one.app.R
import rus.one.app.card.CardItem
import rus.one.app.components.bar.BottomBar
import rus.one.app.components.button.ProfileButton
import rus.one.app.events.event
import rus.one.app.posts.post
import rus.one.app.viewmodel.ViewModelCard

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ViewModelCard>()

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

    val selectedItemPosition = remember { mutableStateOf(0) }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {}) {
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
        BottomBar(selectedItemPosition)
    }) { paddingValues ->  // добавляем параметр padding
        // используем padding при создании содержимого
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (selectedItemPosition.value) {
                0 -> repeat(3) { item { CardItem(viewModel, post)  }} // Контент для Posts
                1 -> repeat(3) { item { CardItem(viewModel, event)  }} // Контент для Events
                2 -> {
                } // Контент для Users
            }

        }

    }
}

