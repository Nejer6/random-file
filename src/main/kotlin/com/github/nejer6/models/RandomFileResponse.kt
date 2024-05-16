package com.github.nejer6.models

import kotlinx.serialization.Serializable

@Serializable
data class RandomFileResponse(
    val fileName: String,
    val location: String
)
