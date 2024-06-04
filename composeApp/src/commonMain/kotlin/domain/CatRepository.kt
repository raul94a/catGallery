package domain

interface CatRepository {

    suspend fun fetchCats() : List<CatDto>
}


