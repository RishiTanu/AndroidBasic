package com.example.androidbasics

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.androidbasics.broadcstreceiver.AirplaneModeBroadcastReceiver
import com.example.androidbasics.broadcstreceiver.TestReceiever
import com.example.androidbasics.services.RunningServices
import com.example.androidbasics.ui.theme.AndroidBasicsTheme

class MainActivity : ComponentActivity() {

    //private val viewModel = ContactViewModel()
    // private val viewModel by viewModels<ContactViewModel>()

    private val viewModelImages by viewModels<ImageViewModel>()

    private val airplaneModeBroadcastReceiver = AirplaneModeBroadcastReceiver()
    private val testReceiver = TestReceiever()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(
            airplaneModeBroadcastReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
        registerReceiver(
            testReceiver,
            IntentFilter("TEST_ACTION")
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        setContent {
            AndroidBasicsTheme {
                val viewModel = viewModel<ContactViewModel>(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ContactViewModel(
                                helloWorld = "Hello World"
                            ) as T
                        }
                    }
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = viewModel.backgroundColor
                ) {
                    AsyncImage(model = viewModelImages.uri, contentDescription = "")

                    Button(onClick = {
                        //  viewModel.changeBackgroundColor()
                        /*Intent(this, SecondActivity::class.java).also {
                            startActivity(it)
                        }*/
                        /* Intent(Intent.ACTION_MAIN).also {
                             if (it.resolveActivity(packageManager) != null) {
                                 it.`package` = "com.google.android.youtube"
                                 startActivity(it)
                             }
                         }*/

                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("text@gmail.com"))
                            putExtra(Intent.EXTRA_SUBJECT, "This is my subject")
                            putExtra(Intent.EXTRA_TEXT, "This is the content of my email")

                        }
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent)
                        }


                    }) {
                        Text(text = "Change Color")
                    }

                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = ""
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            Intent(applicationContext, RunningServices::class.java).also {
                                it.action = RunningServices.Actions.START.toString()
                                startService(it)
                            }
                        }) {
                            Text(text = "Start Service")
                        }
                        Button(onClick = {
                            Intent(applicationContext, RunningServices::class.java).also {
                                it.action = RunningServices.Actions.STOP.toString()
                                startService(it)
                            }
                        }) {
                            Text(text = "Stop Service")
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent.getParcelableExtra(Intent.EXTRA_STREAM)
        }
        viewModelImages.updateImage(uri)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneModeBroadcastReceiver)
        unregisterReceiver(testReceiver)
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
    AndroidBasicsTheme {
        Greeting("Android")
    }
}