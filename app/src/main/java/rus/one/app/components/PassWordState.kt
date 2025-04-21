package rus.one.app.components

import androidx.compose.ui.text.input.TextFieldValue

data class PassWordState(
    var password: TextFieldValue = TextFieldValue(""),
    var confirmPassword: TextFieldValue = TextFieldValue("")
)

