package rus.one.app.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier.padding(8.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFEF7FF)),
        navigationIcon = {
            BackButton(onBackClick = onBackClick)
        }
    )
}