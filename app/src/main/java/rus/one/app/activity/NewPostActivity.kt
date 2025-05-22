package rus.one.app.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import rus.one.app.NavigationItem
import rus.one.app.R
import rus.one.app.components.bar.BottomBarNewPost
import rus.one.app.components.bar.TopBar
import rus.one.app.posts.Post
import rus.one.app.profile.user
import rus.one.app.viewmodel.ViewModelCard
import java.time.LocalDateTime

@AndroidEntryPoint
class NewPostActivity : ComponentActivity() {

    private val viewModel: ViewModelCard by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewPost(viewModel)
        }
    }
}

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun NewPost(viewModel: ViewModelCard) {
        val message = remember { mutableStateOf("") }
        val context = LocalContext.current
        val selectedItemPosition = remember { mutableStateOf(0) }


        Scaffold(topBar = {
            TopBar(
                title = stringResource(R.string.newPost),
                onBackClick = { (context as? NewPostActivity)?.finish() },
                onClick = {

                    Log.d("кнопка", "хуяк")
                    val newPost = Post(
                        id = 444,
                        author = user.copy(name = "Diana Huesoska"),
                        content = message.value,
                        date = LocalDateTime.now()
                    )
                    viewModel.add(newPost)

                    (context as? NewPostActivity)?.finish()
                },
                painterId = R.drawable.ic_done
            )

            Log.d("кнопка", "message.value: ${message.value}")
        }, bottomBar = {

            BottomBarNewPost(
                selectedItemPosition = selectedItemPosition, items = listOf(
                    NavigationItem.Photo,
                    NavigationItem.Attachment,
                    NavigationItem.UsersNoTitle,
                    NavigationItem.Location
                )
            )

        }


        ) { paddingValues ->
            TextField(
                value = message.value,
                onValueChange = { text -> message.value = text },
                maxLines = 20,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent, // Убираем границу при фокусе
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    lineHeight = 20.sp,
                    fontSize = 14.sp,
                    letterSpacing = 0.25.sp,
                    color = Color(0xFF000000),
                    fontWeight = FontWeight(400)

                )

            )
        }

    }

