package rus.one.app.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rus.one.app.state.PassWordState
import rus.one.app.R
import rus.one.app.components.button.CameraButton
import rus.one.app.components.button.CompareButton
import rus.one.app.components.field.LoginField
import rus.one.app.components.field.NameField
import rus.one.app.components.field.PasswordInputField
import rus.one.app.components.bar.TopBar


class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Register()
        }
    }
}


@Preview
@Composable
fun Register() {
    var username = remember { mutableStateOf(TextFieldValue()) }
    var name = remember { mutableStateOf(TextFieldValue()) }
    var passwordState = remember { mutableStateOf(PassWordState()) }
    var passwordError = remember { mutableStateOf("") }
    var passwordVisible = remember { mutableStateOf(false) }

    val context = LocalContext.current

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
                value = username.value,
                onValueChange = { newValue -> username.value = newValue },
                label = stringResource(R.string.login)
            )
            //поле ввода имени
            NameField(
                value = name.value,
                onValueChange = { newValue -> name.value },
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
                onClick = {},
                passwordState.value.password,
                passwordState.value.confirmPassword
            )

        }
    }
}

