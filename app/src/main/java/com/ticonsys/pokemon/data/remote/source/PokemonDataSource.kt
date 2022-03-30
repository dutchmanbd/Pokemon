package com.ticonsys.pokemon.data.remote.source

import com.ticonsys.pokemon.data.remote.responses.ApiResponse
import com.ticonsys.pokemon.data.remote.responses.Pokemon
import com.ticonsys.pokemon.data.remote.responses.PokemonList

interface PokemonDataSource {
    suspend fun getPokemonList(limit: Int, offset: Int): ApiResponse<PokemonList>
    suspend fun getPokemon(name: String): ApiResponse<Pokemon>
}