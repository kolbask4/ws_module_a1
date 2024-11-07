package com.example.myapplication

import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewRootForTest
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.round

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
    data object Vhod : Screen()
    data object MagMain : Screen()
    data object Zareg : Screen()
}

sealed class MagScreen {
    data object Catalog : MagScreen()
    data object Cart : MagScreen()
    data object Profile : MagScreen()
}

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Vhod) }

    when (currentScreen) {
        is Screen.Vhod -> Vhod({ currentScreen = Screen.MagMain }, { currentScreen = Screen.Zareg })
        is Screen.MagMain -> MagMain()
        is Screen.Zareg -> Zareg(
            { currentScreen = Screen.MagMain },
            { currentScreen = Screen.Vhod })
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
fun Zareg(onNavigate: () -> Unit, vihod: () -> Unit) {
    var login by remember { mutableStateOf("") }
    var pochta by remember { mutableStateOf("") }
    var parol by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    var isEmpty by remember { mutableStateOf(false) }

    var passVisible by remember { mutableStateOf(false) }
    var passIcon by remember { mutableStateOf(R.drawable.rounded_visibility_24) }

    if (!passVisible) {
        passIcon = R.drawable.rounded_visibility_24
    } else {
        passIcon = R.drawable.rounded_visibility_off_24
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F5))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .offset(0.dp, 26.dp)
                    .clickable { vihod() }
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                    contentDescription = "back",
                    modifier = Modifier
                        .size(48.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Регистрация"
                )
                Spacer(Modifier.height(16.dp))
            }
        }
        Spacer(Modifier.height(40.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = login,
                        onValueChange = {
                            login = it
                        },
                        placeholder = { Text("Логин", color = Color.LightGray) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            errorContainerColor = Color.White,
                            errorIndicatorColor = Color.Transparent
                        ),
                        maxLines = 1,
                        isError = isEmpty,
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
                        value = pochta,
                        onValueChange = { pochta = it },
                        placeholder = { Text("Почта", color = Color.LightGray) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            errorContainerColor = Color.White,
                            errorIndicatorColor = Color.Transparent
                        ),
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
                            errorIndicatorColor = Color.Transparent,
                            errorTrailingIconColor = Color.Black
                        ),
                        isError = isEmpty,
                        visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                passVisible = !passVisible
                            }) {
                                Icon(
                                    painter = painterResource(passIcon),
                                    contentDescription = "visibility"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    )
                }
            }
            if (isEmpty) {
                Text("Поля не должны быть пустыми", color = Color.Red)
            }
            if (!isValid) {
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
                        isEmpty = login.isEmpty() || pochta.isEmpty() || parol.isEmpty()

                        if (isValid && !isEmpty) {
                            onNavigate()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Создать аккаунт",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun MagMain() {
    var currentScreen by remember { mutableStateOf<MagScreen>(MagScreen.Catalog) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(80.dp),
                containerColor = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { currentScreen = MagScreen.Catalog },
                        modifier = Modifier
                            .weight(0.33f)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Rounded.Star,
                                contentDescription = "star",
                                tint = if (currentScreen == MagScreen.Catalog) Color(0xFF5F46FF) else Color.Black
                            )
                            Text(
                                "Каталог",
                                fontSize = 10.sp,
                                color = if (currentScreen == MagScreen.Catalog) Color(0xFF5F46FF) else Color.Black
                            )
                        }
                    }
                    IconButton(
                        onClick = { currentScreen = MagScreen.Cart },
                        modifier = Modifier
                            .weight(0.34f)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Rounded.ShoppingCart,
                                contentDescription = "cart",
                                tint = if (currentScreen == MagScreen.Cart) Color(0xFF5F46FF) else Color.Black
                            )
                            Text(
                                "Корзина",
                                fontSize = 10.sp,
                                color = if (currentScreen == MagScreen.Cart) Color(0xFF5F46FF) else Color.Black
                            )
                        }
                    }
                    IconButton(
                        onClick = { currentScreen = MagScreen.Profile },
                        modifier = Modifier
                            .weight(0.33f)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Rounded.Person,
                                contentDescription = "profile",
                                tint = if (currentScreen == MagScreen.Profile) Color(0xFF5F46FF) else Color.Black
                            )
                            Text(
                                "Профиль",
                                fontSize = 10.sp,
                                color = if (currentScreen == MagScreen.Profile) Color(0xFF5F46FF) else Color.Black
                            )
                        }
                    }
                }
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .background(Color(0xFFF0F0F5))
        ) {
            when (currentScreen) {
                is MagScreen.Catalog -> Catalog()
                is MagScreen.Cart -> Cart()
                is MagScreen.Profile -> Profile()
            }
        }
    }
}

@Composable
fun Catalog() {
    Column(
        modifier = Modifier
            .padding(top = 50.dp, start = 16.dp, end = 16.dp)
    ) {
        val items = listOf("Keyboard", "Oleg Sokolov", "Laptop", "Mouse", "Monitor", "Computer", "1", "2")

        var searchItem by remember { mutableStateOf("") }

        val filteredItems = items.filter { it.contains(searchItem, ignoreCase = true) }

        OutlinedTextField(
            value = searchItem,
            onValueChange = { searchItem = it },
            leadingIcon = {
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = "search"
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.LightGray,
                focusedLeadingIconColor = Color.LightGray,
                unfocusedLeadingIconColor = Color.LightGray
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            items(filteredItems.size) {index ->
                Box(
                    modifier = Modifier
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(Color.White, Color.Gray)
                                        )
                                    )
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                filteredItems[index],
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Описание",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun Cart() {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(Color.Blue)
    )
}

@Composable
fun Profile() {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(Color.Magenta)
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
        MagMain()
    }
}