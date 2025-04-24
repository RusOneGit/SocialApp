package rus.one.app.components.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import rus.one.app.R

@Composable
fun ButtonLogin(
    onClick: () -> Unit,
    password: TextFieldValue,
    passwordError: MutableState<String>
) {
    Button(
        onClick = {
            if (password.text.isEmpty()) {
                passwordError.value = "Пароль не может быть пустой" // Установить ошибку, если пароль пустой
            } else {
                passwordError.value = "" // Сбросить ошибку
                // Логика авторизации
            }
        }, shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(
            containerColor = Color(0x1D1B201F), contentColor = Color(0x1D1B201F)
        ), modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(stringResource(R.string.login))
    }

}