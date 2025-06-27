package rus.one.app.profile

import android.content.ContentResolver
import android.net.Uri
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {


    private val _registrationState = MutableStateFlow<RegistrationResult?>(null)
    val registrationState: StateFlow<RegistrationResult?> = _registrationState.asStateFlow()

    private val _authenticationState = MutableStateFlow<AuthenticationResult?>(null)
    val authenticationState: StateFlow<AuthenticationResult?> = _authenticationState.asStateFlow()


    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    private val _userId = MutableStateFlow<Long?>(null)
    val userId: StateFlow<Long?> = _userId


    fun registration(
        login: String,
        password: String,
        name: String,
        avatar: Uri,
        contentResolver: ContentResolver,
    ) {
        viewModelScope.launch {
            _registrationState.value = RegistrationResult.Loading
            try {
                userRepository.registration(login, password, name, avatar, contentResolver)
                _registrationState.value = RegistrationResult.Success
            } catch (e: Exception) {
                _registrationState.value = RegistrationResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    init {
        userRepository.tokenFlow
            .onEach { newToken ->
                _token.value = if (newToken.isNotBlank()) newToken else null
            }
            .launchIn(viewModelScope)

        // Подписываемся на изменения userId из DataStore
        userRepository.userIdFlow
            .onEach { newUserId ->
                _userId.value = if (newUserId != 0L) newUserId else null
            }
            .launchIn(viewModelScope)

        getUsers()
    }

    fun authorization(login: String, password: String) {
        viewModelScope.launch {
            _authenticationState.value = AuthenticationResult.Loading
            try {
                userRepository.authentication(login, password)
                _authenticationState.value = AuthenticationResult.Success
            } catch (e: Exception) {
                _authenticationState.value =
                    AuthenticationResult.Error(e.message ?: "Authentication error ")
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


    sealed class AuthenticationResult {
        object Success : AuthenticationResult()
        data class Error(val message: String) : AuthenticationResult()
        object Loading : AuthenticationResult()
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.clearAuthData()
        }
    }
}




