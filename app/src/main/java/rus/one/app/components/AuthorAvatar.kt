package rus.one.app.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import rus.one.app.R

@Composable
fun AuthorAvatar(
    avatarUrl: String,
    contentDescription: String
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(avatarUrl)
            .placeholder(R.drawable.ic_account)
            .error(R.drawable.ic_clear)
            .build(),
        contentDescription = contentDescription,
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .size(40.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}