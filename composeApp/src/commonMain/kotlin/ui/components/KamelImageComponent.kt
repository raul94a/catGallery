package ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Composable
fun KamelImageComponent(
    url: String,
    modifier: Modifier = Modifier,
    fit: ContentScale = ContentScale.FillWidth,
    width: Int = 150, height: Int = 150
) {
    val painterResource: Resource<Painter> = asyncPainterResource(url) {

        // CoroutineContext to be used while loading the image.
        coroutineContext = Job() + Dispatchers.Main

        // Customizes HTTP request
        requestBuilder { // this: HttpRequestBuilder
            header("Key", "Value")
            parameter("Key", "Value")

        }

    }
    KamelImage(
        resource = painterResource,
        contentDescription = "",
        contentScale = fit,
        modifier = modifier,
        onLoading = { boxScope ->
            Box(
                modifier = Modifier.width(width.dp).height(height.dp)
                    .background(brush = shimmerBrush(), shape = RoundedCornerShape(0.dp))
            )
        }

    )
}