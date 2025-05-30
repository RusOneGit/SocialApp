package rus.one.app.components.button

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import rus.one.app.R
import rus.one.app.viewmodel.ViewModelCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LikeButton(viewModel: ViewModelCard) {
    Log.d("Перерисовка", "button")
    val isLiked = viewModel.isLiked.collectAsState()
    val likesCount = viewModel.likesCount.collectAsState()

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                viewModel.toggleLike()
            }
    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(18.dp),
            tint = Color(0xFF6750A4),
            painter = painterResource(if (isLiked.value) R.drawable.ic_like_on else R.drawable.ic_like_off),
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            color = Color(0xFF6750A4),
            text = likesCount.value.takeIf { it > 0 }?.toString() ?: ""
        )
    }
}