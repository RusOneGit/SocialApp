package rus.one.app.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatIsoDate(isoDate: String): String {
    val zonedDateTime = ZonedDateTime.parse(isoDate)
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
    return zonedDateTime.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertMillisToIsoDate(millis: Long): String {
    val instant = java.time.Instant.ofEpochMilli(millis)
    return DateTimeFormatter.ISO_INSTANT.format(instant)
}