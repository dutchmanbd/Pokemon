package com.ticonsys.pokemon.data.remote.responses

import com.google.gson.annotations.SerializedName

data class Versions(
    @SerializedName("generation-i")
    val generation1: GenerationI,
    @SerializedName("generation-ii")
    val generation2: GenerationIi,
    @SerializedName("generation-iii")
    val generation3: GenerationIii,
    @SerializedName("generation-iv")
    val generation4: GenerationIv,
    @SerializedName("generation-v")
    val generation5: GenerationV,
    @SerializedName("generation-vi")
    val generation6: GenerationVi,
    @SerializedName("generation-vii")
    val generation7: GenerationVii,
    @SerializedName("generation-viii")
    val generation8: GenerationViii
)