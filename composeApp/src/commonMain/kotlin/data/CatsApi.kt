package data

import domain.CatDto

interface CatsApi  {

    suspend fun fetchCats() :List<CatDto>
}