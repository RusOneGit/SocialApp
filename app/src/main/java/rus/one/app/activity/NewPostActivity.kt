package rus.one.app.posts.ui.activity

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import dagger.hilt.android.AndroidEntryPoint
import rus.one.app.R
import rus.one.app.components.bar.BottomBarNewPost
import rus.one.app.components.bar.TopBar
import rus.one.app.posts.Attachment
import rus.one.app.posts.Attachments
import rus.one.app.posts.Coords
import rus.one.app.posts.Post
import rus.one.app.viewmodel.PostViewModel
import java.io.File

@AndroidEntryPoint
class NewPostActivity : ComponentActivity() {

    private val viewModel: PostViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewPost(viewModel)
        }
    }
}

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun NewPost(viewModel: PostViewModel) {
        val message = remember { mutableStateOf("") }
        val context = LocalContext.current
        val selectedItemPosition = remember { mutableStateOf(0) }


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

        fun openUserSelection() {
            // TODO: реализуйте выбор пользователей
        }

        fun openLocationPicker() {
            // TODO: реализуйте выбор координат
        }



        Scaffold(topBar = {
            TopBar(
                title = stringResource(R.string.newPost),
                onBackClick = { (context as? NewPostActivity)?.finish() },
                onClick = {

                    val post = Post(
                        id = 2, // 0, чтобы база сгенерировала id
                        authorId = 22,
                        author = "Никитос",
                        authorJob = "Разработчик",
                        authorAvatar = "https://example.com/avatar.png",
                        content = message.value,
                        published = "2025-06-12T12:49:35.637Z",
                        coords = null,
                        link = "fjf",
                        mentionIds = null,
                        mentionedMe = false,
                        likeOwnerIds = null,
                        likedByMe = false,
                        attachment = Attachment(photoUri.toString(), "IMAGE" ),
                        users = null
                    )
                    viewModel.add(post)


                    (context as? NewPostActivity)?.finish()
                },
                painterId = R.drawable.ic_done
            )

            Log.d("кнопка", "message.value: ${message.value}")
        }, bottomBar = {

            BottomBarNewPost(
                selectedItemPosition = selectedItemPosition, items = listOf(
                    Attachments.Photo,
                    Attachments.Attachment,
                    Attachments.UsersNoTitle,
                    Attachments.Location
                ),
                onItemClick = { attachment ->
                    when (attachment) {
                        Attachments.Photo -> pickImageLauncher.launch("image/*")
                        Attachments.Attachment -> pickAttachmentLauncher.launch("*/*")
                        Attachments.UsersNoTitle -> openUserSelection()
                        Attachments.Location -> openLocationPicker()
                    }
                }
            )

        }


        ) { paddingValues ->

            TextField(
                value = message.value,
                onValueChange = { text -> message.value = text },
                maxLines = 20,
                modifier = Modifier
                    .fillMaxWidth(), // занимает всё свободное пространство сверху
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
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Удалить фото",
                            tint = Color.White
                        )
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
