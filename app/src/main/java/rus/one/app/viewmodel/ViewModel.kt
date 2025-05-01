package rus.one.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import rus.one.app.common.Likeable
import kotlin.math.max
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelCard @Inject constructor(): ViewModel() {

    private val _itemCard = MutableLiveData<Likeable>()
    val itemCard: LiveData<Likeable> = _itemCard
    private val _isLiked = MutableStateFlow(false)
    val isLiked: StateFlow<Boolean> = _isLiked

    private val _likesCount = MutableStateFlow(0)
    val likesCount: StateFlow<Int> = _likesCount

    fun toggleLike() {
        val currentLiked = _isLiked.value
        _isLiked.value = !currentLiked
        _likesCount.value =
            if (currentLiked) max(_likesCount.value - 1, 0) else _likesCount.value + 1
    }
}