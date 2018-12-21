package module

import controllers.UserController
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import repository.IUserRepository
import repository.UserRepository
import service.UserService
import service.UserServiceImpl

object KoinModuleBuilder {
    fun modules(): List<Module> = listOf(module {
        // Controllers
        single { UserController(get()) }

        // Services
        single<UserService> { UserServiceImpl(get()) }

        // Repositories
        single<IUserRepository> { UserRepository() }
    })
}
