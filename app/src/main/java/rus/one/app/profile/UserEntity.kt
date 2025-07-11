package rus.one.app.profile

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserEntity")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val login: String,
    val avatar: String?,
    val name: String,
    val token: String?

) {
    fun toDto(): User{
        return User(
            id = id,
            avatar = avatar,
            name = name,
            login = login,
            token = token
        )
    }


    companion object{
        fun fromDto(dto: User): UserEntity{
            return UserEntity(
                id = dto.id,
                login = dto.login,
                avatar = dto.avatar,
                name = dto.name,
                token = dto.token
            )
        }

    }

}