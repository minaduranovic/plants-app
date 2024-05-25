package ba.unsa.etf.rma.projekat

import com.google.gson.annotations.SerializedName

data class GetBiljkaSearchResponse(
    @SerializedName("data") val data: List<TrefleSearchBiljka>
)