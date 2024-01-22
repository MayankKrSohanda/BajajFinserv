package com.littlelemon.bajajfinserv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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


class MainActivity : ComponentActivity() {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val employeeNamesLiveData = MutableLiveData<List<EmployeeDetails>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val employeeName = getEmployee("employees")
            runOnUiThread {
                employeeNamesLiveData.value = employeeName
            }
        }

        setContent {
            BajajFinservTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val items = employeeNamesLiveData.observeAsState(emptyList())
                        EmployeeName(items.value)
                    }
                }
            }
        }
    }

    private suspend fun getEmployee(employee: String): List<EmployeeDetails> {
        val response: Map<String, List<EmployeeDetails>> = client
            .get("https://raw.githubusercontent.com/dixitsoham7/dixitsoham7.github.io/main/index.json")
            .body()
        return response[employee] ?: listOf()
    }
}

@Composable
fun EmployeeName(
    items: List<EmployeeDetails> = emptyList()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        LazyColumn {
            itemsIndexed(items) { _, item ->
                EmployeeNameDetails(item)
            }
        }
    }
}

@Composable
fun EmployeeNameDetails(
    employee: EmployeeDetails
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (employee.name != null) {
            Text(text = employee.name)
        }
    }
}