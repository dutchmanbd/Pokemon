package com.ticonsys.pokemon.data.remote.source

import com.ticonsys.pokemon.data.remote.PokeApiService
import com.ticonsys.pokemon.data.remote.middleware.SafeApiRequest

class PokemonDataSourceImpl(
    private val apiService: PokeApiService
) : SafeApiRequest(), PokemonDataSource {
    override suspend fun getPokemonList(limit: Int, offset: Int) = apiRequest {
        apiService.getPokemonList(limit, offset)
    }

    override suspend fun getPokemon(name: String) = apiRequest {
        apiService.getPokemon(name)
    }
}