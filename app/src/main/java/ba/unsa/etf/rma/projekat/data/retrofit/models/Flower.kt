package ba.unsa.etf.rma.projekat.data.retrofit.models

import com.google.gson.annotations.SerializedName

data class Flower (
    @SerializedName("color") val colors: List<String>
)