import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import rus.one.app.common.Item
import rus.one.app.posts.Post
import rus.one.app.viewmodel.BaseFeedViewModel
import rus.one.app.viewmodel.PostViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemMenu(viewModel: BaseFeedViewModel<Item>, expanded: Boolean, onDismiss: () -> Unit, item: Item) {


    // Выпадающее меню
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss // Закрытие меню при нажатии вне его
    ) {
        DropdownMenuItem(
            text = { Text("Удалить") }, // Указываем текст для первого элемента
            onClick = {
                onDismiss() // Закрываем меню
//                viewModel.delete(post) // Ваше действие
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