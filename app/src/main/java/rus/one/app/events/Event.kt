package rus.one.app.events

import android.os.Build
import androidx.annotation.RequiresApi
import rus.one.app.R
import rus.one.app.profile.Media
import java.time.LocalDateTime

data class Event(
    val eventID: Int,
    val author: String,
    val authorID: Int,
    val publicationDate: LocalDateTime,
    val eventType: EventType,
    val eventDate: LocalDateTime,
    val content: String,
    val media: Media? = null,
    val likesCount: Int? = null
    )

val content =
    "Приглашаю провести уютный вечер за увлекательными играми! У нас есть несколько вариантов настолок, подходящих для любой компании. https://m2.material.io/components/cards"
@RequiresApi(Build.VERSION_CODES.O)
val event = Event(1, "Elena", 2, LocalDateTime.now(),EventType.Offline, LocalDateTime.now(), content, Media.Photo(
    R.drawable.ic_launcher_background))