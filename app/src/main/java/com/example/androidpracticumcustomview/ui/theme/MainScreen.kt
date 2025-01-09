package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainScreen() {
    val elements = remember { mutableStateListOf<@Composable () -> Unit>() }
    // Длительность анимации прозрачности
    val alphaAnimationDuration = 2000
    // Длительность анимации перемещенияэлементов к краям
    val translationAnimationDuration = 5000

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), contentAlignment = Alignment.Center
        ) {
            elements.add { Text("First element") }
            elements.add { Button(onClick = {}) { Text("Second element") } }
//            elements.add { Text("Third element") }


            CustomContainerCompose(
                alphaAnimationDuration,
                translationAnimationDuration,
                elements
            )
        }
    }
}