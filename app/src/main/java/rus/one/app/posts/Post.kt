package rus.one.app.posts

import android.os.Build
import androidx.annotation.RequiresApi
import rus.one.app.common.Item
import rus.one.app.profile.Media
import rus.one.app.profile.User
import rus.one.app.profile.user
import java.time.LocalDateTime

data class Post(
    override val id: Int,
    override val author: User,
    override val content: String,
    override val date: LocalDateTime,
    override val media: Media? = null,
    override val likesCount: Int = 0,
    override val likers: MutableList<User>? = null,

    val mentioned: MutableList<User>? = null
) : Item


const val content =
    "Слушайте, а как вы относитесь к тому, чтобы собраться большой компанией и поиграть в настолки? У меня есть несколько клевых игр, можем устроить вечер настолок! Пишите в лс или звоните"
@RequiresApi(Build.VERSION_CODES.O)
val post = Post(
    2,
    user, content, LocalDateTime.now(), media = null)