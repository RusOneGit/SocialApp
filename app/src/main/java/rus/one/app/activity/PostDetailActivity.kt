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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import rus.one.app.R
import rus.one.app.card.ContentCard
import rus.one.app.card.HeadDetailsPost
import rus.one.app.card.MediaContent
import rus.one.app.card.PostDetailsDate
import rus.one.app.components.bar.TopBar
import rus.one.app.posts.post
import rus.one.app.profile.user

class PostDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DetailPost() {
    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.post), onBackClick = {}, onShareClick = {})
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFFFFFF)),


            ) {
            HeadDetailsPost(user.avatar, user.name, user.actualJob)
            MediaContent(media = post.media)
            PostDetailsDate(post.date)
            ContentCard(post.content)
        }

    }
}


