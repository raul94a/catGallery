package ui.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import ui.components.KamelImageComponent


@Composable
fun DetailScreen(url: String?) {

    if(
        url == null
    ){
        Text("Error!")
    }

    else {

        KamelImageComponent(url, modifier = Modifier.fillMaxSize(), fit = ContentScale.FillBounds)


    }

}