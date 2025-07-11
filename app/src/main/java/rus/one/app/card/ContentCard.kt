package rus.one.app.card

import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import rus.one.app.R
import rus.one.app.posts.Attachment
import rus.one.app.posts.VideoPlayer


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentCardText(content: String, color: Color) {


        Text(
            modifier = Modifier.padding(start = 16.dp, end = 20.dp, bottom = 32.dp, top = 8.dp),
            text = content,
            lineHeight = 20.sp,
            fontSize = 14.sp,
            letterSpacing = 0.25.sp,
            color = color,
            fontWeight = FontWeight(400)
        )
}
@Composable
fun ContentCardMedia( attachment: Attachment? = null){

    when (attachment?.type) {
        "IMAGE" -> AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(attachment.url).build(),
            contentDescription = stringResource(R.string.post_image),
            modifier = Modifier
                .fillMaxWidth()
        )

        "VIDEO" -> VideoPlayer(attachment.url)

        "AUDIO" -> {
            var isPlaying by remember { mutableStateOf(false) }
            val mediaPlayer = remember {
                MediaPlayer().apply {
                    setDataSource(attachment.url)
                    prepareAsync()
                    setOnPreparedListener {
                        // Можно автоматически начать воспроизведение, если нужно
                    }
                    setOnCompletionListener {
                        isPlaying = false
                    }
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    mediaPlayer.release()
                }
            }

            Row(modifier = Modifier.padding(8.dp)) {
                IconButton(onClick = {
                    if (isPlaying) {
                        mediaPlayer.pause()
                    } else {
                        mediaPlayer.start()
                    }
                    isPlaying = !isPlaying
                }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Refresh else Icons.Filled.PlayArrow,
                        contentDescription = if (isPlaying) "Pause audio" else "Play audio"
                    )
                }
                Text(text = "Audio")
            }
        }


        else -> {}
    }


}

