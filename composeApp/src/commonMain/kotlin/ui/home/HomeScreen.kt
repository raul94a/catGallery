package ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import domain.CatDto
import domain.CatRepository
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject
import ui.components.KamelImageComponent


@Composable
fun HomeScreen(

    repository: CatRepository = koinInject(), onNavigate: (String) -> Unit) {
    var catList by remember {
        mutableStateOf(emptyList<CatDto>())
    }

    LaunchedEffect(true) {
        withContext(Dispatchers.Main) {
            val cats = repository.fetchCats()
            catList = cats
        }
    }


    Column() {
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
            },
            onNavigate = onNavigate
        )

    }
}


@Composable
fun ImageGrid(catList: List<CatDto>, onInfiniteScrolling: (CoroutineScope) -> Unit, onNavigate: (String) -> Unit) {
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

            KamelImageComponent(cat.url, modifier = Modifier.clickable {
                onNavigate(cat.url)
            })

        }
    }
}


