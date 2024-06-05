package ba.unsa.etf.rma.projekat

import com.google.gson.annotations.SerializedName

data class MainSpecies(
    @SerializedName("specifications") val specifications: Specifications?,
    @SerializedName("edible") val edible: Boolean,
    @SerializedName("growth") val growth: Growth?,
    @SerializedName("family") val family: String?,
    @SerializedName("flower") val flower: Flower
    )