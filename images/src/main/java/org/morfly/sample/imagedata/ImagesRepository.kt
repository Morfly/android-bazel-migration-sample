package org.morfly.sample.imagedata

import kotlinx.coroutines.flow.Flow


interface ImagesRepository {

    fun loadImages(): Flow<List<Image>>
}