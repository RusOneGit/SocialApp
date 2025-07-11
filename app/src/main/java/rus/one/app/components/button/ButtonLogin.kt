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
        onClick = onClick ,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (password.text.isEmpty()) Color(0x1D1B201F) else Color(0xFF6750A4),
            contentColor = if (password.text.isEmpty()) Color(0x1D1B201F) else Color.White
        ), modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(stringResource(R.string.login))
    }

}