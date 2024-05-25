package ba.unsa.etf.rma.projekat

import com.google.gson.annotations.SerializedName

data class GetBiljkaRetrieveResponse (
    @SerializedName("data") val data: TrefleRetrieveBiljka
     )
