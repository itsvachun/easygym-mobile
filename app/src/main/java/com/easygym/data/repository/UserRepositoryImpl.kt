package com.easygym.data.repository

import com.easygym.data.local.dao.UserDAO
import com.easygym.data.remote.datasource.UserDataSource
import com.easygym.data.remote.model.user.UpdatePasswordRequest
import com.easygym.domain.model.User
import com.easygym.domain.repository.UserRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl @Inject constructor(
    private val userDAO: UserDAO,
    private val userDataSource: UserDataSource
) : UserRepository {

    override val users: Flow<List<User>> = userDAO.getAll().map { users -> users.map { it.toDomain() } }

    override suspend fun fetch(search: String, page: Int): Result<Boolean> =
        runCatching {
            val response = userDataSource.fetch(search, page)
            val entities = response.content.map { it.toEntity() }
            userDAO.insertAll(entities)
            response.last
        }


    override suspend fun updatePassword(request: UpdatePasswordRequest): Result<Unit> =
        runCatching<UserRepositoryImpl, Unit> {
            userDataSource.updatePassword(request = request)
        }
            .onSuccess { Result.success(Unit) }
            .onFailure { Result.failure<Exception>(it) }
}
