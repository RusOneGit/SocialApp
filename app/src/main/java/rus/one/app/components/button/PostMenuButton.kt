import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import rus.one.app.common.Item
import rus.one.app.viewmodel.BaseFeedViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun <T : Item> PostMenuButton(viewModel: BaseFeedViewModel<T>, expanded: Boolean, onDismiss: () -> Unit, item: T,  modifier: Modifier = Modifier) {


    // Выпадающее меню
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismiss // Закрытие меню при нажатии вне его
    ) {
        DropdownMenuItem(
            text = { Text("Удалить") }, // Указываем текст для первого элемента
            onClick = {
                onDismiss() // Закрываем меню
              viewModel.delete(item)
            }
        )

        DropdownMenuItem(
            text = { Text("Изменить") }, // Указываем текст для второго элемента
            onClick = {
                onDismiss() // Закрываем меню
               viewModel.edit(item)
            }
        )
    }
}