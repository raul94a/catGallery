package ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.key


import domain.CatDto
import org.koin.compose.koinInject
import ui.shared.KamelImageComponent


@Composable
fun HomeScreen(

    viewModel: HomeViewModel = koinInject(), onNavigate: (String) -> Unit
) {



    val uiState = viewModel.uiState.collectAsState().value

    Column() {
        ImageGrid(
            uiState.cats,
            onInfiniteScrolling = {
              viewModel.getCats()
            },
            onNavigate = onNavigate
        )

    }
}


@Composable
fun ImageGrid(
    catList: List<CatDto>,
    onInfiniteScrolling: () -> Unit,
    onNavigate: (String) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2)
    ) {
        items(count = catList.count()) { index ->

            val cat = catList[index]
            if (index == catList.count() - 2) {

                onInfiniteScrolling()


            }


            key(cat.id){

                KamelImageComponent(cat.url,
                    width = cat.width.toInt(),
                    height = cat.height.toInt(),
                    modifier = Modifier.clickable {
                    onNavigate(cat.url)
                })
            }

        }
    }
}


