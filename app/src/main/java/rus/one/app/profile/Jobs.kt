package rus.one.app.profile


data class Jobs (
    val company: String,
    val title: String,
    val link: String? = null,
    val period: Period
)
