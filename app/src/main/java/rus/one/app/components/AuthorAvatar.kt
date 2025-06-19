package rus.one.app.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import rus.one.app.R

@Composable
fun AuthorAvatar(
    avatarUrl: String?,
    name: String,
) {
    Log.d("AuthorAvatar", "Загрузка изображения для $name из $avatarUrl")
    if (avatarUrl != null)
    AsyncImage(

        model = ImageRequest.Builder(LocalContext.current)
            .data(avatarUrl)
            .build(),
        contentDescription = name,
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .size(40.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
    else {
        Log.d("AuthorAvatar", "Загрузка изображения для $name из текстового поля")
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray)

        ) {
            Text(
                text = name.firstOrNull()?.uppercase() ?: "?",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}