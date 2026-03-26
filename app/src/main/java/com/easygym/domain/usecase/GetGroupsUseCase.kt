package com.easygym.domain.usecase

import com.easygym.domain.model.Group
import com.easygym.domain.repository.GroupRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGroupsUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(): List<Group> =
        groupRepository.getAll()
}