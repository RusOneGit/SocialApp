package rus.one.app.posts.ui.activity

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import rus.one.app.R
import rus.one.app.common.Item
import rus.one.app.components.bar.BottomBarNewPost
import rus.one.app.components.bar.TopBar
import rus.one.app.events.Event
import rus.one.app.events.EventType
import rus.one.app.posts.Attachment
import rus.one.app.posts.Attachments
import rus.one.app.posts.Coords
import rus.one.app.posts.Post
import rus.one.app.profile.UserViewModel
import rus.one.app.util.convertMillisToIsoDate
import rus.one.app.util.formatIsoDate
import rus.one.app.viewmodel.BaseFeedViewModel
import rus.one.app.viewmodel.EventViewModel
import rus.one.app.viewmodel.PostViewModel
import java.io.File
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
fun <T : Item> NewPost(
    viewModel: BaseFeedViewModel<T>,
    userViewModel: UserViewModel,
    onClose: () -> Unit,
) {
    val message = remember { mutableStateOf("") }
    val context = LocalContext.current
    val selectedItemPosition = remember { mutableStateOf(0) }
    var eventType by remember { mutableStateOf(EventType.OFFLINE) }
    var showEventTypeDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember {
        mutableStateOf(
            datePickerState.selectedDateMillis?.let { convertMillisToIsoDate(it) }
                ?: Clock.System.now().toString()
        )
    }


    val currentUserId by userViewModel.userId.collectAsState()
    val users by userViewModel.users.collectAsState(initial = emptyList())
    val user = users.find { it.id.toLong() == currentUserId }

    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var attachmentUri by remember { mutableStateOf<Uri?>(null) }
    var selectedUsers by remember { mutableStateOf<List<String>>(emptyList()) }
    var coords by remember { mutableStateOf<Coords?>(null) }
    val pickImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val file = copyUriToFile(context, it)
            photoUri = file?.toUri()
        }
    }

    val pickAttachmentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { attachmentUri = it } }


    val isEvent = viewModel is EventViewModel

    fun savePostOrEvent() {
        if (message.value.isBlank()) {
            Toast.makeText(context, "Введите текст", Toast.LENGTH_SHORT).show()
            return
        }

        if (isEvent) {
            val event = Event(
                id = 0,
                authorId = currentUserId,
                author = user?.name.orEmpty(),
                authorJob = null,
                authorAvatar = user?.avatar,
                content = message.value,
                published = Clock.System.now().toString(),
                coords = coords,
                link = null,
                likeOwnerIds = emptyList(),
                likedByMe = false,
                attachment = photoUri?.toString()?.let { Attachment(it, "IMAGE") },
                users = null,
                participatedByMe = false,
                type = eventType,
                datetime = selectedDate,
                speakerIds = emptyList(),
                participantsIds = emptyList()
            )
            (viewModel as EventViewModel).add(event)
        } else {
            val postViewModel = viewModel as PostViewModel
            val post = Post(
                id = 0,
                authorId = currentUserId,
                author = user?.name.orEmpty(),
                authorJob = "Разработчик",
                authorAvatar = user?.avatar,
                content = message.value,
                published = Clock.System.now().toString(),
                coords = coords,
                link = null,
                mentionIds = null,
                mentionedMe = false,
                likeOwnerIds = emptyList(),
                likedByMe = false,
                attachment = photoUri?.toString()?.let { Attachment(it, "IMAGE") },
                users = null
            )
            postViewModel.add(post)
        }
        onClose()
    }


    fun openUserSelection() {
        // TODO: реализуйте выбор пользователей
    }

    fun openLocationPicker() {
        // TODO: реализуйте выбор координат
    }

    BackHandler {
        onClose()
    }

    Dialog(onDismissRequest = onClose) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Scaffold(topBar = {
                TopBar(
                    title = stringResource(R.string.newPost),
                    onBackClick = { onClose() },
                    onClick = {
                        savePostOrEvent()
                        onClose()
                    },
                    painterId = R.drawable.ic_done
                )



                Log.d("кнопка", "message.value: ${message.value}")
            }, bottomBar = {

                BottomBarNewPost(
                    selectedItemPosition = selectedItemPosition, items = listOf(
                        Attachments.Photo,
                        Attachments.Attach,
                        Attachments.UsersNoTitle,
                        Attachments.Location
                    ), onItemClick = { attachment ->
                        when (attachment) {
                            Attachments.Photo -> pickImageLauncher.launch("image/*")
                            Attachments.Attach -> pickAttachmentLauncher.launch("*/*")
                            Attachments.UsersNoTitle -> openUserSelection()
                            Attachments.Location -> openLocationPicker()
                        }
                    })

                if (isEvent) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, top = 8.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {

                       Column {  Text(
                           text = formatIsoDate(selectedDate),
                           modifier = Modifier
                               .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                               .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
                               .padding(horizontal = 8.dp, vertical = 4.dp),
                           fontSize = 12.sp,
                           fontWeight = FontWeight.Medium,
                           color = Color.DarkGray,
                           textAlign = TextAlign.Center
                       )

                           Text(
                               text = eventType.name,
                               modifier = Modifier
                                   .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                   .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
                                   .padding(horizontal = 8.dp, vertical = 4.dp),
                               fontSize = 12.sp,
                               fontWeight = FontWeight.Medium,
                               color = Color.DarkGray,
                               textAlign = TextAlign.Center
                           ) }





                    }
                }


            }, floatingActionButton = {
                if (isEvent) {
                    FloatingActionButton(onClick = {
                        showDatePicker = !showDatePicker
                    }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Выбрать дату"
                        )
                    }
                }

            }


            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    if(isEvent){
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            SegmentedButton(
                                selected = eventType == EventType.OFFLINE,
                                onClick = { eventType = EventType.OFFLINE },shape = MaterialTheme.shapes.small) {
                                Text("Оффлайн")
                            }

                            SegmentedButton(
                                selected = eventType == EventType.ONLINE,
                                onClick = { eventType = EventType.ONLINE },shape = MaterialTheme.shapes.small) {
                                Text("Онлайн")
                            }
                        }

                    }




                    TextField(
                        value = message.value,
                        onValueChange = { text -> message.value = text },
                        maxLines = 20,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedTextColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            lineHeight = 20.sp,
                            fontSize = 14.sp,
                            letterSpacing = 0.25.sp,
                            color = Color.Black,
                            fontWeight = FontWeight(400)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    photoUri?.let { uri ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "Выбранное фото",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            IconButton(
                                onClick = { photoUri = null },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Удалить фото",
                                    tint = Color.White
                                )
                            }
                        }


                    }
                }



                Text(text = selectedDate)

                if (showDatePicker) {
                    Popup(
                        onDismissRequest = { showDatePicker = false },
                        alignment = Alignment.TopStart
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .fillMaxWidth()
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surface)
                        ) {
                            DatePicker(
                                state = datePickerState, showModeToggle = false
                            )


                            Column {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    TextButton(onClick = { showDatePicker = false }) {
                                        Text("Отмена")
                                    }
                                    TextButton(onClick = {
                                        // Подтверждаем выбор даты
                                        val selectedMillis = datePickerState.selectedDateMillis
                                        if (selectedMillis != null) {

                                            selectedDate = convertMillisToIsoDate(selectedMillis)



                                        }

                                        showDatePicker = false
                                    }) {
                                        Text("ОК")
                                    }

                                }


                            }
                        }
                    }


                }


            }
        }

    }


}
fun copyUriToFile(context: Context, uri: Uri): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.cacheDir, "post_photo_${System.currentTimeMillis()}.jpg")
        val outputStream = file.outputStream()
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}



