package rus.one.app.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rus.one.app.components.AuthorAvatar
import rus.one.app.profile.User


@Composable
fun UserCard(user: User) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), // Общий отступ для карточки
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {},
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF7FF)),
        border = BorderStroke(1.dp, color = Color(0xFFCAC4D0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AuthorAvatar(user.avatar, user.name)

            Column() {
                Text(modifier = Modifier.padding(4.dp), text = user.name)
                Text(modifier = Modifier.padding(4.dp), text = user.login)
            }

        }

    }
}


@Preview
@Composable
fun PreviewCardUser() {
    val user = User(
        id = 1,
        avatar = "TODO()",
        name = "Egor",
        login = "Baracude"
    )

    UserCard(user)
}