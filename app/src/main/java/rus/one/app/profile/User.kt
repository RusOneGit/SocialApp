package rus.one.app.profile

import android.os.Build
import androidx.annotation.RequiresApi
import rus.one.app.R
import java.util.Date


data class User(
    val id: Int,
    val avatar: String,
    val name: String,
    val login: String,
    val age: Int,
    val status: Boolean,

    val job: MutableList<Jobs>? = null,
    val friends: MutableList<User>? = null,

    ){
    val actualJob: String?
        get() = job?.firstOrNull()?.title
}

//@RequiresApi(Build.VERSION_CODES.O)
//val user = User(1,R.drawable.img, "Elena", "lenka", 22, true, job = mutableListOf(Jobs("Microsoft", "Manager",
//    period = Period(  Date(2020, 3, 1),
//        Date(2024, 4, 7)))))