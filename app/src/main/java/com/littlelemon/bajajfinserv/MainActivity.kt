package com.littlelemon.bajajfinserv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.littlelemon.bajajfinserv.ui.theme.BajajFinservTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState


class MainActivity : ComponentActivity() {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val employeeDetails = MutableLiveData<String>()

    private suspend fun getEmployee(id: Int): String {
        val response: Map<Int, EmployeeDetails> = client
            .get("https://raw.githubusercontent.com/dixitsoham7/dixitsoham7.github.io/main/index.json")
            .body()
        return response[id]?.name?: String()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val employeeName = getEmployee(1)
            runOnUiThread {
                employeeDetails.value = employeeName
            }
        }
        setContent {
            BajajFinservTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val name = employeeDetails.observeAsState("")
                    Greeting(name.value)
                }
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
    val employeeDetails = MutableLiveData<String>()
    BajajFinservTheme {
        val name = employeeDetails.observeAsState("")
        Greeting(name.value)
    }
}