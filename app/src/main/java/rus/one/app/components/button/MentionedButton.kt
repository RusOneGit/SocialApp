package rus.one.app.components.button

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import rus.one.app.R
import rus.one.app.viewmodel.EventViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MentionedButton(viewModel: EventViewModel, itemID: Long, modifier: Modifier) {
    val items by viewModel.feedState.collectAsState()
    val isMentioned = remember { mutableStateOf(false) }
    val event = items.item.find { it.id == itemID }

    Row(
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = { isMentioned.value = !isMentioned.value }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(22.dp),
            tint = Color(0xFF6750A4),
            painter = painterResource(if (isMentioned.value) R.drawable.ic_people_no_outline else R.drawable.ic_people_outline),
            contentDescription = null
        )
        event?.participantsIds?.size?.let {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                color = Color(0xFF6750A4),
                text =  if(it > 0) event.participantsIds.size.toString() else ""
            )
        }
    }

}