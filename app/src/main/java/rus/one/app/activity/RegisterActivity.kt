package rus.one.app.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import rus.one.app.R
import rus.one.app.components.bar.TopBar
import rus.one.app.components.button.CameraButton
import rus.one.app.components.button.CompareButton
import rus.one.app.components.field.LoginField
import rus.one.app.components.field.NameField
import rus.one.app.components.field.PasswordInputField
import rus.one.app.profile.UserViewModel
import rus.one.app.profile.UserViewModel.RegistrationResult
import rus.one.app.state.PassWordState

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Register(userViewModel)
        }
    }
}


@Composable
fun Register(userViewModel: UserViewModel) {
    var login = remember { mutableStateOf(TextFieldValue()) }
    var name = remember { mutableStateOf(TextFieldValue()) }
    var passwordState = remember { mutableStateOf(PassWordState()) }
    var passwordError = remember { mutableStateOf("") }
    var passwordVisible = remember { mutableStateOf(false) }
    val registrationState by userViewModel.registrationState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(registrationState) {
        when (registrationState) {
            is RegistrationResult.Loading -> {
                // Можно показать ProgressBar или что-то
            }
            is RegistrationResult.Success -> {
                Toast.makeText(context, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                // Закрываем экран регистрации
                (context as? RegisterActivity)?.finish()
            }
            is RegistrationResult.Error -> {
                val errorMessage = (registrationState as RegistrationResult.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> { /* ничего */ }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.registration),
                onBackClick = { (context as? RegisterActivity)?.finish() })
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CameraButton(R.drawable.ic_camera, stringResource(R.string.camera), onClick = {})

            //поле ввода логина
            LoginField(
                value = login.value,
                onValueChange = { newValue -> login.value = newValue },
                label = stringResource(R.string.login)
            )
            //поле ввода имени
            NameField(
                value = name.value,
                onValueChange = { newValue -> name.value = newValue },
                label = (stringResource(R.string.name))
            )


            (
                    //поле ввода пароля
                    PasswordInputField(
                        value = passwordState.value.password, onValueChange = {
                            passwordState.value = passwordState.value.copy(password = it)
                        }, labelResId = R.string.password, passwordVisible = passwordVisible
                    ))


            //поле повторного ввода пароля
            PasswordInputField(
                value = passwordState.value.confirmPassword, onValueChange = {
                    passwordState.value = passwordState.value.copy(confirmPassword = it)
                }, labelResId = R.string.confirm_password, passwordVisible = passwordVisible
            )


            //кнопка входа

            CompareButton(
                onClick = {
                    userViewModel.registration(
                        login = login.value.text,
                        password = passwordState.value.password.text,
                        name = name.value.text,
                        context
                    )
                },
                passwordState.value.password,
                passwordState.value.confirmPassword
            )

        }
    }
}

