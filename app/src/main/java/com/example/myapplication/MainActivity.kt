package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainScreen()
            }
        }
    }
}

sealed class Screen {
    data object Vhod: Screen()
    data object Catalog: Screen()
    data object Zareg: Screen()
}

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Vhod) }

    when (currentScreen) {
        is Screen.Vhod -> Vhod ({currentScreen = Screen.Catalog}, {currentScreen = Screen.Zareg})
        is Screen.Catalog -> Catalog()
        is Screen.Zareg -> Zareg { currentScreen = Screen.Catalog }
    }
}

@Composable
fun Vhod(onNavigate1: () -> Unit, onNavigate2: () -> Unit) {
    var pochta by remember { mutableStateOf("") }
    var parol by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    var isEmpty by remember { mutableStateOf(false) }

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
                        },
                        placeholder = { Text("Почта", color = Color.LightGray) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            errorContainerColor = Color.White,
                            errorIndicatorColor = Color.Transparent
                        ),
                        maxLines = 1,
                        isError = !isValid,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
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
                        placeholder = { Text("Пароль", color = Color.LightGray) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            errorContainerColor = Color.White,
                            errorIndicatorColor = Color.Transparent
                        ),
                        isError = isEmpty,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    )
                }
            }
            if (isEmpty) {
                Spacer(Modifier.height(20.dp))
                Text("Поля не должны быть пустыми", color = Color.Red)
            }
            if (!isValid) {
                Spacer(Modifier.height(20.dp))
                Text("Введите почту в формате example@gmail.com", color = Color.Red)
            }
            Spacer(Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF5F46FF))
                    .clickable {
                        isValid = isEmailValid(pochta)
                        isEmpty = pochta.isEmpty() || parol.isEmpty()

                        if (isValid && !isEmpty) {
                            onNavigate1()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Войти",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            Spacer(Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF5F46FF))
                    .clickable { onNavigate2() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Зарегистрироваться",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun Zareg(onNavigate: () -> Unit) {
    var pochta by remember { mutableStateOf("") }
    var parol by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    var isEmpty by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
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
                        },
                        placeholder = { Text("Почта", color = Color.LightGray) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            errorContainerColor = Color.White,
                            errorIndicatorColor = Color.Transparent
                        ),
                        maxLines = 1,
                        isError = !isValid,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
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
                        placeholder = { Text("Пароль", color = Color.LightGray) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            errorContainerColor = Color.White,
                            errorIndicatorColor = Color.Transparent
                        ),
                        isError = isEmpty,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    )
                }
            }
            if (isEmpty) {
                Spacer(Modifier.height(20.dp))
                Text("Поля не должны быть пустыми", color = Color.Red)
            }
            if (!isValid) {
                Spacer(Modifier.height(20.dp))
                Text("Введите почту в формате example@gmail.com", color = Color.Red)
            }
            Spacer(Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF5F46FF))
                    .clickable {
                        isValid = isEmailValid(pochta)
                        isEmpty = pochta.isEmpty() || parol.isEmpty()

                        if (isValid && !isEmpty) {
                            onNavigate()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Войти",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            Spacer(Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF5F46FF)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Зарегистрироваться",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun Catalog() {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(Color.Green)
    )
}

fun isEmailValid(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"
    return email.matches(emailPattern.toRegex())
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}