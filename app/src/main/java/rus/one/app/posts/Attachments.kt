package rus.one.app.posts

import rus.one.app.R
import rus.one.app.navigation.NavigationItem

sealed class Attachments( val icon: Int) {
    object Photo: Attachments(
        icon = R.drawable.ic_camera
    )

    object Attachment: Attachments(
        icon = R.drawable.ic_attach
    )

    object UsersNoTitle : Attachments(
        icon = R.drawable.ic_people
    )

    object Location: Attachments(
        icon = R.drawable.ic_location
    )




}


