package rus.one.app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import rus.one.app.R

@Composable
fun PasswordInputField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    labelResId: Int,
    passwordVisible: MutableState<Boolean>
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(labelResId)) },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None
        else PasswordVisualTransformation(),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        trailingIcon = {
            Icon(
                painterResource(id = if (passwordVisible.value) R.drawable.ic_view else R.drawable.ic_no_visibility),
                contentDescription = if (passwordVisible.value) "Hide password" else "Show password",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        passwordVisible.value = !passwordVisible.value
                    }
            )
        }
    )
}
