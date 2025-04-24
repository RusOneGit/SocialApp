package rus.one.app.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rus.one.app.posts.Post
import rus.one.app.posts.post
import rus.one.app.profile.User
import rus.one.app.profile.user

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardPost(post: Post, user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF7FF)),
        border = BorderStroke(1.dp, color = Color(0xFFCAC4D0))
    ) {
        HeadCard(
            authorIcon = user.avatar,
            author = user.name,
            date = post.date
        )
        ContentCard(content = post.content)
        StatPost(likesCount = post.likesCount)
    }
}


@Composable
fun MediaCard() {

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewPost() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { CardPost(post, user) }


}

