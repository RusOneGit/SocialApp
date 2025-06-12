package rus.one.app.posts

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
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

    object Location: Attachments(
        icon = R.drawable.ic_location
    )




}


@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(url: String) {
    val context = LocalContext.current

    // Добавляем состояние для отслеживания нажатия
    var isPlaying by remember { mutableStateOf(false) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            // Сначала не воспроизводим
            playWhenReady = false
            prepare()
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Компонуем кнопку воспроизведения
    Column {
        if (!isPlaying) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        isPlaying = true
                        exoPlayer.playWhenReady = true
                    }
                    .background(Color.Black)
            ) {
                Image(
                    painter = painterResource(R.drawable.place_holder),
                    contentDescription = "Preview",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                // Добавляем кнопку воспроизведения
                IconButton(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp),
                    onClick = {
                        isPlaying = true
                        exoPlayer.playWhenReady = true
                    }
                ) {

                }
            }
        } else {
            // Отображаем PlayerView после нажатия
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                        useController = true
                        setUseArtwork(true)
                        defaultArtwork = ContextCompat.getDrawable(context, R.drawable.place_holder)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

