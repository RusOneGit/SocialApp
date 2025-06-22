package rus.one.app.components.button

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun CompareButton(onClick: () -> Unit, password: TextFieldValue, confirmPassword: TextFieldValue) {
    var passwordError = remember { mutableStateOf("") }
    Button(
        onClick = {
            // Сравниваем пароли
            if (password != confirmPassword) {
                passwordError.value = "Пароли не совпадают"// Устанавливаем ошибку
            } else {

                passwordError.value = "" // Сбрасываем ошибку при успешной проверке
                onClick()
            }
        }, modifier = Modifier.padding(8.dp)
    ) {
        Text("Войти")
    }
    // Показываем ошибку, если есть
    if (passwordError.value.isNotEmpty()) {
        Text(
            text = "Пароли не совпадают",
            color = Color.Red,
            modifier = Modifier.padding(8.dp)
        )
    }
}