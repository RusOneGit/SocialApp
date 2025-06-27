@file:OptIn(ExperimentalMaterial3Api::class)

package rus.one.app.posts

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rus.one.app.card.CardItem
import rus.one.app.common.Item
import rus.one.app.viewmodel.BaseFeedViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Item> PostScreen(
    viewModel: BaseFeedViewModel<T>,
    paddingValues: PaddingValues,
    onClick: (T) -> Unit,
) {
    val feedState by viewModel.feedState.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()
    val listState = rememberLazyListState()

    PullToRefreshBox(
        isRefreshing = feedState.isRefreshing,
        onRefresh = { viewModel.load() },
        modifier = Modifier.fillMaxSize(),
        state = pullRefreshState,
        indicator = {
            Indicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 80.dp),
                isRefreshing = true,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = pullRefreshState
            )
        },
    ) {
        when {
            feedState.error && feedState.item.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Ошибка загрузки")
                }
            }

            feedState.item.isEmpty() && feedState.isRefreshing -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            feedState.item.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Посты отсутствуют")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = paddingValues
                ) {
                    items(feedState.item, key = { it.id }) { item ->

                        CardItem(
                            viewModel = viewModel,
                            item = item,
                            paddingValues = paddingValues,
                            onClick =  { onClick(item)}
                        )

                    }
                }
            }
        }
    }

    // Прокрутка до последнего поста после обновления
    LaunchedEffect(feedState.item) {
        if (feedState.item.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }
}
