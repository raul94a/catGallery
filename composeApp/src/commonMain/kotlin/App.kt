import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults.contentWindowInsets
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import domain.CatRepository
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument

import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import ui.details.DetailScreen
import ui.home.HomeScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatAppBar(
    currentScreen: CatScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text  (currentScreen.title) },
        modifier = modifier.height(60.dp),
        navigationIcon = {

            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = ""
                )

            }
        },
        windowInsets = WindowInsets.statusBars
    )
}

@Composable
@Preview
fun App() {


    KoinContext {

        AppContent()
    }

}

@Composable
fun AppContent(repository: CatRepository = koinInject()) {
// Get current back stack entry
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen


    MaterialTheme {

        Scaffold(
            topBar = {

                CatAppBar(

                    currentScreen = CatScreen.Home,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }

        ) {


            NavHost(
                navController = navController,
                startDestination = CatScreen.Home.name,
                modifier = Modifier.padding(it).padding(top = it.calculateTopPadding())
            ) {

                composable(route = CatScreen.Home.name) {
                    HomeScreen { imageUrl ->
                        println("ImageUrl is: $imageUrl")
                        val lastIndex = imageUrl.lastIndexOf("/")
                        val lastUrlPart = imageUrl.substring(startIndex = lastIndex + 1)
                        val url = CatScreen.Details.name + "/$lastUrlPart"
                        println("Url is: $url")
                        navController.navigate(url)
                    }
                }

                composable(
                    route = CatScreen.Details.title,
                    arguments = listOf(navArgument("url") { type = NavType.StringType })
                ) { backStackEntry ->
                    val url : String? = backStackEntry.arguments?.getString("url")
                    val data = "https://cdn2.thecatapi.com/images/$url"
                    DetailScreen(data)
                }

            }

        }
    }

}


