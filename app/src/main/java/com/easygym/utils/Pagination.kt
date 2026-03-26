package com.easygym.utils

import com.easygym.domain.model.Searchable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PaginationState<T>(
    val isLoading: Boolean = false,
    val isFetchingNextPage: Boolean = false,
    val search: String = "",
    val items: List<T> = listOf(),
    val errorMessage: String? = null
)

@OptIn(FlowPreview::class)
class PaginationHandler<T : Searchable>(
    private val scope: CoroutineScope,
    private val fetchItems: suspend (query: String, page: Int) -> Result<Boolean>,
    private val localItemsFlow: Flow<List<T>>,
    private val extraFilter: Flow<(T) -> Boolean> = flowOf({ true }),
    private val onStateUpdate: (PaginationState<T>) -> Unit
) {
    private var _state = PaginationState<T>()
    private var currentPage = 0
    private var isLastPage = false
    private var fetchJob: Job? = null
    private val searchFlow = MutableStateFlow("")

    init {
        updateState { it.copy(isLoading = true) }

        scope.launch {
            combine(
                localItemsFlow,
                searchFlow.asStateFlow(),
                extraFilter
            ) { items, query, filter ->
                val filteredBySearch = if (query.isBlank()) items
                else items.filter {
                    it.firstName.contains(query, ignoreCase = true) ||
                            it.lastName.contains(query, ignoreCase = true)
                }
                    .sortedWith(
                        comparator = compareBy<T> { it.firstName.lowercase() }
                            .thenBy { it.lastName.lowercase() }
                    )
                filteredBySearch.filter(filter)
            }.collect { filteredItems ->
                updateState { it.copy(items = filteredItems, isLoading = false) }
            }
        }

        searchFlow
            .debounce(1000)
            .onEach { resetAndFetch() }
            .launchIn(scope)
    }

    fun onSearchChanged(query: String) {
        searchFlow.value = query
        updateState { it.copy(search = query) }
    }

    private fun resetAndFetch() {
        currentPage = 0
        isLastPage = false
        fetchJob?.cancel()
        updateState { it.copy(errorMessage = null) }
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLastPage || _state.isFetchingNextPage) return

        fetchJob = scope.launch {
            updateState { it.copy(isFetchingNextPage = true, errorMessage = null) }

            val result = fetchItems(_state.search, currentPage)

            result.onSuccess { last ->
                isLastPage = last
                currentPage++
            }.onFailure { e ->
                updateState { it.copy(errorMessage = e.message) }
            }

            updateState { it.copy(isFetchingNextPage = false) }
        }
    }

    private fun updateState(transform: (PaginationState<T>) -> PaginationState<T>) {
        _state = transform(_state)
        onStateUpdate(_state)
    }
}
