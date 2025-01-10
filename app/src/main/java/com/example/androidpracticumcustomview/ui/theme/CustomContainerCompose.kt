package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import kotlinx.coroutines.launch

@Composable
fun CustomContainerCompose(
    alphaAnimationDuration: Int,
    translationAnimationDuration: Int,
    items: List<@Composable () -> Unit>
) {
    val coroutineScope = rememberCoroutineScope()

    // Первый элемент
    var firstElementHeight by remember { mutableStateOf(0) }
    var alphaFirstElement = remember { Animatable(0f) }
    var translationYFirstElement = remember { Animatable(0f) }

    // Второй элемент
    var secondElementHeight by remember { mutableStateOf(0) }
    var alphaSecondElement = remember { Animatable(0f) }
    var translationYSecondElement = remember { Animatable(0f) }

    // Родитель
    var parentHeight by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {

        // Запуск анимаций
        coroutineScope.launch {
            launch{
                alphaFirstElement.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(alphaAnimationDuration)
                )
            }
            launch{
                alphaSecondElement.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(alphaAnimationDuration)
                )
            }
            launch{
                translationYFirstElement.animateTo(
                    targetValue = -(parentHeight / 2f) + firstElementHeight / 2f,
                    animationSpec = tween(translationAnimationDuration)
                )
            }
            launch{
                translationYSecondElement.animateTo(
                    targetValue = parentHeight / 2f - secondElementHeight / 2f,
                    animationSpec = tween(translationAnimationDuration)
                )
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize().onSizeChanged { size ->
            parentHeight = size.height
        }
    ) {
        if (items.size <= 2){
            items.forEachIndexed { index, childView ->
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        // Применение переменных анимации к Box дочернего элемента
                        .graphicsLayer {
                            alpha = when (index) {
                                0 -> alphaFirstElement.value
                                1 -> alphaSecondElement.value
                                else -> 1f
                            }
                            translationY = when (index) {
                                0 -> translationYFirstElement.value
                                1 -> translationYSecondElement.value
                                else -> 0f
                            }
                        }
                        .onSizeChanged { size ->
                            when (index) {
                                0 -> firstElementHeight = size.height
                                1 -> secondElementHeight = size.height
                                else -> 0
                            }
                        }
                ) {
                    childView()
                }
            }
        } else{
            // Ошибка если добавленных view уже 2 и пользователь пытается добавить 3-е
            throw IllegalStateException("Can't set more than 2 views in CustomContainerCompose.kt. Current count: ${items.size}")
        }

    }
}