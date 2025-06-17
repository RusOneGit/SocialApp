@file:OptIn(ExperimentalMaterial3Api::class)

package rus.one.app.posts


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import rus.one.app.card.CardItem
import rus.one.app.common.Item
import rus.one.app.viewmodel.ViewModelCard

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    viewModel: ViewModelCard,
    paddingValues: PaddingValues,
    onClick: (Item) -> Unit,
) {
    val feedState by viewModel.feedState.collectAsState()
    val listState = remember { LazyListState() } // Создаем LazyListState

    // Создаем состояние жеста pull-to-refresh
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = feedState.isRefreshing,
        onRefresh = { viewModel.load() },
        state = pullRefreshState,
        modifier = Modifier.fillMaxSize()
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
                    state = listState, // Применяем LazyListState
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = paddingValues
                ) {
                    items(feedState.item, key = { it.id }) { post ->
                        CardItem(
                            viewModel = viewModel,
                            item = post,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
                }

                // Прокрутка до последнего поста после обновления
                LaunchedEffect(feedState.item) {
                    if (feedState.item.isNotEmpty()) {
                        listState.scrollToItem(0) // Прокручиваем до самого верхнего элемента
                    }
                }
            }
        }
    }
}

