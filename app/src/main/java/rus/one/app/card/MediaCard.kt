package rus.one.app.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import rus.one.app.profile.Media

@Composable
fun MediaContent(media: Media?) {
    when (media) {
        is Media.Link -> {
            // Отображение ссылки
            Text(text = "Ссылка: ${media.url}")
        }

        is Media.Photo -> {
            // Отображение фото
            Image(
                painter = painterResource(media.url),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth() // Задайте нужные размеры
            )
        }

        is Media.Video -> {
            // Отображение видео
            // VideoPlayer(url = media.url) // Предполагается, что у вас есть компонент VideoPlayer
        }

        null -> {
            // Обработка случая, когда медиа отсутствует
            Text(text = "Нет медиа")
        }
    }
}