package rus.one.app.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatIsoDate(isoDate: String): String {
    val zonedDateTime = ZonedDateTime.parse(isoDate)
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
    return zonedDateTime.format(formatter)
}