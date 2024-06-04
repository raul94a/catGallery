package ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.CatDto
import domain.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class HomeUiState(
    var page: Int = 0,
    var loading: Boolean = false,
    var cats: List<CatDto> = mutableListOf()
)

class HomeViewModel(private val catRepository: CatRepository) : ViewModel() {


    private var _uiState = MutableStateFlow(HomeUiState())

    val uiState = _uiState.asStateFlow()

    init {
        getCats()
    }

    fun getCats() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(loading = true)
            }
            try {
                val cats = catRepository.fetchCats()
                val newCats = _uiState.value.cats + cats
                _uiState.update {
                    it.copy(cats = newCats)
                }
            } catch (exception: Exception) {
                println("Error: $exception")
            } finally {
                _uiState.update { it.copy(loading = false) }
            }
        }
    }


}