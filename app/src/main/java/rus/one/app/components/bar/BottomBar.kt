package rus.one.app.components.bar

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import rus.one.app.navigation.NavigationItem
import rus.one.app.navigation.NavigationState
import rus.one.app.posts.Attachments

@Composable
fun BottomBarMain(
    items: List<NavigationItem>, navigationState: NavigationState,
) {
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val currentRout = navBackStackEntry?.destination?.route



    NavigationBar(containerColor = Color(0xFFF3EDF7)) {

        items.forEachIndexed { index, item ->

            NavigationBarItem(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                selected = currentRout == item.screen.route,
                onClick = {
                    navigationState.navigateTo(item.screen.route)
                },
                icon = {
                    Icon(ImageVector.vectorResource(item.icon), contentDescription = null)
                }, label = {Text(stringResource(item.titleResID))
                },
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
fun BottomBarNewPost(selectedItemPosition: MutableState<Int>, items: List<Attachments>, onItemClick: (Attachments) -> Unit) {


    NavigationBar {

        Row(
            modifier = Modifier.padding(start =  16.dp).fillMaxWidth(0.5f),
            horizontalArrangement = Arrangement.Start // Смещение влево
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    modifier = Modifier.weight(1f), // Каждый элемент занимает равную ширину
                    selected = selectedItemPosition.value == index,
                    onClick = {
                        selectedItemPosition.value = index
                        onItemClick(item)


                    },
                    icon = {
                        Icon(ImageVector.vectorResource(item.icon), contentDescription = null)
                    },
                    label = {},
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

