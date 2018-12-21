package repository

import dto.UserDto

class UserRepository : IUserRepository {
    override fun findById(userId: Long): UserDto? {
        return UserDto(1, "Test", "Taro")
    }
}

