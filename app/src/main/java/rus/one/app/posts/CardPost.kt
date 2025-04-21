package rus.one.app.posts

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rus.one.app.R
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
        HeadCardPost(
            authorIcon = user.avatar,
            author = user.name,
            date = post.date
        )
        ContentCard(content = post.content, likesCount = post.likesCount)

    }
}

@Composable
fun ContentCard(content: String, media: Media? = null, likesCount: Int? = null) {
    Text(
        modifier = Modifier.padding(start = 16.dp, end = 20.dp, bottom = 32.dp, top = 8.dp),
        text = content,
        color = Color(0xFF49454F),
        fontWeight = FontWeight(400)
    )

    Row {

        LikeButton(post)


        Spacer(modifier = Modifier.padding(8.dp))



        Row(
            modifier = Modifier
                .padding(8.dp)
                .clickable(onClick = {})
        ) {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .size(18.dp),
                painter = painterResource(R.drawable.ic_share),
                tint = Color(0xFF6750A4),
                contentDescription = null
            )
        }

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
fun MediaContent(media: Media?) {
    when (media) {
        is Media.Link -> {
            // Отображение ссылки
            Text(text = "Ссылка: ${media.url}")
        }

        is Media.Photo -> {
            // Отображение фото
            Image(
                painter = painterResource(media.url),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth() // Задайте нужные размеры
            )
        }

        is Media.Video -> {
            // Отображение видео
            // VideoPlayer(url = media.url) // Предполагается, что у вас есть компонент VideoPlayer
        }

        null -> {
            // Обработка случая, когда медиа отсутствует
            Text(text = "Нет медиа")
        }
    }
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