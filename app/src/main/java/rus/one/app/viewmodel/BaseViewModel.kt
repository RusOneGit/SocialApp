package rus.one.app.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import rus.one.app.common.Item
import rus.one.app.state.FeedState

abstract class BaseFeedViewModel<T> : ViewModel() {
    abstract val feedState: StateFlow<FeedState<T>>
    abstract fun load()
    abstract fun refresh()
    abstract fun like(itemID: Long)
    abstract fun add(item: Item)
    abstract fun edit(item: Item)
}