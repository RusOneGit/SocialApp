package rus.one.app.profile

import java.time.Period

data class Jobs (
    val company: String,
    val title: String,
    val link: String? = null,
    val duration: Period
)
