package data

import domain.CatDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

class CatApiImpl(private val httpClient: HttpClient) : CatsApi {
    override suspend fun fetchCats(): List<CatDto> {
        val data = httpClient.get("https://api.thecatapi.com/v1/images/search?limit=15").body<String>()
        return Json.decodeFromString<List<CatDto>>(data)
    }
}