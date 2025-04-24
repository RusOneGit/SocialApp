package rus.one.app.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import rus.one.app.R

@Composable
fun BackButton(onBackClick: () -> Unit) {
    Icon(
        painter = painterResource(R.drawable.ic_back),
        contentDescription = stringResource(R.string.back),
        tint = Color.DarkGray,
        modifier = Modifier
            .padding(8.dp)
            .size(32.dp)
            .clickable(onClick = onBackClick)
    )
}
