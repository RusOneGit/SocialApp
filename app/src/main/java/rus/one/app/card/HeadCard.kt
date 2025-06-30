package rus.one.app.card

import PostMenuButton
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rus.one.app.R
import rus.one.app.common.Item
import rus.one.app.components.AuthorAvatar
import rus.one.app.util.formatIsoDate
import rus.one.app.viewmodel.BaseFeedViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun <T : Item> HeadCard(viewModel: BaseFeedViewModel<T>, item: T, currentUserId: Long) {
    val expanded = remember { mutableStateOf(false) }

    val isMyPost = item.authorId == currentUserId

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AuthorAvatar(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            item.authorAvatar,
            item.author
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            PostAuthorName(item.author)
            PostData(modifier = Modifier.padding(4.dp), item.published, Color(0xff1D1B20))
        }

        Box(
            modifier = Modifier.wrapContentSize(Alignment.TopEnd)
        ) {
            if (isMyPost) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { expanded.value = true },
                    painter = painterResource(R.drawable.ic_more_vert),
                    tint = Color(0xFF79747E),
                    contentDescription = "Меню"
                )

                PostMenuButton(
                    viewModel = viewModel,
                    expanded = expanded.value,
                    onDismiss = { expanded.value = false },
                    item = item,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostData(modifier: Modifier, data: String, color: Color) {
    Text(
        modifier = modifier,
        text = formatIsoDate(data),
        color = color ,
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        lineHeight = 20.sp
    )

}

@Composable
fun PostAuthorName(author: String) {
    Text(
        modifier = Modifier.padding(4.dp),
        text = author,
        color = Color(0xff1D1B20),
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp

    )

}


