package rus.one.app.profile

import androidx.room.PrimaryKey


data class Jobs (
    val id: Long,
    val name: String,
    val position: String,
    val start: String,
    val finish: String,
    val link: String
)

data class JobsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val position: String,
    val start: String,
    val finish: String,
    val link: String
){
    fun fromDto(): JobsEntity{

        return JobsEntity(
            id = id,
            name = name,
            position = position,
            start = start,
            finish = finish,
            link = link
        )

    }

    companion object{ fun toDto(dto: Jobs): Jobs{

        return Jobs(
            id = dto.id,
            name = dto.name,
            position = dto.position,
            start = dto.start,
            finish = dto.finish,
            link = dto.link
        )


    }}

}
