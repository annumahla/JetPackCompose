package com.example.composerapp.chatbot.viewmodel

import androidx.lifecycle.ViewModel
import com.example.composerapp.*
import com.example.composerapp.chatbot.ApiService
import com.example.composerapp.chatbot.BotResponseViewState
import com.example.composerapp.chatbot.model.BotResponseModel
import com.example.composerapp.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(BotResponseViewState())
    val uiState: StateFlow<BotResponseViewState> = _uiState.asStateFlow()

    private val USER_KEY = "user"
    private val BOT_KEY = "bot"
    val listItems = ArrayList<Message>()

    fun getResponse(msg: String) {
        val url = "http://api.brainshop.ai/get?bid=170161&key=EOsFH1cIepCCnW4p&uid=[uid]&msg="+msg
        val baseUrl = "http://api.brainshop.ai/"

        val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        retrofit.create(ApiService::class.java).getMessage(url).enqueue(object:
            Callback<BotResponseModel> {
            override fun onResponse(call: Call<BotResponseModel>, response: Response<BotResponseModel>) {
                if(response.isSuccessful){
                    val msgModel = response.body()
                    val firstResponse = msgModel?.cnt.orEmpty()

                    listItems.add(Message(msg, firstResponse, R.drawable.ic_launcher_foreground))

                    _uiState.update { currentState ->
                        currentState.copy(
                            messageList = listItems
                        )
                    }
                }

            }

            override fun onFailure(call: Call<BotResponseModel>, t: Throwable) {
                listItems.add(Message(msg, "Sorry Could u plz elaborate?", R.drawable.ic_launcher_foreground))

                _uiState.update { currentState ->
                    currentState.copy(
                        messageList = listItems
                    )
                }
            }
        })
    }

}