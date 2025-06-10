package rus.one.app.posts

import android.os.Build
import androidx.annotation.RequiresApi
import rus.one.app.common.Item
import rus.one.app.profile.User
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data class Post(
    override val id: Long,
    override val authorId: Int,
    override val author: String,
    override val authorJob: String,
    override val authorAvatar: String, // URL
    override val content: String,
    override val published:  String,
    override val coords: Coords?,
    override val link: String?,
    override val mentionIds: List<Int>?,
    override val mentionedMe: Boolean,
    override val likeOwnerIds: List<Int>?,
    override val likedByMe: Boolean,
    override val attachment: Attachment?,
    override val users: Map<String, User>?,
): Item

data class Coords(
    val lat: Double,
    val long: Double
)

data class Attachment(
    val url: String,
    val type: String // Можно сделать enum, например IMAGE, VIDEO и т.д.
)

@RequiresApi(Build.VERSION_CODES.O)
val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

@RequiresApi(Build.VERSION_CODES.O)
val testPost = Post(
    id = 0,
    authorId = 0,
    author = "Иван Иванов",
    authorJob = "Developer",
    authorAvatar = "https://example.com/avatar.jpg",
    content = "Пример контента",
    published = "2025-06-06T18:33:56.930Z",
    coords = Coords(55.7558, 37.6173),
    link = "https://example.com",
    mentionIds = listOf(1, 2),
    mentionedMe = true,
    likeOwnerIds = listOf(1, 3),
    likedByMe = true,
    attachment = Attachment("https://example.com/image.jpg", "IMAGE"),
    users = mapOf(
        "1" to User(id = 1, avatar = "https://example.com/user1.jpg", name = "User One", login = "", age = 0, status = true),
        "2" to User(id = 2, avatar = "https://example.com/user2.jpg", name = "User Two", login = "", age = 0, status = true)
    ))