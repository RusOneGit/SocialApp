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
fun ShareButton(onShareClick: () -> Unit){
    Icon(
        painter =  painterResource(R.drawable.ic_share),
        contentDescription = stringResource(R.string.share),
        tint =  Color(0xFF000000),
        modifier = Modifier
            .padding(8.dp)
            .size(32.dp)
            .clickable {onShareClick}

    )
}