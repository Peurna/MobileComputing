package com.example.composetutorial
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import android.media.MediaPlayer
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.MainActivity.Message


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = "home_screen"
                    ){
                        composable("home_screen"){
                            HomeScreen(navController)
                        }
                        composable("settings_screen"){
                            SettingsScreen(navController)
                        }

                    }
                }
            }
        }
    }

    @Composable
    fun  HomeScreen(navController: NavController) {
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ){
            Button(
                onClick = {navController.navigate("settings_screen")},
            ){
                Text("Settings")
            }

            Conversation(demoMessages)
        }
    }
    @Composable
    fun SettingsScreen(navController: NavController) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text("Settings", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = {
                navController.navigate("home_screen") {
                    popUpTo("home_screen") { inclusive = true }
                }
            }) {
                Text("Back to Messages")
            }
        }
    }

    data class Message(val user: String, val message: String, val time: String, val pic: Int)


    @Composable

    fun MessageCard(msg: Message) {
        Surface(
            shape = MaterialTheme.shapes.large,
            shadowElevation = 50.dp,
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Row(modifier = Modifier.padding(all = 15.dp)) {
                Image(
                    painter = painterResource(msg.pic),
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .border(5.dp, MaterialTheme.colorScheme.primary)
                )


                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = msg.user,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = msg.message)
                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        text = msg.time,
                        color = MaterialTheme.colorScheme.outline,
                        style = MaterialTheme.typography.titleSmall

                    )

                }
            }
        }
    }

    @Composable
    fun Conversation(messages: List<Message>) {
        val context = LocalContext.current

        LazyColumn {
            items(messages) { message ->

                if (message.message == "vm") {
                    AudioMessageButton(context)
                } else {

                    MessageCard(message)
                }
            }
        }
    }
    @Composable
    fun AudioMessageButton(context: android.content.Context) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val mp = MediaPlayer.create(context, R.raw.dornan_welcome)
                    mp.setOnCompletionListener { it.release() }
                    mp.start()
                },
                modifier = Modifier.height(60.dp)
            ) {
                Text("Voice message")
            }
        }
    }

    @Preview
    @Composable
    fun PreviewConversation() {
        ComposeTutorialTheme {
            Conversation(messages = demoMessages)

        }
    }
}
val demoMessages = listOf(
    Message("SERGEANT ARCH DORNAN", "apolgy for bad english", "12:00", R.drawable.images),
    Message("SERGEANT ARCH DORNAN", "where were u wen club penguin die", "12:00", R.drawable.images),
    Message("SERGEANT ARCH DORNAN", "i was at house eating dorito when phone ring", "12:00", R.drawable.images),
    Message(user = "phone", message = "*ring*", time = "12:00", pic = R.drawable.phone),
    Message(user = "phone", message = "Club penguin is kil", time = "12:00", pic = R.drawable.phone),
    Message("SERGEANT ARCH DORNAN", "no", "12:00", R.drawable.images),
    Message("Club penguin", "*die*", "12:00", R.drawable.peng),
    Message("SERGEANT ARCH DORNAN", "vm", "12:00", R.drawable.images),
    Message("SERGEANT ARCH DORNAN", "Welcome to Camp Navarro.", "12:00", R.drawable.images),
    Message("SERGEANT ARCH DORNAN", "So, you're the new replacement...", "12:00", R.drawable.images),
    Message("SERGEANT ARCH DORNAN", "YOU ARE OUT OF UNIFORM, SOLDIER!", "12:00", R.drawable.images),
    Message("SERGEANT ARCH DORNAN", "WHERE IS YOUR POWER ARMOR?", "12:00", R.drawable.images),
)