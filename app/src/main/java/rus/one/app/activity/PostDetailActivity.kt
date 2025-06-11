package rus.one.app.posts.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import rus.one.app.R
import rus.one.app.components.bar.TopBar

class PostDetailActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DetailPost()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DetailPost() {

    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.post), onBackClick = {(context as? PostDetailActivity)?.finish()}, onClick = {}, R.drawable.ic_share)
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFFFFFF)),


            ) {


    }
}}


