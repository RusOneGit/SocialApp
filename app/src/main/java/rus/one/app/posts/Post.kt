package rus.one.app.posts

import android.os.Build
import androidx.annotation.RequiresApi
import rus.one.app.profile.Media
import rus.one.app.profile.User
import rus.one.app.profile.user
import java.time.LocalDateTime

data class Post(
    val postID: Int,
    val author: String,
    val authorID: Int,
    val authorIcon: Int,
    val content: String,
    val date: LocalDateTime,
    val media: Media? = null,
    val likesCount: Int? = null,

    val likers: MutableList<User>? = null,
    val mentioned: MutableList<User>? = null,
)

val content =
    "Слушайте, а как вы относитесь к тому, чтобы собраться большой компанией и поиграть в настолки? У меня есть несколько клевых игр, можем устроить вечер настолок! Пишите в лс или звоните"
@RequiresApi(Build.VERSION_CODES.O)
val post = Post(
    2,
    user.name, 3, user.avatar, content, LocalDateTime.now(), media = null, 3
)