package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Vhod()
            }
        }
    }
}

@Composable
fun Vhod() {
    var pochta by remember { mutableStateOf("") }
    var parol by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F5))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.White),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                Text(
                    "Авторизация"
                )
                Spacer(Modifier.height(16.dp))
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = pochta,
                        onValueChange = {
                            pochta = it
                            isValid = isEmailValid(pochta)
                        },
                        placeholder = { Text("почта", color = Color.LightGray) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            errorContainerColor = Color.White
                        ),
                        isError = !isValid,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                    TextField(
                        value = parol,
                        onValueChange = { parol = it },
                        placeholder = { Text("пароль", color = Color.LightGray) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
            if (!isValid) {
                Spacer(Modifier.height(16.dp))
                Text("Введите почту в формате example@gmail.com", color = Color.Red)
            }

        }
    }
}

fun isEmailValid(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"
    return email.matches(emailPattern.toRegex())
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Vhod()
    }
}