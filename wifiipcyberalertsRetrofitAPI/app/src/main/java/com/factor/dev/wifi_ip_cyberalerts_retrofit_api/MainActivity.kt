package com.factor.dev.wifi_ip_cyberalerts_retrofit_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.factor.dev.wifi_ip_cyberalerts_retrofit_api.ui.theme.WifiipcyberalertsRetrofitAPITheme


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CyberAlertApp()
        }
    }
}

@Composable
fun CyberAlertApp() {
    var alertMessage by remember { mutableStateOf("") }
    var alertType by remember { mutableStateOf("") }
    var responseMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = alertType,
            onValueChange = { alertType = it },
            label = { Text("Alert Type") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = alertMessage,
            onValueChange = { alertMessage = it },
            label = { Text("Alert Message") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                postAlert(alertType, alertMessage) { response ->
                    responseMessage = response ?: "Failed to post alert"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send Alert")
        }
        Text(text = responseMessage, style = MaterialTheme.typography.bodyLarge)
    }
}

private fun postAlert(type: String, message: String, callback: (String?) -> Unit) {
    val alert = Alert(type = type, message = message)
    RetrofitInstance.api.postAlert(alert).enqueue(object : Callback<Alert> {
        override fun onResponse(call: Call<Alert>, response: Response<Alert>) {
            if (response.isSuccessful) {
                callback("Alert posted successfully: ${response.body()?.message}")
            } else {
                callback("Failed to post alert: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<Alert>, t: Throwable) {
            callback("Error: ${t.message}")
        }
    })
}
