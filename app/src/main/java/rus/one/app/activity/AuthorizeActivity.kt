package rus.one.app.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rus.one.app.R
import rus.one.app.components.bar.TopBar
import rus.one.app.components.button.ButtonLogin
import rus.one.app.components.field.LoginField
import rus.one.app.components.field.PasswordInputField
import rus.one.app.state.PassWordState


class AuthorizeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Authorize()

        }
    }
}

@Preview
@Composable
fun Authorize() {
    val context = LocalContext.current
    var username = remember { mutableStateOf(TextFieldValue()) }
    var passwordError = remember { mutableStateOf("") }
    var passwordState = remember { mutableStateOf(PassWordState()) }
    var passwordVisible = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.login),
                onBackClick = { (context as? AuthorizeActivity)?.finish() })
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            //Поля для ввода логина
            LoginField(
                username.value, onValueChange = { newValue -> username.value = newValue }, label =
                    stringResource(R.string.login)
            )


            // Поле для ввода пароля
            PasswordInputField(
                value = passwordState.value.password,
                onValueChange = {
                        newPassword ->
                    passwordState.value = passwordState.value.copy(password = newPassword)
                },
                labelResId = R.string.password,
                passwordVisible = passwordVisible
            )

            // Кнопка "Войти"
            ButtonLogin(onClick = { // Проверка на пустой пароль
                if (passwordState.value.password.text.isEmpty()) {
                    passwordError.value = context.getString(R.string.password_empty)
                } else {
                    // Логика авторизации
                    passwordError.value = "" // Сбросить ошибку при успешной авторизации
                }
            }, password = passwordState.value.password, passwordError)


            // Показываем ошибку, если есть
            if (passwordError.value.isNotEmpty()) {
                Text(
                    text = passwordError.value,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )}


            Spacer(modifier = Modifier.height(16.dp))

            // Ссылка "Забыли пароль?"

            TextButton(onClick = {
                val intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)
            }) {
                Text(
                    "Don't have an account? Register",
                    color = Color(0xFF6750A4)
                ) // Если у пользователя нет аккаунта, открываем активити с регистрацией


            }
        }
    }

}
