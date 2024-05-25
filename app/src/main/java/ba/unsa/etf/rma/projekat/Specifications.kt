package ba.unsa.etf.rma.projekat

import com.google.gson.annotations.SerializedName

data class Specifications(
    @SerializedName("toxicity") val toxicity: String?,
)
