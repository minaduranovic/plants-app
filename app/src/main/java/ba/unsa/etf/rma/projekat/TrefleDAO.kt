package ba.unsa.etf.rma.projekat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class TrefleDAO {


    private var defaultBitmap: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888).apply {
        eraseColor(android.graphics.Color.WHITE) }

         suspend fun getImage(biljka: Biljka): Bitmap = withContext(Dispatchers.IO) {
            try {
                val latinskiNaziv= latinski(biljka.naziv.toString())
                val response = ApiAdapter.retrofit.searchPlants(latinskiNaziv)
                if (response.isSuccessful) {
                    val plants = response.body()?.plants
                    if (!plants.isNullOrEmpty()) {
                        val imageUrl = plants[0].imageUrl
                        if (!imageUrl.isNullOrEmpty()) {
                            val url = URL(imageUrl)
                            return@withContext BitmapFactory.decodeStream(url.openStream())
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return@withContext defaultBitmap
        }


    private fun latinski(naziv: String): String? {
        val pocetak = naziv.indexOf('(')
        if (pocetak == -1) {
            return null
        }
        val kraj = naziv.indexOf(')', pocetak)
        if (kraj == -1) {
            return null
        }
        return naziv.substring(pocetak + 1, kraj)
    }
}