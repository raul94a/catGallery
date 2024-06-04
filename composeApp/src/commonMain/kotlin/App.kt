import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import data.CatApiImpl
import data.CatRepositoryImpl
import di.initKoin
import domain.CatDto
import domain.CatRepository
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject


@Composable
@Preview
fun App() {


    KoinContext {

        AppContent()
    }

}

@Composable
fun AppContent(repository: CatRepository = koinInject()) {

    MaterialTheme {

        Scaffold(
            topBar = {
                TopAppBar {
                    Text("Compose App")
                }
            }
        ) {
            var catList by remember {
                mutableStateOf(emptyList<CatDto>())
            }

            LaunchedEffect(true) {
                withContext(Dispatchers.Main) {
                    val cats = repository.fetchCats()
                    catList = cats
                }
            }

            Column(modifier = Modifier.padding(it)) {
                ImageGrid(
                    catList,
                    onInfiniteScrolling = { scope ->
                        scope.launch {
                            val formerCats = catList
                            val cats = repository.fetchCats()
                            val newlist = mutableListOf<CatDto>()
                            newlist.addAll(formerCats)
                            newlist.addAll(cats)
                            catList = newlist
                        }
                    }
                )

            }
        }

    }
}


@Composable
fun ImageGrid(catList: List<CatDto>, onInfiniteScrolling: (CoroutineScope) -> Unit) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2)
    ) {
        items(count = catList.count()) { index ->

            val cat = catList[index]
            if (index == catList.count() - 2) {
                LaunchedEffect(true) {
                    withContext(Dispatchers.Default) {
                        onInfiniteScrolling(this)
                    }
                }
            }

            StaggeredGridItem(cat)

        }
    }
}


@Composable
fun StaggeredGridItem(cat: CatDto) {
    val painterResource: Resource<Painter> = asyncPainterResource(cat.url) {

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
        contentScale = ContentScale.FillWidth

    )
}