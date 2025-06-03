package rus.one.app.navigation

import rus.one.app.R

sealed class NavigationItem(
    val screen: Screen,
    val titleResID: Int,
    val icon: Int,
) {
    object Posts : NavigationItem(
        screen = Screen.PostsScreen,
        titleResID = R.string.posts,
        icon = R.drawable.ic_posts
    )

    object Events : NavigationItem(
        screen = Screen.EventsScreen,
        titleResID = R.string.events,
        icon = R.drawable.ic_events
    )

    object Users : NavigationItem(
        screen = Screen.UsersScreen,
        titleResID = R.string.users,
        icon = R.drawable.ic_people
    )


}