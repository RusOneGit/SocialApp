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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rus.one.app.R
import rus.one.app.card.ContentCard
import rus.one.app.card.HeadDetailsPost
import rus.one.app.card.PostData
import rus.one.app.components.bar.TopBar
import rus.one.app.posts.Post
import rus.one.app.posts.data.PostRepository
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
            PostData(post.published, Color(0xff49454F))
            ContentCard(post.content, post.attachment, Color(0xff49454F))

    }
}}


