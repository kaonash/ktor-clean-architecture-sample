package service

import dto.UserDto
import repository.IUserRepository

interface UserService {
    fun findById(userId: Long): UserDto
}

class UserServiceImpl(
    private val userRepository: IUserRepository
) :
    UserService {
    override fun findById(userId: Long): UserDto {
        return userRepository.findById(userId) ?: throw IllegalStateException("No User Found for Given Id")
    }
}

