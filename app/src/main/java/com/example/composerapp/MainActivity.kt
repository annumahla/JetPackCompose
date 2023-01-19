package com.example.composerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composerapp.chatbot.model.ChatsModel
import com.example.composerapp.chatbot.viewmodel.ChatViewModel
import com.example.composerapp.itemview.ItemView
import com.example.composerapp.model.Message
import com.example.composerapp.ui.theme.ComposerAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //printHelloWord()
        //printDynamicWord()
        //printDynamicWordLayout()
        createRecyclerView(getListItems())

        //createChatBotLayout()
    }

    // It will print just Hello word
    private fun printHelloWord() {
        setContent {
            Text("Hello All my team members!")
        }
    }

    // create composable method and call it from setContent
    private fun printDynamicWord() {
        setContent {
            DynamicWord("All my team members")
        }
    }

    @Composable
    fun DynamicWord(name: String) {
        Text(text = "Hello $name!")
    }

    // create composable method to multiple content
    private fun printDynamicWordLayout() {
        setContent {
            DynamicWordLayout("All my team members")
        }
    }

    private fun getListItems(): List<Message> {
        val msgs = ArrayList<Message>()
        msgs.add(Message("Hello Android developers", "Please look at JetPack Compose tutorial!", R.drawable.ic_launcher_foreground))

        msgs.add(Message("Hey Boni", "Wdyt can we implement it in our app or not?", R.drawable.ic_launcher_foreground))
        msgs.add(Message("Halo mas Hendi", "Suggest us if we have more better technologies.", R.drawable.ic_launcher_foreground))
        msgs.add(Message("Heyyyy Agung", "R u ready to use JetPack?", R.drawable.ic_launcher_foreground))

        msgs.add(Message("Hello Ios developers", "Is there anything else in Ios also like we have JetPack in Android ?", R.drawable.ic_launcher_foreground))
        msgs.add(Message("Hello FrontEnd developers", "Is there anything else in FE also like we have JetPack in Android ?", R.drawable.ic_launcher_foreground))

        msgs.add(Message("Hello team members", "I know this is just small tutorial but I'm trying to make fun hahah", R.drawable.ic_launcher_foreground))

        return msgs
    }

    // create recyclerview
    private fun createRecyclerView(msgs: List<Message>) {
        setContent {
            ComposerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ConversationItems(msgs)
                }
            }
        }
    }

    private fun createChatBotLayout() {
        setContent {
            ComposerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Conversation()
                }
            }
        }
    }
}



//@Preview
//@Composable
//fun PreviewDynamicWord() {
//    DynamicWord("Android Developers")
//}

@Composable
fun DynamicWordLayout(name: String) {
    Column {
        Text(text = "Hello $name!")
        Text(text = "Please look at JetPack compose tutorial.")
    }
}

@Composable
fun ConversationItems(msgs: List<Message>) {
    LazyColumn(modifier = Modifier.padding(bottom = 10.dp)) {
        items(msgs) { message ->
            ItemView(Message(message.name, message.topic, message.icon))
        }
    }
}


@Composable
fun Conversation(chatViewModel: ChatViewModel = viewModel()) {
    val chatUiState by chatViewModel.uiState.collectAsState()
    ConversationItems(chatUiState.messageList)

    val query = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .height(100.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        TextField(
            value = query.value,
            onValueChange = {
                query.value = it
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp
            ),
            label = { Text("Enter Your Query Here") },
        )


        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 10.dp)
                .clickable {
                    chatViewModel.getResponse(query.value)
                    query.value = ""
                }

        ) {
            Image(
                painter = painterResource(R.drawable.ic_baseline_send_24),
                contentDescription = "send icon",
                modifier = Modifier
                    .clip(CircleShape)
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposerAppTheme {
        ItemView(Message("colleague", "here we are testing the jetpack compose", R.drawable.ic_launcher_foreground))
    }
}