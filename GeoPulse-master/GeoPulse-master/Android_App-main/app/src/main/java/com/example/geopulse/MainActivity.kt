package com.example.geopulse

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geopulse.KtorClient.StudentLogin
import com.example.geopulse.ui.theme.GeoPulseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeoPulseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginSuccess = { /* Handle successful login */ }
                    )
                }
            }
        }
    }
}

enum class UserType(val displayName: String) {
    STUDENT("Student"),
    TEACHER("Teacher")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current

    var userType by remember { mutableStateOf<UserType?>(null) }
    var userId by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val isLoginEnabled = userType != null && userId.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Radio buttons for User Type
        Text("Select User Type:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = userType == UserType.STUDENT,
                onClick = { userType = UserType.STUDENT },
                colors = RadioButtonDefaults.colors()
            )
            Text("Student", modifier = Modifier.clickable { userType = UserType.STUDENT })

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = userType == UserType.TEACHER,
                onClick = { userType = UserType.TEACHER },
                colors = RadioButtonDefaults.colors()
            )
            Text("Teacher", modifier = Modifier.clickable { userType = UserType.TEACHER })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User ID Input Field
        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("User ID") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Error Message
        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Login Button
        Button(
            onClick = {
                if (isLoginEnabled) {
                    isLoading = true
                    errorMessage = null // reset error first

                    CoroutineScope(Dispatchers.IO).launch {
                        val result = when (userType) {
                            UserType.STUDENT -> KtorClient.StudentLogin(userId)
                            UserType.TEACHER -> KtorClient.TeacherLogin(userId)
                            else -> ""
                        }

                        with(Dispatchers.Main) {
                            isLoading = false

                            if (result.contains("ID exists")) {
                                val intent = Intent(context, MapsActivity::class.java)
                                intent.putExtra("userid", userId)
                                intent.putExtra("usertype", userType!!.name.lowercase())
                                context.startActivity(intent)
                                onLoginSuccess()
                            } else {
                                errorMessage = "Invalid ${userType?.displayName?.lowercase()} credentials."
                            }
                        }
                    }
                }
            },
            enabled = isLoginEnabled
        ) {
            Text("Login")
        }


        // Loading Spinner
        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    GeoPulseTheme {
        LoginScreen(
            onLoginSuccess = {}
        )
    }
}