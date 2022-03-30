package com.ticonsys.pokemon.data.repositories

import com.ticonsys.pokemon.data.remote.middleware.Resource
import com.ticonsys.pokemon.data.remote.responses.*
import com.ticonsys.pokemon.data.remote.source.PokemonDataSource

class PokemonRepositoryImpl(
    private val dataSource: PokemonDataSource
) : PokemonRepository {
    override suspend fun getPokemonList(
        limit: Int, offset: Int
    ) = when (val response = dataSource.getPokemonList(limit, offset)) {
        is ApiEmptyResponse -> Resource.Failure(response.errorMessage, response.code)
        is ApiErrorResponse -> Resource.Failure(response.errorMessage, response.code)
        is ApiSuccessResponse -> Resource.Success(response.body)
    }

    override suspend fun getPokemon(
        name: String
    ) = when (val response = dataSource.getPokemon(name)) {
        is ApiEmptyResponse -> Resource.Failure(response.errorMessage, response.code)
        is ApiErrorResponse -> Resource.Failure(response.errorMessage, response.code)
        is ApiSuccessResponse -> Resource.Success(response.body)
    }
}