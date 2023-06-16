package com.zen.cendakala.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.Color3
import com.zen.cendakala.ui.theme.White2

@Composable
fun TabsCustom() {
    var selectedIndex by remember { mutableStateOf(0) }

    val list = listOf("Survey", "Your Data")

    TabRow(selectedTabIndex = selectedIndex,
        containerColor = Color3,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(50))
            .padding(1.dp),
        indicator = { tabPositions: List<TabPosition> ->
            Box {}
        }
    ) {
        list.forEachIndexed { index, text ->
            val selected = selectedIndex == index
            Tab(
                modifier = if (selected) Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        Color1
                    )
                else Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        Color3
                    ),
                selected = selected,
                onClick = { selectedIndex = index },
                text = { Text(text = text, color = White2) }
            )
        }
    }
}