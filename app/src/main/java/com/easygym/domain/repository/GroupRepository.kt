package com.easygym.domain.repository

import com.easygym.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {

    val groups: Flow<List<Group>>

    suspend fun getAll(): List<Group>

}