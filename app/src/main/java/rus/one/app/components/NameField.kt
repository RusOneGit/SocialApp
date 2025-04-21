package rus.one.app.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun NameField(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, label: String) {
    TextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        label = { Text(label) },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    )
}