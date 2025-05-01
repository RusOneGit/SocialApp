package rus.one.app.components.bar

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import rus.one.app.NavigationItem

@Composable
fun BottomBar(selectedItemPosition: MutableState<Int>) {

    NavigationBar {

        val items = listOf(
            NavigationItem.Posts,
            NavigationItem.Events,
            NavigationItem.Users
        )

        items.forEachIndexed { index, item ->

            NavigationBarItem(
                selected = selectedItemPosition.value == index,
                onClick = {
                    selectedItemPosition.value = index
                    Log.d("SelectedItem", "Selected item position: ${selectedItemPosition.value}")
                },
                icon = {
                    Icon(ImageVector.vectorResource(item.icon), contentDescription = null)
                },
                label = { Text(stringResource(item.titleResID)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF1D1B20),
                    selectedTextColor = Color(0xFF1D1B20),
                    indicatorColor = Color(0xFFE8DEF8),
                    unselectedIconColor = Color(0xFF49454F),
                    unselectedTextColor = Color(0xFF49454F)
                )


            )


        }
    }
}