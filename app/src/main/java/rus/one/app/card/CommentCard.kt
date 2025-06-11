package rus.one.app.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CommentCard() {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize()) {
        // Кнопка по центру экрана
        Button(
            onClick = {
                scope.launch { sheetState.show()
                }
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Показать лист")
        }

        ModalBottomSheet(
            onDismissRequest = { },
            sheetState = sheetState,
            dragHandle = {
                Box(
                    Modifier
                        .padding(vertical = 8.dp)
                        .size(width = 40.dp, height = 4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            shape = RoundedCornerShape(2.dp)
                        )
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = 600.dp)
                    .padding(16.dp)
            ) {
                Text("Заголовок листа", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text("Здесь можно разместить комментарии или любой другой контент.")
                Spacer(Modifier.height(16.dp))
                Button(onClick = { scope.launch { sheetState.expand() } }) {
                    Text("Закрыть")
                }
            }
        }
    }
}
