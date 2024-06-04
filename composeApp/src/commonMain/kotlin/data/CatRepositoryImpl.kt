package data

import domain.CatDto
import domain.CatRepository



class CatRepositoryImpl(private val catsApi: CatsApi) : CatRepository {
    override suspend fun fetchCats(): List<CatDto> {
        return catsApi.fetchCats()
    }
}