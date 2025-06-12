package rus.one.app.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import rus.one.app.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentCard(content: String, image: String) {
    Text(
        modifier = Modifier.padding(start = 16.dp, end = 20.dp, bottom = 32.dp, top = 8.dp),
        text = content,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        color = Color(0xFF000000),
        fontWeight = FontWeight(400)
    )

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .build(),
        contentDescription = stringResource(R.string.post_image),
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
    )
}
