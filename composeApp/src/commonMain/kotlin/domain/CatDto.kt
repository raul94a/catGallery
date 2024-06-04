package domain

import kotlinx.serialization.Serializable

@Serializable
data class CatDto (
    val id: String,
    val url: String,
    val width: Long,
    val height: Long
)
