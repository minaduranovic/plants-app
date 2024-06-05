package ba.unsa.etf.rma.projekat

import com.google.gson.annotations.SerializedName

data class TrefleRetrieveBiljka(
    @SerializedName("id") val id: Int,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("family_common_name") val family: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("main_species") val mainSpecies: MainSpecies?
)