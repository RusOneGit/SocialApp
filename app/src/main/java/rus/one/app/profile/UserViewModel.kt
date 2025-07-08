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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {






    val token: StateFlow<String> = userRepository.tokenFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val userId: StateFlow<Long> = userRepository.userIdFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0L)


    val isAuthorized: StateFlow<Boolean> = combine(token, userId) { token, userId ->
        token.isNotBlank() && userId != 0L
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private val _registrationState = MutableStateFlow<RegistrationResult?>(null)
    val registrationState: StateFlow<RegistrationResult?> = _registrationState.asStateFlow()

    private val _authenticationState = MutableStateFlow<AuthenticationResult?>(null)
    val authenticationState: StateFlow<AuthenticationResult?> = _authenticationState.asStateFlow()


    private val _jobs = MutableStateFlow<List<Jobs>>(emptyList())
    val jobs: StateFlow<List<Jobs>> = _jobs.asStateFlow()
    private val _jobsLoading = MutableStateFlow(false)
    val jobsLoading: StateFlow<Boolean> = _jobsLoading.asStateFlow()

    private val _jobsError = MutableStateFlow<String?>(null)
    val jobsError: StateFlow<String?> = _jobsError.asStateFlow()

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
        viewModelScope.launch {
            userId.collect { id ->
                Log.d("UserViewModel", "Current userId = $id")
            }
        }
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
    val users = userRepository.users.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

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

    fun loadJobs(userId: Long) {
        viewModelScope.launch {
            _jobsLoading.value = true
            _jobsError.value = null
            try {
                val jobsList = userRepository.getJobs(userId)
                _jobs.value = jobsList
            } catch (e: Exception) {
                _jobsError.value = "Ошибка загрузки работ: ${e.message}"
            } finally {
                _jobsLoading.value = false
            }
        }
    }
}




