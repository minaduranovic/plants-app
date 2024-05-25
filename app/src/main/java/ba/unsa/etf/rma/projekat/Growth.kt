package ba.unsa.etf.rma.projekat

import com.google.gson.annotations.SerializedName

data class Growth(
    @SerializedName("soil_texture") val soilTexture: Int?,
    @SerializedName("light") val light: Int?,
    @SerializedName("atmospheric_humidity") val atmosphericHumidity: Int?
)