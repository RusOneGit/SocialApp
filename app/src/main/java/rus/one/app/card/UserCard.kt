package rus.one.app.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rus.one.app.components.AuthorAvatar
import rus.one.app.profile.User


@Composable
fun UserCard( user: User,
              isSelected: Boolean = false, // Добавляем параметр выбора
              onClick: () -> Unit = {}) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), // Общий отступ для карточки
//        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFE8DEF8) else Color(0xFFFEF7FF) ),
        border = BorderStroke(1.dp, color = if (isSelected) Color(0xFF6750A4) else Color(0xFFCAC4D0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AuthorAvatar(modifier = Modifier.padding(start = 8.dp, end = 8.dp),  user.avatar, user.name)

            Column() {
                Text(modifier = Modifier.padding(start = 8.dp, 4.dp), color = Color(0xff1D1B20), text = user.name, fontWeight = FontWeight(500), fontSize =  16.sp, letterSpacing = 0.15.sp, lineHeight = 24.sp)
                Text(modifier = Modifier.padding(start = 8.dp, 4.dp),color = Color(0xff1D1B20), text = user.login, fontWeight = FontWeight(400), fontSize = 14.sp, letterSpacing = 0.25.sp, lineHeight = 20.sp)
            }

        }

    }
}

