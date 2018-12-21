package dto

data class UserDto(var id: Long, var familyName: String, var givenName: String) {
    val fullName = "$familyName $givenName"
}
