package com.easygym.data.repository

import com.easygym.data.local.dao.GroupDAO
import com.easygym.data.remote.datasource.GroupDataSource
import com.easygym.domain.model.Group
import com.easygym.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupDAO: GroupDAO,
    private val groupDataSource: GroupDataSource
) : GroupRepository {

    override val groups: Flow<List<Group>> = groupDAO.getAllGroups().map { groups -> groups.map { it.toDomain() } }

    override suspend fun getAll(): List<Group> =
        runCatching {
            val response = groupDataSource.getAll()
            val entities = response.map { it.toEntity() }
            groupDAO.insertAll(entities)
            entities.map { it.toDomain() }
        }.getOrElse {
            emptyList()
        }

}