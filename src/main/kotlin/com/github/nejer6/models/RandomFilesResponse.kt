package com.github.nejer6.models

import kotlinx.serialization.Serializable

@Serializable
data class RandomFilesResponse(
    val files: List<RandomFileResponse>,
    val update: Boolean
)