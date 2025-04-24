package rus.one.app.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rus.one.app.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HeadCard(authorIcon: Int, author: String, date: LocalDateTime) {

    val formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"))


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically // Выравниваем элементы по центру
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp).size(40.dp), // Отступ справа от иконки
            painter = painterResource(authorIcon),
            contentDescription = ""
        )

        Column(
            modifier = Modifier.weight(1f) // Занимает все доступное пространство
        ) {
            Text(modifier = Modifier.padding(4.dp),
                text = author, color = Color(0xff1D1B20), fontWeight = FontWeight(500), fontSize =  16.sp

            )
            Text( modifier = Modifier.padding(4.dp),
                text = formattedDate, color = Color(0xff1D1B20), fontWeight = FontWeight(400), fontSize = 14.sp
            )
        }

        Icon(
            modifier = Modifier.size(24.dp), // Устанавливаем размер иконки
            painter = painterResource(R.drawable.ic_more_vert), tint = Color(0xFF79747E),
            contentDescription = ""
        )
    }
}
