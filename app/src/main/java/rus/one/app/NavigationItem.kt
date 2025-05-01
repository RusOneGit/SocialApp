package rus.one.app

sealed class NavigationItem(
    val titleResID: Int,
    val icon: Int,
) {
    object Posts : NavigationItem(
        titleResID = R.string.posts,
        icon = R.drawable.ic_posts
    )

    object Events : NavigationItem(
        titleResID = R.string.events,
        icon = R.drawable.ic_events
    )

    object Users : NavigationItem(
        titleResID = R.string.users,
        icon = R.drawable.ic_people
    )
}