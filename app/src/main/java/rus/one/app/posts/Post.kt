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
