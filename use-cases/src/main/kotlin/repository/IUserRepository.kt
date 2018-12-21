package repository

import dto.UserDto

interface IUserRepository {
    fun findById(userId: Long): UserDto?
}
