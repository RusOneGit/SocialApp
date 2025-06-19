package rus.one.app.common

import rus.one.app.posts.Attachment
import rus.one.app.posts.Coords
import rus.one.app.profile.User

interface Item {
    val id: Long
    val authorId: Int
    val author: String
    val authorJob: String
    val authorAvatar: String?// URL
    val content: String
    val published: String
    val coords: Coords?
    val link: String?
    val likeOwnerIds: List<Int>?
    val likedByMe: Boolean
    val attachment: Attachment?
    val users: Map<String, User>?
}
sealed class FetchResult {
    data class Success(val item: List<Item>) : FetchResult()
    data class Error(val errorMessage: String) : FetchResult()
}