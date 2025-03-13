package com.althaaf.jettip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import com.althaaf.jettip.ui.widget.RoundedIconButton
import com.althaaf.jettip.utils.calculateTotalPerPerson
import com.althaaf.jettip.utils.calculateTotalTip

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

@Preview
@Composable
fun MainContent(modifier: Modifier = Modifier) {

    val splitStateValue = remember {
        mutableIntStateOf(1)
    }

    val totalTipValue = remember {
        mutableDoubleStateOf(0.0)
    }

    val totalPerPersonValue = remember {
        mutableDoubleStateOf(0.0)
    }
    Column(modifier = modifier.padding(12.dp)) {
        BoxForm(
            modifier = Modifier,
            totalTipValue = totalTipValue,
            splitStateValue = splitStateValue,
            totalPerPersonValue = totalPerPersonValue
        ) {}
    }
}

@Composable
fun BoxForm(
    modifier: Modifier = Modifier,
    totalTipValue: MutableState<Double>,
    totalPerPersonValue: MutableState<Double>,
    splitStateValue: MutableState<Int>,
    onValueChange: (String) -> Unit = {}
) {
    val totalMoney = remember {
        mutableStateOf("")
    }

    val validState = remember(totalMoney.value) {
        totalMoney.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val sliderStateValue = remember {
        mutableFloatStateOf(0f)
    }

    val tipPercentage = (sliderStateValue.value * 100).toInt()


    HeaderTop(
        modifier = Modifier.padding(bottom = 12.dp),
        totalPerPerson = totalPerPersonValue.value
    )
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
                    if (!validState) return@KeyboardActions
                    keyboardController?.hide()
                    onValueChange(totalMoney.value)
                }
            )
            if (validState) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Split",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.width(120.dp))
                    RoundedIconButton(
                        modifier = Modifier,
                        icon = Icons.Default.Remove,
                        tint = Color.Black,
                        onAction = {
                            if (splitStateValue.value > 1) splitStateValue.value -= 1
                            totalPerPersonValue.value = calculateTotalPerPerson(
                                totalMoney.value.toDouble(),
                                sliderStateValue.floatValue,
                                splitStateValue.value
                            )
                        }
                    )
                    Text(
                        "${splitStateValue.value}",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 9.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    RoundedIconButton(
                        modifier = Modifier,
                        icon = Icons.Default.Add,
                        tint = Color.Black,
                        onAction = {
                            splitStateValue.value += 1
                            totalPerPersonValue.value = calculateTotalPerPerson(
                                totalMoney.value.toDouble(),
                                sliderStateValue.floatValue,
                                splitStateValue.value
                            )
                        }
                    )
                }
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Tip",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(140.dp))
                    Text(
                        "${totalTipValue.value}$",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text("$tipPercentage%", style = MaterialTheme.typography.bodyLarge)
                    Slider(
                        value = sliderStateValue.floatValue,
                        onValueChange = { newValue ->
                            sliderStateValue.floatValue = newValue
                            totalTipValue.value = calculateTotalTip(
                                totalMoney.value.toDouble(),
                                sliderStateValue.floatValue
                            )
                            totalPerPersonValue.value = calculateTotalPerPerson(
                                totalMoney.value.toDouble(),
                                sliderStateValue.floatValue,
                                splitStateValue.value
                            )
                        },
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 14.dp),
                    )
                }
            } else {
                Box { }
            }
        }
    }
}

@Preview
@Composable
fun HeaderTop(modifier: Modifier = Modifier, totalPerPerson: Double = 0.0) {
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