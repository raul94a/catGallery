package ui.shared

import androidx.compose.foundation.ScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

fun Modifier.parallaxLayoutModifier(scrollState: ScrollState, rate: Int) =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val height = if (rate > 0) scrollState.value / rate else scrollState.value
        println("Calculation height: $height")
        layout(placeable.width, placeable.height) {
            placeable.place(0, height)
        }
    }
