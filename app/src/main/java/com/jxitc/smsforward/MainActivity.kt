package com.jxitc.smsforward

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jxitc.smsforward.presentation.screen.PermissionScreen
import com.jxitc.smsforward.ui.theme.SMSForwardTheme
import com.jxitc.smsforward.utils.PermissionUtils

class MainActivity : ComponentActivity() {
    
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Handle permission results - for now just recreate to refresh UI
        recreate()
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SMSForwardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SMSForwardApp(
                        modifier = Modifier.padding(innerPadding),
                        onRequestPermissions = {
                            permissionLauncher.launch(PermissionUtils.REQUIRED_SMS_PERMISSIONS)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SMSForwardApp(
    modifier: Modifier = Modifier,
    onRequestPermissions: () -> Unit = {}
) {
    val context = LocalContext.current
    val hasPermissions = remember(context) {
        PermissionUtils.hasAllSmsPermissions(context)
    }
    
    if (hasPermissions) {
        Text(
            text = "SMS Forward - Ready!",
            modifier = modifier
        )
    } else {
        PermissionScreen(
            onRequestPermissions = onRequestPermissions,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SMSForwardAppPreview() {
    SMSForwardTheme {
        SMSForwardApp()
    }
}