package rus.one.app.navigation

sealed class Screen(
    val route: String
)
{
    object PostsScreen: Screen(ROUTE_POSTS)
    object EventsScreen: Screen(ROUTE_EVENTS)
    object UsersScreen: Screen(ROUTE_USERS)
    object NewPostScreen : Screen(ROUTE_NEW_POST)
    object NewEventScreen : Screen(ROUTE_NEW_EVENT)


    private companion object{
        const val ROUTE_POSTS = "posts"
        const val ROUTE_EVENTS = "events"
        const val ROUTE_USERS = "users"
        const val ROUTE_NEW_POST = "new_post"
        const val ROUTE_NEW_EVENT = "new_event"

    }
}