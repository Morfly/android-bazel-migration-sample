package org.morfly.sample.imagedata

import kotlinx.coroutines.flow.Flow


interface ImagesRepository {

    suspend fun loadImages(): List<Image>
}