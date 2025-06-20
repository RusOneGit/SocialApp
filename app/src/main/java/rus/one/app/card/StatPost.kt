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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import rus.one.app.R
import rus.one.app.common.Item
import rus.one.app.components.button.LikeButton
import rus.one.app.components.button.TopButton
import rus.one.app.viewmodel.BaseFeedViewModel

import rus.one.app.viewmodel.PostViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun <T: Item>StatPost(viewModel: BaseFeedViewModel<T>, itemID: Long, showMentioned: Boolean = false) {


    Row {
        Log.d("Перерисовка", "Статс")

        LikeButton(viewModel, itemID)


        Spacer(modifier = Modifier.padding(8.dp))

        TopButton(
            modifier = Modifier
                .padding(8.dp)
                .size(18.dp),
            Color(0xFF6750A4),
            onClick = {},
            painter = painterResource(R.drawable.ic_share)
        )


        if (showMentioned) {
            Spacer(modifier = Modifier.padding(8.dp))

        }


    }
}
