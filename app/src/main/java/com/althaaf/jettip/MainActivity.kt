package com.althaaf.jettip

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.althaaf.jettip.ui.component.InputTextField
import com.althaaf.jettip.ui.theme.JetTipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetTipTheme {
                MyApp { innerPadding ->
                    MainContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable (PaddingValues) -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    BoxForm(modifier = modifier) {
        Log.d("InputText", it)
    }
}

@Preview
@Composable
fun BoxForm(modifier: Modifier = Modifier, onValueChange: (String) -> Unit = {}) {
    val totalMoney = remember {
        mutableStateOf("")
    }

    val validState = remember(totalMoney.value) {
        totalMoney.value.trim().isNotEmpty()
    }
    
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, Color.LightGray)
    ) {
        Column {
            InputTextField(
                moneyValue = totalMoney,
                labelId = "Add Bill",
                isSingleLine = true,
                isEnabled = true,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
                actionType = KeyboardActions {
                    if(!validState) return@KeyboardActions
                    keyboardController?.hide()
                    onValueChange(totalMoney.value)
                }
            )
        }

    }
}

@Preview
@Composable
fun HeaderTop(modifier: Modifier = Modifier, totalPerPerson: Double = 3.13245) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(corner = CornerSize(size = 12.dp))),
        shape = RectangleShape,
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val totalPerson = "%.2f".format(totalPerPerson)
            Text("Total Per Person", style = MaterialTheme.typography.titleLarge)
            Text(
                "\$$totalPerson",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetTipTheme {
    }
}