package rus.one.app.profile

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import rus.one.app.state.FeedState
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    val feedState = MutableStateFlow(FeedState<User>(loading = true))


    @RequiresApi(Build.VERSION_CODES.O)
    val users: StateFlow<List<User>> = userRepository.users.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    ).also {
        viewModelScope.launch {
            it.collect { list ->
                Log.d("ViewModelCard", "События  обновились: ${list} штук")
            }
        }
    }

    init {
        getUsers()

    }

    fun getUsers() {
        viewModelScope.launch {
            userRepository.fetchUsers()
        }

    }
}
