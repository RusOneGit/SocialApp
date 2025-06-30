package rus.one.app.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rus.one.app.R
import rus.one.app.card.ContentCardMedia
import rus.one.app.card.ContentCardText
import rus.one.app.card.HeadDetailsPost
import rus.one.app.card.PostData
import rus.one.app.components.AuthorAvatar
import rus.one.app.components.bar.TopBar
import rus.one.app.posts.Post
import rus.one.app.posts.data.PostRepository
import rus.one.app.profile.User
import javax.inject.Inject

@AndroidEntryPoint
class PostDetailActivity : ComponentActivity() {
    @Inject
    lateinit var postRepository: PostRepository
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val postId = intent.getLongExtra("postId", -1L)
        var post: Post? = null

        lifecycleScope.launch {
            post = postRepository.getPostById(postId)
            setContent {
                if (post != null) {
                    DetailPost(post!!)
                } else {
                    // Можно показать экран ошибки или заглушку
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailPost(post: Post) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.post), onBackClick = {(context as? PostDetailActivity)?.finish()}, onClick = {}, R.drawable.ic_share)
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFFFFFF))
                .verticalScroll(scrollState)


            ) {

            HeadDetailsPost(post)
            ContentCardMedia(attachment = post.attachment)
            PostData(modifier = Modifier.padding(16.dp), post.published, Color(0xff49454F))
            ContentCardText(post.content, Color(0xff49454F))
            UserList(post,"Likers")
            Spacer(modifier = Modifier.padding(16.dp))
            UserList(post,"Mentioned")


    }
}}


@Composable
fun UserList(post: Post, listName: String) {
    val avatarSize = 40.dp
    val overlapFraction = 0.3f
    val density = LocalDensity.current

    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            fontWeight = FontWeight(500),
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
            text = listName
        )

        Row(
            modifier = Modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = if (listName == "Likers") {
                    painterResource(R.drawable.ic_like_on)
                } else {
                    painterResource(R.drawable.ic_people_outline)
                },
                contentDescription = null,
                tint = Color(0xFF6750A4),
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 8.dp),
            )

            val users: Map<String, User> = post.users ?: emptyMap()
            val likeOwnerIds: List<Int> = post.likeOwnerIds ?: emptyList()
            val mentionIds: List<Int> = post.mentionIds ?: emptyList()

            // В зависимости от listName выбираем нужный список id
            val filteredUserIds: List<Int> = when (listName) {
                "Likers" -> likeOwnerIds
                "Mentioned" -> mentionIds
                else -> emptyList()
            }

            // Фильтруем пользователей по выбранным id
            val filteredUsers: List<User> = users.filterKeys { it.toInt() in filteredUserIds }.values.toList()

            Text(
                modifier = Modifier.padding(start = 4.dp, end = 16.dp),
                fontWeight = FontWeight(500),
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
                text = filteredUsers.size.toString()
            )

            if (filteredUsers.isNotEmpty()) {
                with(density) {
                    filteredUsers.forEachIndexed { index, user ->
                        AuthorAvatar(
                            modifier = Modifier
                                .graphicsLayer {
                                    translationX = -avatarSize.toPx() * overlapFraction * index
                                }
                                .zIndex(index.toFloat())
                                .border(BorderStroke(2.dp, color = Color.White), CircleShape),
                            avatarUrl = user.avatar,
                            name = user.name
                        )
                    }
                }
            } else {
                Text("No $listName yet", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}


