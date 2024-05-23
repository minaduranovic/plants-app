package ba.unsa.etf.rma.projekat

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
        @GET("plants/search")
        suspend fun searchPlants(
                @Query("q") query: String?,
                @Query("token") token: String = BuildConfig.TREFLE_TOKEN
        ): Response<GetBiljkaResponse>

}