package com.ticonsys.pokemon.ui.pokemon.list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.ticonsys.pokemon.data.models.PokedexListEntry
import com.ticonsys.pokemon.data.remote.middleware.Resource
import com.ticonsys.pokemon.data.repositories.PokemonRepository
import com.ticonsys.pokemon.internal.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {


    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadingError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)


    init {
        loadPokemonPaginated()
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            when (val resource =
                repository.getPokemonList(Constant.PAGE_SIZE, curPage * Constant.PAGE_SIZE)) {
                is Resource.Failure -> {
                    isLoading.value = false
                    loadingError.value = resource.message
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
                is Resource.Success -> {

                    endReached.value = curPage * Constant.PAGE_SIZE >= resource.data.count
                    val pokedexEntries = resource.data.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val imageUrl =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(
                            pokemonName = entry.name.replaceFirstChar {
                                it.uppercase(Locale.ROOT)
                            },
                            imageUrl = imageUrl,
                            number = number.toInt()
                        )
                    }

                    curPage++
                    loadingError.value = ""
                    isLoading.value = false
                    pokemonList.value = pokedexEntries
                }
            }
        }
    }


    fun calcDominantColor(drawable: Drawable, onFinish: (color: Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

}