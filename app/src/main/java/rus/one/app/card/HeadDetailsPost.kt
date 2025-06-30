package rus.one.app.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rus.one.app.components.AuthorAvatar
import rus.one.app.posts.Post

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HeadDetailsPost(post: Post) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically // Выравниваем элементы по центру
    ) {
       AuthorAvatar(modifier = Modifier,post.authorAvatar, post.author)

        Column(
            modifier = Modifier.weight(1f) // Занимает все доступное пространство
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = post.author,
                color = Color(0xff1D1B20),
                fontWeight = FontWeight(500),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = post.authorJob ?: "В поиске работы",
                color = Color(0xff1D1B20),
                fontWeight = FontWeight(400),
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
                fontSize = 14.sp
            )
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewHeadCard(){

    val newpost = Post(
        id = 44444,
        authorId = 44444,
        author = "John",
        authorJob = "Developer",
        authorAvatar = "https://www.gstatic.com/devrel-devsite/prod/vd980a342b8e3e77c07209be506f8385246f583d6eec83ceb07569bbf26f054dc/android/images/lockup.png",
        content = "Hello, My Friends",
        published = "2023-06-01T12:34:56",
        coords = null,
        link = null,
        mentionIds = null,
        mentionedMe = false,
        likeOwnerIds = null,
        likedByMe = false,
        attachment = null,
        users = null
    )
    HeadDetailsPost(newpost)

}
