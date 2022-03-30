package com.ticonsys.pokemon.data.repositories

import com.ticonsys.pokemon.data.remote.middleware.Resource
import com.ticonsys.pokemon.data.remote.responses.Pokemon
import com.ticonsys.pokemon.data.remote.responses.PokemonList

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>
    suspend fun getPokemon(name: String): Resource<Pokemon>
}