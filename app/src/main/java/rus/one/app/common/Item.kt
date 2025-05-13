package rus.one.app.common

import rus.one.app.profile.Media
import rus.one.app.profile.User
import java.time.LocalDateTime

interface Item {
    val id: Int
    val author: User
    val content: String
    val date: LocalDateTime
    val media: Media?
    val likesCount: Int
    val likers: MutableList<User>?
}