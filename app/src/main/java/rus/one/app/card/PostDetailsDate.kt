package rus.one.app.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostDetailsDate(date: LocalDateTime){
    val formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"))

    Text(text =  formattedDate, modifier = Modifier.align(Alignment.Start))
}