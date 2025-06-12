package rus.one.app.events

import rus.one.app.common.Item
import rus.one.app.posts.Attachment
import rus.one.app.posts.Coords
import rus.one.app.profile.User

data class Event(
    override val id: Long,
    override val authorId: Int,
    override val author: String,
    override val authorJob: String,
    override val authorAvatar: String, // URL
    override val content: String,
    override val published: String,
    override val coords: Coords?,
    override val link: String?,
    override val likeOwnerIds: List<Int>?,
    override val likedByMe: Boolean,
    override val attachment: Attachment?,
    override val users: Map<String, User>?,

    val participatedByMe: Boolean,
    val eventType: EventType,
    val dataTime: String,
    val speakerIds: List<Int>? = null,
    val participantsIds: List<Int>? = null,
) : Item

enum class EventType {
    ONLINE,
    OFFLINE,
    online,
    Online,
    offline,
    Offline
}

//const val content =
//    "Приглашаю провести уютный вечер за увлекательными играми! У нас есть несколько вариантов настолок, подходящих для любой компании. https://m2.material.io/components/cards"
//@RequiresApi(Build.VERSION_CODES.O)
//val event = Event(
//    id = 1,
//    author = user,
//    content = content,
//    date = LocalDateTime.now(),
//    eventDate = LocalDateTime.now(),
//    eventType = EventType.Offline
//)

