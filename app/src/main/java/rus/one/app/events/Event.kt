package rus.one.app.events

import android.os.Build
import androidx.annotation.RequiresApi
import rus.one.app.common.Item
import rus.one.app.profile.Media
import rus.one.app.profile.User
import rus.one.app.profile.user
import java.time.LocalDateTime

data class Event(
    override val id: Int,
    override val author: User,
    override val content: String,
    override val date: LocalDateTime,
    override val media: Media? = null,
    override val likesCount: Int = 0,
    override val likers: MutableList<User>? = null,

    val eventType: EventType,
    val eventDate: LocalDateTime,
    val mentionedCount: Int = 0,
    val speakers: MutableList<User>? = null,
    val participants: MutableList<User>? = null,
) : Item

enum class EventType {
    Online,
    Offline
}

const val content =
    "Приглашаю провести уютный вечер за увлекательными играми! У нас есть несколько вариантов настолок, подходящих для любой компании. https://m2.material.io/components/cards"
@RequiresApi(Build.VERSION_CODES.O)
val event = Event(
    id = 1,
    author = user,
    content = content,
    date = LocalDateTime.now(),
    eventDate = LocalDateTime.now(),
    eventType = EventType.Offline
)

