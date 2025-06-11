package rus.one.app.posts.ui.activity

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import rus.one.app.R
import rus.one.app.components.bar.BottomBarNewPost
import rus.one.app.components.bar.TopBar
import rus.one.app.posts.Attachments
import rus.one.app.posts.Post

import rus.one.app.viewmodel.ViewModelCard

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

                    val post = Post(
                        id = 2, // 0, чтобы база сгенерировала id
                        authorId = 22,
                        author = "Никитос",
                        authorJob = "Разработчик",
                        authorAvatar = "https://example.com/avatar.png",
                        content = message.value,
                        published = "OffsetDateTime.now()",
                        coords = null,
                        link = "fjf",
                        mentionIds = null,
                        mentionedMe = false,
                        likeOwnerIds = null,
                        likedByMe = false,
                        attachment = null,
                        users = emptyMap()
                    )
                    viewModel.add(post)
                    viewModel.getPosts()


                    (context as? NewPostActivity)?.finish()
                },
                painterId = R.drawable.ic_done
            )

            Log.d("кнопка", "message.value: ${message.value}")
        }, bottomBar = {

            BottomBarNewPost(
                selectedItemPosition = selectedItemPosition, items = listOf(
                    Attachments.Photo,
                    Attachments.Attachment,
                    Attachments.UsersNoTitle,
                    Attachments.Location
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

