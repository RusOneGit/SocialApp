package rus.one.app.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import rus.one.app.R
import rus.one.app.components.bar.TopBar

class NewPostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewPost()
        }
    }

    @Preview
    @Composable
    fun NewPost() {
        val message = remember { mutableStateOf("") }
        val context = LocalContext.current
        Scaffold(topBar = {
            TopBar(
                title = stringResource(R.string.newPost),
                onBackClick = { (context as? NewPostActivity)?.finish() },
                onClick = {},
                painterId = R.drawable.ic_done
            )

        }) { paddingValues ->
            TextField(
                value = message.value,
                onValueChange = { text -> message.value = text },
                maxLines = 20,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f),
                colors = TextFieldDefaults.colors(focusedContainerColor = Color(0xFFFFFF))

            )
        }

    }
}

