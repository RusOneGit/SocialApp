package rus.one.app.common

import rus.one.app.posts.Attachment
import rus.one.app.posts.Coords
import rus.one.app.profile.Media
import rus.one.app.profile.User
import java.time.LocalDateTime
import java.time.OffsetDateTime

interface Item {
    val id: Long
    val authorId: Int
    val author: String
    val authorJob: String
    val authorAvatar: String// URL
    val content: String
    val published: OffsetDateTime
    val coords: Coords?
    val link: String?
    val mentionIds: List<Int>?
    val mentionedMe: Boolean
    val likeOwnerIds: List<Int>?
    val likedByMe: Boolean
    val attachment: Attachment?
    val users: Map<String, User>?
}