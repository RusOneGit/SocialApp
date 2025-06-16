package rus.one.app.state

import rus.one.app.common.Item

data class FeedState(
    val item: List<Item> = emptyList(),
    val error: Boolean = false,
    val loading: Boolean = false,
    val empty: Boolean = false,
    val isRefreshing: Boolean = false

)
