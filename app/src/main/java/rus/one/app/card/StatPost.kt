package rus.one.app.card

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import rus.one.app.components.button.LikeButton
import rus.one.app.components.button.MentionedButton
import rus.one.app.components.button.ShareButton
import rus.one.app.events.event
import rus.one.app.viewmodel.ViewModelCard


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatPost(viewModel: ViewModelCard, showMentioned: Boolean = false) {
    val viewModel: ViewModelCard = viewModel


    Row {
        Log.d("Перерисовка", "Статс")

        LikeButton(viewModel)


        Spacer(modifier = Modifier.padding(8.dp))

        ShareButton(
            modifier = Modifier
                .padding(8.dp)
                .size(18.dp),
            Color(0xFF6750A4),
            onShareClick = {})


        if (showMentioned) {
            Spacer(modifier = Modifier.padding(8.dp))
            MentionedButton(event)
        }


    }
}
