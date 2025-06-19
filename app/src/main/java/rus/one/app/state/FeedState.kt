package rus.one.app.state

import rus.one.app.common.Item

data class FeedState<T>(
    val item: List<T> = emptyList(),
    val error: Boolean = false,
    val loading: Boolean = false,
    val empty: Boolean = false,
    val isRefreshing: Boolean = false

)
