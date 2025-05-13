package rus.one.app.components.bar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import rus.one.app.components.button.BackButton
import rus.one.app.components.button.TopButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit,
    onClick: (() -> Unit)? = null,
    painterId: Int? = null,
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
        },
        actions = {
            onClick?.let {
                val painter =
                    painterId?.let { id -> painterResource(id) } // Получаем painter только если id не null
                TopButton(
                    modifier = Modifier.size(32.dp),
                    color = Color(0xFF000000),
                    onClick = it,
                    painter = painter // Передаем painter, который может быть null
                )
            }
        }
    )
}