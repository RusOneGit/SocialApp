    package rus.one.app.posts.data

    import android.os.Build
    import androidx.annotation.RequiresApi
    import androidx.room.Entity
    import androidx.room.PrimaryKey
    import com.google.gson.Gson
    import com.google.gson.reflect.TypeToken
    import rus.one.app.posts.Attachment
    import rus.one.app.posts.Coords
    import rus.one.app.posts.Post
    import rus.one.app.profile.User
    import java.time.format.DateTimeFormatter

    @Entity(tableName = "PostEntity")
    data class PostEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,

        val authorId: Int,
        val authorName: String,
        val authorJob: String?,
        val authorAvatar: String?, // URL
        val published: String,
        val content: String,
        val attachment: String? = null, // URL медиа

        val coords: String? = null, // Сохраняем координаты как строку "lat,long"
        val link: String? = null,
        val mentionIds: String? = null, // Сохраняем как JSON или строку, например "1,2,3"
        val mentionedMe: Boolean = false,
        val likeOwnerIds: String? = null, // Сохраняем как JSON или строку
        val likedByMe: Boolean = false,
        val likesCount: Int = 0,
        val users: String? = null // Сохраняем как JSON или строку
    ) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun toDto(): Post {
            val latLong = coords?.split(",")?.map { it.toDouble() }
            val coordinates = latLong?.let { Coords(it[0], it[1]) }
            val usersMap: Map<String, User>? = users?.let {parseUsersJson(it)}
            val attachmentObj: Attachment? = attachment?.let {
                try {
                    Gson().fromJson(it, Attachment::class.java)
                } catch (e: Exception) {
                    null
                }
            }


            return Post(
                id = id,
                authorId = authorId,
                author = authorName,
                authorJob = authorJob.toString(),
                authorAvatar = authorAvatar.toString(),
                content = content,
                published = published,
                coords = coordinates,
                link = link,
                mentionIds = mentionIds?.split(",")?.mapNotNull { it.toIntOrNull() },
                likeOwnerIds = likeOwnerIds?.split(",")?.mapNotNull { it.toIntOrNull() },
                mentionedMe = mentionedMe,
                likedByMe = likedByMe,
                attachment = attachmentObj, // Предполагаем, что это изображение
                users = usersMap // Вам нужно реализовать этот метод
            )
        }

        companion object {
            fun fromDto(dto: Post): PostEntity {
                val usersJson = dto.users?.let {convertUsersToJson(it) }
                val attachmentJson: String? = dto.attachment?.let {
                    try {
                        Gson().toJson(it)
                    } catch (e: Exception) {
                        null
                    }
                }

                return PostEntity(
                    id = dto.id,
                    authorId = dto.authorId,
                    authorName = dto.author,
                    authorJob = dto.authorJob,
                    authorAvatar = dto.authorAvatar,
                    content = dto.content,
                    attachment = attachmentJson ,
                    coords = dto.coords?.let { "${it.lat},${it.long}" },
                    link = dto.link,
                    published = dto.published,
                    mentionIds = dto.mentionIds?.joinToString(","),
                    mentionedMe = dto.mentionedMe,
                    likeOwnerIds = dto.likeOwnerIds?.joinToString(","),
                    likedByMe = dto.likedByMe,
                    users = usersJson
                )
            }

                private fun parseUsersJson(json: String): Map<String, User> {
                    val gson = Gson()
                    val type = object : TypeToken<Map<String, User>>() {}.type
                    return gson.fromJson(json, type)
                }

                private fun convertUsersToJson(users: Map<String, User>): String {
                    val gson = Gson()
                    return gson.toJson(users)
                }
        }

    }
