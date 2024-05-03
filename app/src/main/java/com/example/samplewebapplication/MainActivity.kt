package com.example.samplewebapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import com.example.samplewebapplication.ui.theme.SampleWebApplicationTheme
import com.google.accompanist.web.rememberWebViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val (first, second, third, fourth) = remember { FocusRequester.createRefs() }
            val webViewState = rememberWebViewState("https://www.google.com")
            SampleWebApplicationTheme {
                // A surface container using the 'background' color from the theme

                Scaffold(
                    topBar = { HomeScreenHeader(
                                onClickedHeaderButton = {  },
                                one = first,
                                two = second,
                                three = third
                            )
                    },
                    bottomBar = {
                            HomeScreenFooter(
                                0,
                                onClickedFooterButton = {},
                                fourth
                            )
                    }
                ){paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues)

                    ){
                        HomeWebView(
                            modifier = Modifier
                                .focusRequester(third)
                                .focusProperties { next = fourth },
                            state = webViewState,
                            isVisible = true,
                            onWebViewNavigating = {},
                            onPageFinished = {}
                        )
                    }

                }
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SampleWebApplicationTheme {
        Greeting("Android")
    }
}