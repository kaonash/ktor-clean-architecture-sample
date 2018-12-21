package routes

import controllers.UserController
import controllers.UserResponse
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import mapper.ObjectMapperBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.koin.dsl.module.module

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
class UserRouteTest : BaseRouteTest() {

    private val userController = mockk<UserController>(relaxed = true)
    private val modules = listOf(
        module { single { userController } }
    )

    private val objectMapper = ObjectMapperBuilder.build()

    @Test
    fun `Return user data when call GET request with user id`() {
        val mockSettingBlock: () -> Unit = {
            every { runBlocking { userController.getUser(1010) } } returns UserResponse(
                1010, "Test", "Taro"
            )
        }

        val verifyBlock: () -> Unit = {
            verify(exactly = 1) { runBlocking { userController.getUser(1010) } }
        }

        routeTest(HttpMethod.Get, "/v1/users/1010", modules, mockSettingBlock, verifyBlock) { response ->
            val expect = UserResponse(1010, "Test", "Taro")

            assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
            assertThat(objectMapper.readValue(response.content, UserResponse::class.java)).isEqualTo(
                expect
            )
        }
    }
}
