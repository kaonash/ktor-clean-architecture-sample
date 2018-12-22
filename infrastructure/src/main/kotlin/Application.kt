import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.KtorExperimentalAPI
import mapper.ObjectMapperBuilder
import module.KoinModuleBuilder
import org.koin.ktor.ext.installKoin
import routes.root

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun main() {
    val server = embeddedServer(Netty, port = 8082) {
        installKoin(KoinModuleBuilder.modules())

        install(ContentNegotiation) {
            jackson {
                ObjectMapperBuilder.build(this)
                configure(SerializationFeature.INDENT_OUTPUT, true)
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            }
        }
        install(StatusPages) {
            exception<Throwable> { cause ->
                log.error(cause.message, cause)
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
        install(Locations)
        install(CORS) {
            method(HttpMethod.Options)
            method(HttpMethod.Put)
            method(HttpMethod.Delete)
            anyHost()
        }

        routing {
            root()
        }
    }
    server.start(wait = true)
}

