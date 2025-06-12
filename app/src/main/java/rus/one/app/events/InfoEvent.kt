package rus.one.app.events

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rus.one.app.util.formatIsoDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoEvent(type: EventType, evenDateTime: String){


    Column(modifier = Modifier.padding(8.dp)) {
        Text(modifier = Modifier.padding(start = 8.dp), text = type.name, color = Color(0xFF1D1B20), fontSize = 16.sp )
        Spacer(modifier = Modifier.padding(bottom = 4.dp))
        Text(modifier = Modifier.padding(start = 8.dp, bottom = 8.dp), text = formatIsoDate(evenDateTime), textAlign = TextAlign.Center, color = Color(0xFF49454F), fontSize = 14.sp)
    }

}
