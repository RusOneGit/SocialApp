package rus.one.app.comments

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Comment")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val postId: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: String,
    val likeOwnerIds: String? = null,
    val likedByMe: Boolean = false,
) {

    fun toDto(): Comment {
        return Comment(
            id = id,
            postId = postId,
            authorId = authorId,
            author = author,
            authorAvatar = authorAvatar,
            content = content,
            published = published,
            likeOwnerIds = likeOwnerIds?.split(",")?.mapNotNull { it.toIntOrNull() },
            likedByMe = likedByMe
        )
    }


    companion object {
        fun fromDto(dto: Comment): CommentEntity {
            return CommentEntity(
                id = dto.id,
                postId = dto.postId,
                authorId = dto.authorId,
                author = dto.author,
                authorAvatar = dto.authorAvatar,
                content = dto.content,
                published = dto.published,
                likeOwnerIds = dto.likeOwnerIds?.joinToString(","),
                likedByMe = dto.likedByMe
            )
        }
    }

}
