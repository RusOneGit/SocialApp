package rus.one.app.profile

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import rus.one.app.state.FeedState
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _registrationState = MutableStateFlow<RegistrationResult?>(null)
    val registrationState: StateFlow<RegistrationResult?> = _registrationState.asStateFlow()


    fun registration(login: String, password: String, name: String, context: Context) {
        viewModelScope.launch {
            _registrationState.value = RegistrationResult.Loading
            try {
                userRepository.registration(login, password, name, context)
                _registrationState.value = RegistrationResult.Success
            } catch (e: Exception) {
                _registrationState.value = RegistrationResult.Error(e.message ?: "Unknown error")
            }
        }
    }


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

    sealed class RegistrationResult {
        object Success : RegistrationResult()
        data class Error(val message: String) : RegistrationResult()
        object Loading : RegistrationResult()
    }

}




