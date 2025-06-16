package rus.one.app.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import rus.one.app.common.Item
import rus.one.app.events.Event
import rus.one.app.events.InfoEvent
import rus.one.app.posts.Post
import rus.one.app.viewmodel.ViewModelCard


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardItem(
    viewModel: ViewModelCard,
    item: Item,
    paddingValues: PaddingValues,
    onClick: (Item) -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp,top = 4.dp, bottom = 4.dp, end = 8.dp), // Общий отступ для карточки
//        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = { onClick(item) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF7FF)),
        border = BorderStroke(1.dp, color = Color(0xFFCAC4D0))
    ) {
        // Применяем paddingValues к элементам внутри карточки
        Modifier.padding(paddingValues) // Применяем paddingValues к содержимому карточки
        when (item) {
            is Post -> HeadCard(item)
            is Event -> InfoEvent(item.type, item.datetime)
        }
        item.attachment?.let { ContentCard(content = item.content, it) }
        StatPost(viewModel, item.id, item !is Post)
    }
}


