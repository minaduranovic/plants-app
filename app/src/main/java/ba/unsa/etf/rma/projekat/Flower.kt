package ba.unsa.etf.rma.projekat

import com.google.gson.annotations.SerializedName

data class Flower (
    @SerializedName("color") val colors: List<String>
)