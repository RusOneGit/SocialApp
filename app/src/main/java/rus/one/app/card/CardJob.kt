package rus.one.app.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import okhttp3.internal.format
import rus.one.app.profile.Jobs
import rus.one.app.util.formatIsoDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardJob(job: Jobs) {
    val startDate = job.start?.let { formatIsoDate(it) } ?: "не указано"
    val finishDate = when {
        job.finish.isNullOrBlank() || job.finish == "null" -> "по сей день"
        else -> formatIsoDate(job.finish)
    }
    val linkJob =  if(job.link.isNullOrBlank()) "не указано" else job.link
    Card(modifier = Modifier.fillMaxWidth().padding(4.dp), shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(8.dp), border = BorderStroke(width = 1.dp, color = Color(0xFF6750A4))) {
       Column(modifier = Modifier.padding(8.dp)){
            Text("Место работы: ${job.name}")
            Text("Должность: ${job.position}")
            Text("Начало работы: ${startDate}")
           Text("Окончание работы: ${finishDate}")
           Text("Ссылка на компанию: ${linkJob}")

        }


    }
}