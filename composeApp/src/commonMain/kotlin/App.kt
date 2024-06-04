import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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


@Composable
fun CatAppBar(
    currentScreen: CatScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen.title) },

        modifier = modifier,
        navigationIcon = {

                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = ""
                    )

            }
        }
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
                    modifier = Modifier.padding(top = 30.dp),
                    currentScreen = CatScreen.Home,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }

        ) {


            NavHost(
                navController = navController,
                startDestination = CatScreen.Home.name,
                modifier = Modifier.padding(it)
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


