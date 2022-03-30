package com.ticonsys.pokemon.di

import com.ticonsys.pokemon.data.remote.PokeApiService
import com.ticonsys.pokemon.data.remote.source.PokemonDataSource
import com.ticonsys.pokemon.data.remote.source.PokemonDataSourceImpl
import com.ticonsys.pokemon.data.repositories.PokemonRepository
import com.ticonsys.pokemon.data.repositories.PokemonRepositoryImpl
import com.ticonsys.pokemon.internal.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providePokemonApiService(): PokeApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .build()
            .create(PokeApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonDataSource(
        apiService: PokeApiService
    ): PokemonDataSource = PokemonDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun providePokemonRepository(
        dataSource: PokemonDataSource
    ): PokemonRepository = PokemonRepositoryImpl(dataSource)

}