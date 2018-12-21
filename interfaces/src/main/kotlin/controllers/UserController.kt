package controllers

import dto.UserDto
import service.UserService

class UserController(private val userService: UserService) {
    fun getUser(userId: Long): UserResponse {
        return userService.findById(userId).toResponse()
    }
}

data class UserResponse(var userId: Long, var familyName: String, var givenName: String)

private fun UserDto.toResponse() = UserResponse(id, familyName, givenName)

