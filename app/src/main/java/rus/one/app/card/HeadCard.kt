package rus.one.app.card

import DropdownMenuWithViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rus.one.app.R
import rus.one.app.posts.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HeadCard(post: Post) {
    val formattedDate = post.date.format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"))
    val expanded = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically // Выравниваем элементы по центру
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .size(40.dp), // Отступ справа от иконки
            painter = painterResource(post.author.avatar),
            contentDescription = ""
        )

        Column(
            modifier = Modifier.weight(1f) // Занимает все доступное пространство
        ) {
            Text(modifier = Modifier.padding(4.dp),
                text = post.author.name, color = Color(0xff1D1B20), fontWeight = FontWeight(500), fontSize =  16.sp, letterSpacing = 0.15.sp, lineHeight = 24.sp

            )
            Text( modifier = Modifier.padding(4.dp),
                text = formattedDate, color = Color(0xff1D1B20), fontWeight = FontWeight(400), fontSize = 14.sp, letterSpacing = 0.25.sp, lineHeight = 20.sp
            )
        }

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    expanded.value = true
                }, // Устанавливаем размер иконки
            painter = painterResource(R.drawable.ic_more_vert), tint = Color(0xFF79747E),
            contentDescription = ""
        )
    }

    DropdownMenuWithViewModel(expanded = expanded.value, onDismiss = { expanded.value = false }, post)

}
