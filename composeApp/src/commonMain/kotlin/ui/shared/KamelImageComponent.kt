package ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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