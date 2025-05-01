package rus.one.app.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import rus.one.app.R
import rus.one.app.card.ContentCard
import rus.one.app.card.HeadDetailsPost
import rus.one.app.card.MediaContent
import rus.one.app.card.PostDetailsDate
import rus.one.app.card.StatPost
import rus.one.app.components.bar.TopBar
import rus.one.app.events.EventType
import rus.one.app.events.InfoEvent
import rus.one.app.posts.post
import rus.one.app.profile.user
import rus.one.app.viewmodel.ViewModelCard
import java.time.LocalDateTime

class PostDetailActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DetailPost()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DetailPost() {
    val viewModel = ViewModelCard()

    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.post), onBackClick = {(context as? PostDetailActivity)?.finish()}, onShareClick = {})
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFFFFFF)),


            ) {
            HeadDetailsPost(post.author.avatar, post.author.name, user.actualJob)
            MediaContent(media = post.media)
            PostDetailsDate(post.date)
            InfoEvent(EventType.Online, LocalDateTime.now())
            ContentCard(post.content)
        }

    }
}


