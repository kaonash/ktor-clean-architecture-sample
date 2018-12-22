package repository

import dto.UserDto

class UserRepository : IUserRepository {
    override fun findById(userId: Long): UserDto? {
        // TODO This is mock.
        return UserDto(1, "Test", "Taro")
    }
}

