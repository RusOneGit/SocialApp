package rus.one.app.components.bar

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import rus.one.app.NavigationItem

@Composable
fun BottomBarMain(selectedItemPosition: MutableState<Int>, items: List<NavigationItem>) {

    NavigationBar {

        items.forEachIndexed { index, item ->

            NavigationBarItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                selected = selectedItemPosition.value == index,
                onClick = {
                    selectedItemPosition.value = index
                    Log.d("SelectedItem", "Selected item position: ${selectedItemPosition.value}")
                },
                icon = {
                    Icon(ImageVector.vectorResource(item.icon), contentDescription = null)
                }, label = { item.titleResID?.let { Text(stringResource(it)) } },
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

@Composable
fun BottomBarNewPost(selectedItemPosition: MutableState<Int>, items: List<NavigationItem>) {
    NavigationBar {
        Row(
            modifier = Modifier.fillMaxWidth(0.5f),
            horizontalArrangement = Arrangement.Start // Смещение влево
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    modifier = Modifier.weight(1f), // Каждый элемент занимает равную ширину
                    selected = selectedItemPosition.value == index,
                    onClick = {
                        selectedItemPosition.value = index
                    },
                    icon = {
                        Icon(ImageVector.vectorResource(item.icon), contentDescription = null)
                    },
                    label = { item.titleResID?.let { Text(stringResource(it)) } },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1D1B20),
                        selectedTextColor = Color(0xFF1D1B20),
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = Color(0xFF49454F),
                        unselectedTextColor = Color(0xFF49454F)
                    )
                )
            }
        }
    }
}

