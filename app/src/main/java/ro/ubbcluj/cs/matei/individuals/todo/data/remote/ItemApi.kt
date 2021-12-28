package ro.ubbcluj.cs.matei.individuals.todo.data.remote

import retrofit2.http.*
import ro.ubbcluj.cs.matei.individuals.core.Api
import ro.ubbcluj.cs.matei.individuals.todo.data.Movie

object ItemApi {
    interface Service {
        @GET("/api/movie")
        suspend fun find(): List<Movie>

        @GET("/api/movie/{id}")
        suspend fun read(@Path("id") itemId: String): Movie;

        @Headers("Content-Type: application/json")
        @POST("/api/movie")
        suspend fun create(@Body movie: Movie): Movie

        @Headers("Content-Type: application/json")
        @PUT("/api/movie/{id}")
        suspend fun update(@Path("id") itemId: String, @Body movie: Movie): Movie
    }

    val service: Service = Api.retrofit.create(Service::class.java)
}