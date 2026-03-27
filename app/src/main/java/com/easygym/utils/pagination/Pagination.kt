package com.easygym.utils.pagination

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PaginationState<T>(
    val isLoading: Boolean = false,
    val isFetchingNextPage: Boolean = false,
    val isLastPage: Boolean = false,
    val search: String = "",
    val items: List<T> = listOf(),
    val errorMessage: String? = null
)

@OptIn(FlowPreview::class)
class PaginationHandler<T : Searchable>(
    private val scope: CoroutineScope,
    private val fetchItems: suspend (query: String, page: Int) -> Result<Boolean>,
    private val localItemsFlow: Flow<List<T>>,
    private val extraFilter: Flow<(T) -> Boolean> = flowOf { true },
    private val onStateUpdate: (PaginationState<T>) -> Unit
) {
    private var _state = PaginationState<T>()
    private var currentPage = 0
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

                filteredBySearch
                    .sortedWith(
                        comparator = compareBy<T> { it.firstName.lowercase() }
                            .thenBy { it.lastName.lowercase() }
                    )
                    .filter(filter)
            }.collect { filteredItems ->
                updateState { it.copy(items = filteredItems, isLoading = false) }
            }
        }

        searchFlow
            .debounce(500)
            .onEach { resetAndFetch() }
            .launchIn(scope)
    }

    fun onSearchChanged(query: String) {
        searchFlow.value = query
        updateState { it.copy(search = query) }
    }

    private fun resetAndFetch() {
        currentPage = 0
        fetchJob?.cancel()
        updateState { it.copy(errorMessage = null, isLastPage = false) }
        loadNextPage()
    }

    fun loadNextPage() {
        if (_state.isLastPage || _state.isFetchingNextPage) return

        fetchJob = scope.launch {
            updateState { it.copy(isFetchingNextPage = true, errorMessage = null) }

            val result = fetchItems(_state.search, currentPage)

            result.onSuccess { last ->
                updateState { it.copy(isLastPage = last) }
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
