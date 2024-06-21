package ba.unsa.etf.rma.projekat.data.retrofit.models

import com.google.gson.annotations.SerializedName

data class GetBiljkaRetrieveResponse (
    @SerializedName("data") val data: TrefleRetrieveBiljka
     )
