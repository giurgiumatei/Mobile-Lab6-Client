package ro.ubbcluj.cs.matei.individuals.auth.data.remote

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ro.ubbcluj.cs.matei.individuals.auth.data.TokenHolder
import ro.ubbcluj.cs.matei.individuals.auth.data.User
import ro.ubbcluj.cs.matei.individuals.core.Api
import ro.ubbcluj.cs.matei.individuals.core.Result

object RemoteAuthDataSource {
    interface AuthService {
        @Headers("Content-Type: application/json")
        @POST("/api/auth/login")
        suspend fun login(@Body user: User): TokenHolder
    }

    private val authService: AuthService = Api.retrofit.create(AuthService::class.java)

    suspend fun login(user: User): Result<TokenHolder> {
        try {
            return Result.Success(authService.login(user))
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}

