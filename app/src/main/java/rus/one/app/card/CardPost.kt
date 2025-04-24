package rus.one.app.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rus.one.app.R
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
    CardPost(post, user)

}


@Composable
fun LikeButton(post: Post) {
    val isLiked = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = { isLiked.value = !isLiked.value })
    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(18.dp),
            tint = Color(0xFF6750A4),
            painter = painterResource(if (isLiked.value) R.drawable.ic_like_on else R.drawable.ic_like_off),
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            color = Color(0xFF6750A4),
            text = post.likesCount.toString()
        )
    }

}