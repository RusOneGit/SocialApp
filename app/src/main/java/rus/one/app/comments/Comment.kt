package rus.one.app.comments

data class Comment(
    val id: Long,
    val postId: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: String,
    val likeOwnerIds: List<Int>?,
    val likedByMe: Boolean
)
