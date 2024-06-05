package ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import catgallery.composeapp.generated.resources.Res
import catgallery.composeapp.generated.resources.lorem
import org.jetbrains.compose.resources.stringResource
import ui.shared.KamelImageComponent
import ui.shared.parallaxLayoutModifier


@Composable
fun DetailScreen(url: String?) {

    if (
        url == null
    ) {
        Text("Error!")
    } else {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {

            KamelImageComponent(
                url,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(.8f)
                    .parallaxLayoutModifier(scrollState, 4),
                fit = ContentScale.FillBounds
            )

            Text(
                text = stringResource(Res.string.lorem),
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 8.dp),

                )


        }
    }

}