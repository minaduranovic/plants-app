package ba.unsa.etf.rma.projekat

import com.google.gson.annotations.SerializedName

data class GetBiljkaResponse(
    @SerializedName("data") val plants: List<TrefleBiljka>
)