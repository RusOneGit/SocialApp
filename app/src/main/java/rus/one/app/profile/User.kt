package rus.one.app.profile

import rus.one.app.R

data class User(
    val avatar: Int,
    val name: String,
    val login: String,
    val age: Int,
    val status: Boolean,

    val job: MutableList<Jobs>? = null,
    val friends: MutableList<User>? = null
)

val user = User(R.drawable.ic_account, "Elena", "lenka", 22, true)