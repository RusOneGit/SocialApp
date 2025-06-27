package rus.one.app.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CameraButton(iconID: Int, contentDescription: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .padding(16.dp)
            .size(160.dp)
            .clip(RoundedCornerShape(50))
            .background(color = Color(0xFFC4C4C4))
            .clickable{ onClick()},

        Alignment.Center
    ) {
        Icon(
            painterResource(id = iconID),
            contentDescription = contentDescription,
            modifier = Modifier
                .size(80.dp)
        )
    }
}