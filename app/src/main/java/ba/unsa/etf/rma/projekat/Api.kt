package ba.unsa.etf.rma.projekat

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {
        @GET("plants/search")
        suspend fun searchPlants(
                @Query("q") query: String?,
                @Query("token") token: String = BuildConfig.TREFLE_TOKEN
        ): Response<GetBiljkaSearchResponse>

        @GET("plants/search")
        suspend fun searchPlants(
                @Query("q") query: String,
                @QueryMap filters: Map<String, String>,
                @Query("token") token: String = BuildConfig.TREFLE_TOKEN
        ): Response<GetBiljkaSearchResponse>

        @GET("plants/{id}")
        suspend fun getPlant(
                @Path("id") plantId: Int,
                @Query("token") token: String = BuildConfig.TREFLE_TOKEN
        ): Response<GetBiljkaRetrieveResponse>

//        @GET("plants")
//        suspend fun getPlantsByFlowerColor(
//                @Query("filter[flower_color]") flowerColor: String,
//                @Query("token") token: String = BuildConfig.TREFLE_TOKEN
//        ): Response<GetBiljkaSearchResponse>
}