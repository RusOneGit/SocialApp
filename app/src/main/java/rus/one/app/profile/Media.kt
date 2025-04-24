package rus.one.app.profile

sealed class Media {
    data class Link(val url: String) : Media()
    data class Photo(val url: Int) : Media()
    data class Video(val url: String) : Media()
}