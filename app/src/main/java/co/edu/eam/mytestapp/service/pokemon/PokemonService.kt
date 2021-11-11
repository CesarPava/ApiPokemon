package co.edu.eam.mytestapp.service.pokemon

import co.edu.eam.mytestapp.model.pokemon.Pokemon
import co.edu.eam.mytestapp.model.pokemon.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://pokeapi.co/api/v2/"

interface PokemonService {

    @GET("pokemon")
    fun getPokemon(@Query("limit") limit:Int ): Call<PokemonResponse>

    @GET("pokemon/{number}/")
    fun getPokemonByNumber(@Path("number") number:Int): Call<Pokemon>
}