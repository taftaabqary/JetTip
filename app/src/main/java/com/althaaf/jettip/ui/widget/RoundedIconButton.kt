package com.althaaf.jettip.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val RoundedButtonSize = Modifier.size(40.dp)

@Composable
fun RoundedIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onAction: () -> Unit,
    tint: Color,
    background: Color = MaterialTheme.colorScheme.background,
    elevation: Dp = 4.dp,
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .clickable {
                onAction.invoke()
            },
        colors = CardDefaults.cardColors(background),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Plus or minus icon",
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally),
            tint = tint
        )
    }
}