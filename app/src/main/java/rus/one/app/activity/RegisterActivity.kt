package rus.one.app.activity

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import coil.compose.rememberAsyncImagePainter
import dagger.hilt.android.AndroidEntryPoint
import rus.one.app.R
import rus.one.app.components.bar.TopBar
import rus.one.app.components.button.CameraButton
import rus.one.app.components.button.CompareButton
import rus.one.app.components.field.LoginField
import rus.one.app.components.field.NameField
import rus.one.app.components.field.PasswordInputField
import rus.one.app.profile.UserViewModel
import rus.one.app.profile.UserViewModel.RegistrationResult
import rus.one.app.state.PassWordState

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Register(userViewModel)
        }
    }
}

@Composable
fun Register(userViewModel: UserViewModel) {
    var login = remember { mutableStateOf(TextFieldValue()) }
    var name = remember { mutableStateOf(TextFieldValue()) }
    var passwordState = remember { mutableStateOf(PassWordState()) }
    var passwordVisible = remember { mutableStateOf(false) }
    val registrationState by userViewModel.registrationState.collectAsState()

    val context = LocalContext.current

    // Состояние для URI аватарки
    var avatarUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher для выбора изображения из галереи
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { avatarUri = it }
    }

    // Launcher для запроса разрешения
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pickImageLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Разрешение не предоставлено", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkPermissionAndPickImage() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_GRANTED -> {
                pickImageLauncher.launch("image/*")
            }
            else -> {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    LaunchedEffect(registrationState) {
        when (registrationState) {
            is RegistrationResult.Loading -> { /* Можно показать индикатор загрузки */ }
            is RegistrationResult.Success -> {
                Toast.makeText(context, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                (context as? RegisterActivity)?.finish()
            }
            is RegistrationResult.Error -> {
                Toast.makeText(
                    context,
                    (registrationState as RegistrationResult.Error).message,
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> { }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.registration),
                onBackClick = { (context as? RegisterActivity)?.finish() }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (avatarUri == null) {
                CameraButton(
                    iconID = R.drawable.ic_camera,
                    contentDescription = stringResource(R.string.camera),
                    onClick = { checkPermissionAndPickImage() }
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(avatarUri),
                    contentDescription = "Выбранный аватар",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(160.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFC4C4C4))
                        .clickable { checkPermissionAndPickImage() },
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LoginField(
                value = login.value,
                onValueChange = { login.value = it },
                label = stringResource(R.string.login)
            )

            NameField(
                value = name.value,
                onValueChange = { name.value = it },
                label = stringResource(R.string.name)
            )

            PasswordInputField(
                value = passwordState.value.password,
                onValueChange = {
                    passwordState.value = passwordState.value.copy(password = it)
                },
                labelResId = R.string.password,
                passwordVisible = passwordVisible
            )

            PasswordInputField(
                value = passwordState.value.confirmPassword,
                onValueChange = {
                    passwordState.value = passwordState.value.copy(confirmPassword = it)
                },
                labelResId = R.string.confirm_password,
                passwordVisible = passwordVisible
            )

            Spacer(modifier = Modifier.height(16.dp))

            CompareButton(
                onClick = {
                    avatarUri?.let {
                        userViewModel.registration(
                            login = login.value.text,
                            password = passwordState.value.password.text,
                            name = name.value.text,
                            avatar = it, // может быть null
                            contentResolver = context.contentResolver
                        )
                    }
                },
                passwordState.value.password,
                passwordState.value.confirmPassword
            )
        }
    }
}
