package rus.one.app.posts

import android.net.Uri
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import rus.one.app.R

sealed class Attachments( val icon: Int) {
    object Photo: Attachments(
        icon = R.drawable.ic_camera
    )

    object Attachment: Attachments(
        icon = R.drawable.ic_attach
    )

    object UsersNoTitle : Attachments(
        icon = R.drawable.ic_people
    )

    object Location : Attachments(
        icon = R.drawable.ic_location
    )
}


@Composable
fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    val context = LocalContext.current

    // Создаем и запоминаем ExoPlayer
    val player = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
            playWhenReady = false  // без автозапуска
            prepare()
        }
    }

    // Освобождаем плеер при уничтожении композиции
    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }

    // Встраиваем PlayerView с контролами
    AndroidView(
        modifier = modifier.aspectRatio(16f / 9f),
        factory = { ctx ->
            PlayerView(ctx).apply {
                this.player = player
                useController = true
            }
        }
    )
}
