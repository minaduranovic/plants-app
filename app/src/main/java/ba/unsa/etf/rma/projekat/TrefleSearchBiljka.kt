package ba.unsa.etf.rma.projekat

import com.google.gson.annotations.SerializedName

data class TrefleSearchBiljka(
    @SerializedName("id") val id: Int,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("family") val family: String?,
    @SerializedName("family_common_name") val familyCommonName: String?,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("image_url") val imageUrl: String?
)