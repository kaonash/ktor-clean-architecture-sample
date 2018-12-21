package routes

import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.server.testing.TestApplicationRequest
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import main
import org.koin.dsl.module.Module
import org.koin.standalone.StandAloneContext
import org.koin.test.KoinTest

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
abstract class BaseRouteTest : KoinTest {
    fun routeTest(
        httpMethod: HttpMethod, uri: String, koinModules: List<Module>,
        mockSettingBlock: () -> Unit,
        verifyBlock: () -> Unit,
        requestSetupBlock: TestApplicationRequest.() -> Unit = {},
        assertBlock: (response: TestApplicationResponse) -> Unit
    ) {
        withTestApplication(Application::main) {
            StandAloneContext.stopKoin()
            StandAloneContext.startKoin(koinModules)

            mockSettingBlock()

            with(handleRequest(httpMethod, uri, requestSetupBlock)) {
                assertBlock(response)
            }

            verifyBlock()

            StandAloneContext.stopKoin()
        }
    }
}