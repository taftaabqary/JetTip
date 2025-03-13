package com.althaaf.jettip.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    moneyValue: MutableState<String>,
    labelId: String,
    isSingleLine: Boolean,
    isEnabled: Boolean,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Number,
    actionType: KeyboardActions
    ) {

    OutlinedTextField(
        value = moneyValue.value,
        onValueChange = {
            moneyValue.value = it
        },
        modifier = modifier.padding(12.dp).fillMaxWidth(),
        label = {
            Text(labelId)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.AttachMoney, contentDescription = "show icon dollar")
        },
        singleLine = isSingleLine,
        enabled = isEnabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = actionType
    )
}