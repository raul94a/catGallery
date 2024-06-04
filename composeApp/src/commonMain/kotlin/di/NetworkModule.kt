package di

import data.CatApiImpl
import data.CatRepositoryImpl
import data.CatsApi
import domain.CatDto
import domain.CatRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body

import io.ktor.client.request.get


import kotlinx.serialization.json.*
import org.koin.dsl.module


val networkModule = module {
    single {
        HttpClient()
    }

    single<CatsApi> {
        CatApiImpl(get())
    }

    single<CatRepository> {
        CatRepositoryImpl(get())
    }
}

