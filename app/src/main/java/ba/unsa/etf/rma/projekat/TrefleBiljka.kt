package ba.unsa.etf.rma.projekat

import com.google.gson.annotations.SerializedName

data class TrefleBiljka(
    @SerializedName("id") val id: Int,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("image_url") val imageUrl: String?
)