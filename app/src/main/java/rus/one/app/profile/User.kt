package rus.one.app.profile

import android.os.Build
import androidx.annotation.RequiresApi
import rus.one.app.R
import java.util.Date


data class User(
    val id: Int,
    val avatar: String?,
    val name: String,
    val login: String)

//    val job: MutableList<Jobs>? = null,
//    val friends: MutableList<User>? = null,
//
//    ){
//    val actualJob: String?
//        get() = job?.firstOrNull()?.title
//}
