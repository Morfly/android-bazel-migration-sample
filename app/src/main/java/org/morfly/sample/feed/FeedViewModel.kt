package org.morfly.sample.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.morfly.sample.imagedata.ImagesRepository
import javax.inject.Inject


class FeedViewModel @Inject constructor(
    imagesRepository: ImagesRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<FeedViewState>(FeedViewState.Idle)
    val viewState: StateFlow<FeedViewState> = _viewState

    init {
        viewModelScope.launch {
            imagesRepository.loadImages()
                .map { images -> FeedViewState.Loaded(images) }
                .onStart { _viewState.value = FeedViewState.Loading(viewState.value.images) }
                .collect { state -> _viewState.value = state }
        }
    }
}