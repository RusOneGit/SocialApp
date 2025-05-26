package rus.one.app.card

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rus.one.app.activity.PostDetailActivity
import rus.one.app.activity.RegisterActivity
import rus.one.app.common.Item
import rus.one.app.events.Event
import rus.one.app.events.InfoEvent
import rus.one.app.posts.Post
import rus.one.app.posts.post
import rus.one.app.viewmodel.ViewModelCard


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardItem(viewModel: ViewModelCard, item: Item) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = { val intent = Intent(context, PostDetailActivity::class.java)
            context.startActivity(intent)},
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF7FF)),
        border = BorderStroke(1.dp, color = Color(0xFFCAC4D0))
    ) {
        HeadCard( item as Post
        )
        if (item is Event) {
            InfoEvent(
                item.eventType, item.eventDate
            )
        }
        ContentCard(content = item.content)
        StatPost(viewModel, item !is Post)
    }
}


