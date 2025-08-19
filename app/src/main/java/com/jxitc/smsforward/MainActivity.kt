package com.jxitc.smsforward

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
import com.jxitc.smsforward.ui.theme.SMSForwardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SMSForwardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SMSForwardApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SMSForwardApp(modifier: Modifier = Modifier) {
    Text(
        text = "SMS Forward",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SMSForwardAppPreview() {
    SMSForwardTheme {
        SMSForwardApp()
    }
}