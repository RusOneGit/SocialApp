package rus.one.app.events

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rus.one.app.posts.Attachment
import rus.one.app.posts.Coords
import rus.one.app.profile.User

@Entity(tableName = "EventEntity")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val authorId: Int,
    val authorName: String,
    val authorJob: String?,
    val authorAvatar: String?, // URL
    val published: String,
    val content: String,
    val datetime: String,
    val type: String,
    val coords: String? = null,
    val likeOwnerIds: String? = null,
    val likedByMe: Boolean = false,
    val speakerIds: String? = null,
    val participantsIds: String? = null,
    val participatedByMe: Boolean = false,
    val attachment: String,
    val link: String? = null,
    val users: String? = null,
) {
    fun toDto(): Event {
        val latLong = coords?.split(",")?.map { it.toDouble() }
        val coordinates = latLong?.let { Coords(it[0], it[1]) }
        val usersMap: Map<String, User>? = users?.let { EventEntity.Companion.parseUsersJson(it) }

        return Event(
            id = id,
            authorId = authorId,
            author = authorName,
            authorJob = authorJob.toString(),
            authorAvatar = authorAvatar.toString(),
            content = content,
            published = published,
            coords = coordinates,
            link = link,
            likeOwnerIds = likeOwnerIds?.split(",")?.mapNotNull { it.toIntOrNull() },
            likedByMe = likedByMe,
            attachment = attachment?.let { Attachment(it, "IMAGE") },
            users = usersMap,
            type = type.let { EventType.valueOf(it) },
            datetime = datetime,
            speakerIds = speakerIds?.split(",")?.mapNotNull { it.toIntOrNull() },
            participantsIds = participantsIds?.split(",")?.mapNotNull { it.toIntOrNull() },
            participatedByMe = participatedByMe
        )

    }

    companion object {
        fun fromDto(dto: Event): EventEntity {

            val usersJson = dto.users?.let { EventEntity.Companion.convertUsersToJson(it) }

            return EventEntity(
                id = dto.id,
                authorId = dto.authorId,
                authorName = dto.author,
                authorJob = dto.authorJob,
                authorAvatar = dto.authorAvatar,
                published = dto.published,
                content = dto.content,
                datetime = dto.datetime,
                type = dto.type.name,
                coords = dto.coords?.let { "${it.lat},${it.long}" },
                likeOwnerIds = dto.likeOwnerIds?.joinToString(","),
                likedByMe = dto.likedByMe,
                speakerIds = dto.speakerIds?.joinToString(","),
                participantsIds = dto.participantsIds?.joinToString(","),
                participatedByMe = dto.participatedByMe,
                attachment = dto.attachment?.url ?: "",
                link = dto.link,
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
