package rus.one.app.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp


@Composable
fun TopButton(modifier: Modifier = Modifier, color: Color, onClick: () -> Unit, painter: Painter?) {

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = { onClick })
    ) {
        painter?.let {
            Icon(
                modifier = modifier,
                painter = it,
                contentDescription = null,
                tint = color
            )

        }
    }
}

