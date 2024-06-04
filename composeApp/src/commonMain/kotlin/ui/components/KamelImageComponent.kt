package ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Composable
fun KamelImageComponent(url: String, modifier : Modifier = Modifier, fit: ContentScale = ContentScale.FillWidth) {
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
        modifier = modifier

    )
}