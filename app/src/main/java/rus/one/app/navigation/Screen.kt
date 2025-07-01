package rus.one.app.navigation

sealed class Screen(
    val route: String
)
{
    object PostsScreen: Screen(ROUTE_POSTS)
    object EventsScreen: Screen(ROUTE_EVENTS)
    object UsersScreen: Screen(ROUTE_USERS)
    object NewPostScreen : Screen("new_post")

    private companion object{
        const val ROUTE_POSTS = "item"
        const val ROUTE_EVENTS = "events"
        const val ROUTE_USERS = "users"
        const val ROUTE_NEW = "new_post"

    }
}