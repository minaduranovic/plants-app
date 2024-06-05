package ba.unsa.etf.rma.projekat

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

class TrefleDAO {


//    private var defaultBitmap: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888).apply {
//        eraseColor(android.graphics.Color.WHITE) }

  private  lateinit var context: Context

    fun setContext(context: Context) {
        this.context = context
    }

    private fun getDefaultBitmap(): Bitmap {
        return BitmapFactory.decodeResource(context.resources, R.drawable.plant)
    }
         suspend fun getImage(biljka: Biljka): Bitmap = withContext(Dispatchers.IO) {
            try {
                val latinskiNaziv= latinski(biljka.naziv.toString())
                val response = ApiAdapter.retrofit.searchPlants(latinskiNaziv)
                if (response.isSuccessful) {
                    val plants = response.body()?.data
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
            return@withContext getDefaultBitmap()
        }

    suspend fun fixData(biljka: Biljka): Biljka = withContext(Dispatchers.IO) {
        try {
            val latinskiNaziv= latinski(biljka.naziv.toString())
            val searchResponse = ApiAdapter.retrofit.searchPlants(latinskiNaziv)
            if (searchResponse.isSuccessful) {
                val plants = searchResponse.body()?.data
//                Log.d("TrefleDAO", "searchResponse: ${biljka.naziv}")

                if (!plants.isNullOrEmpty()) {
//                    Log.d("TrefleDAO", "nullor empty: ${biljka.naziv}")

                    val trefleSearchPlant = plants[0]
                    val plantId = trefleSearchPlant.id
                    val retrieveResponse = ApiAdapter.retrofit.getPlant(plantId)
                    if (retrieveResponse.isSuccessful) {
                        val plant = retrieveResponse.body()?.data
                        if (plant != null) {
//                            Log.d("TrefleDAO", "prije ${biljka.porodica} to ${plant.mainSpecies?.family}")
                            updateFamilyName(biljka, plant)
//                            Log.d("TrefleDAO", "poslije: ${biljka.porodica}")
                            updateEdibleStatus(biljka, plant)
                            updateMedicalWarning(biljka, plant)
                            updateSoilTextures(biljka, plant)
                            updateClimateTypes(biljka, plant)
                        }
                    } else {
//                        Log.d("TrefleDAO", "neuspjesno: ${biljka.naziv}")

                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        Log.d("TrefleDAO", "fixData finished for biljka: ${biljka.naziv}")
        return@withContext biljka
    }


    suspend fun getPlantsWithFlowerColor(flowerColor: String, substr: String): List<Biljka> {
        return withContext(Dispatchers.IO) {
            var ids=  mutableListOf<Int>()
            val filteredPlants = mutableListOf<TrefleRetrieveBiljka>()
            val response = ApiAdapter.retrofit.searchPlants(substr)
            if (response.isSuccessful) {
                val plants = response.body()?.data
//                Log.d("TrefleDAO", "Response velicina liste: ${plants?.size}")
                if (!plants.isNullOrEmpty()){
                    for (p in plants){
                        ids.add(p.id)
                    }
                }
                for (i in ids){
                    val responsePlant= ApiAdapter.retrofit.getPlant(i)
                    if (responsePlant.isSuccessful){
                        val boje = responsePlant.body()?.data?.mainSpecies?.flower?.colors
                        val biljka = responsePlant.body()?.data
//                        Log.d("TrefleDAO", "filtrirana biljka : commonName=${biljka?.commonName}, scientificName=${biljka?.scientificName}")
//                        Log.d("TrefleDAO", "boje${boje?.size}")
//
                        if (boje!=null && boje.contains(flowerColor) && (biljka?.commonName?.contains(substr, ignoreCase = true) == true || biljka?.scientificName?.contains(substr, ignoreCase = true)==true) ){
                            filteredPlants+= biljka
                        }
                    }
                }
                if (filteredPlants != null) {
//                    plants.forEach { plant ->
//                        Log.d("TrefleDAO", " biljka: commonName=${plant.commonName}, scientificName=${plant.scientificName}")
//                    }
//                    it.commonName?.contains(substr, ignoreCase = true) == true || izbaceno odozdo
//                    val filteredPlants = plants.filter {
//                                it.scientificName?.contains(substr, ignoreCase = true) == true || it.commonName?.contains(substr, ignoreCase = true) == true
//                    }

//                    Log.d("TrefleDAO", "filtrirano velicina: ${filteredPlants.size}")
//                    filteredPlants.forEach { plant ->
////                        Log.d("TrefleDAO", "filtrirana biljka : commonName=${plant.commonName}, scientificName=${plant.scientificName}")
//                    }

                    return@withContext filteredPlants.map { plant ->
                        Biljka(
                            naziv = (plant.commonName + " (" + plant.scientificName + ")") ?: "",
                            porodica = plant.mainSpecies?.family ?: "",
                            medicinskoUpozorenje = null,
                            medicinskeKoristi = listOf(),
                            profilOkusa = null,
                             jela = emptyList(),
                            klimatskiTipovi = listOf(),
                            zemljisniTipovi = listOf()
                        )
                    }
                } else {
//                    Log.d("TrefleDAO", "nema biljaka")
                    return@withContext emptyList<Biljka>()
                }
            } else {
//                Log.d("TrefleDAO", "neuspesno: ${response.errorBody()}")
                return@withContext emptyList<Biljka>()
            }
        }
    }



    private fun updateFamilyName(biljka: Biljka, plant: TrefleRetrieveBiljka) {
        val trefleFam = plant.mainSpecies?.family
        if (trefleFam != null && biljka.porodica != trefleFam) {
            biljka.porodica = trefleFam
        }
    }

    private fun updateEdibleStatus(biljka: Biljka, plant: TrefleRetrieveBiljka) {
        plant.mainSpecies?.edible.let {
          if (it==false){
              biljka.jela= emptyList()
              if (!biljka.medicinskoUpozorenje?.contains("NIJE JESTIVO")!!) {
                  biljka.medicinskoUpozorenje = (biljka.medicinskoUpozorenje ?: "") + " NIJE JESTIVO"
              }
          }
        }
    }

    private fun updateMedicalWarning(biljka: Biljka, plant: TrefleRetrieveBiljka) {
        plant.mainSpecies?.specifications?.toxicity?.let {
            if (it != "none" && !biljka.medicinskoUpozorenje?.contains("TOKSIČNO")!!) {
                biljka.medicinskoUpozorenje = (biljka.medicinskoUpozorenje ?: "") + " TOKSIČNO"
            }
        }
    }

    private fun updateSoilTextures(biljka: Biljka, plant: TrefleRetrieveBiljka) {
        val soilTexture = plant.mainSpecies?.growth?.soilTexture ?: return
        val updatedZemljisniTipovi = mutableListOf<Zemljiste>()

        for (zemljiste in biljka.zemljisniTipovi) {
            val texture = zemljiste.toString()
            when (texture) {
                "GLINENO" -> if (soilTexture in 1..2) updatedZemljisniTipovi.add(zemljiste)
                "PJESKOVITO" -> if (soilTexture in 3..4) updatedZemljisniTipovi.add(zemljiste)
                "ILOVACA"  -> if (soilTexture in 5..6) updatedZemljisniTipovi.add(zemljiste)
                "CRNICA" -> if (soilTexture in 7..8) updatedZemljisniTipovi.add(zemljiste)
                "SLJUNKOVITO"-> if (soilTexture == 9) updatedZemljisniTipovi.add(zemljiste)
                "KRECNJACKO" -> if (soilTexture == 10) updatedZemljisniTipovi.add(zemljiste)
            }
        }
        biljka.zemljisniTipovi = emptyList()
        biljka.zemljisniTipovi = updatedZemljisniTipovi
    }


    private fun updateClimateTypes(biljka: Biljka, plant: TrefleRetrieveBiljka) {
        val light = plant.mainSpecies?.growth?.light
        val atmosphericHumidity = plant.mainSpecies?.growth?.atmosphericHumidity
        val updatedClimateTypes = mutableListOf<KlimatskiTip>()

        for (tip in biljka.klimatskiTipovi) {
            val type=tip.toString()
            when (type) {
                "SREDOZEMNA" -> if (light in 6..9 && atmosphericHumidity in 1..5)  updatedClimateTypes.add(tip)
                "TROPSKA" ->  if (light in 8..10 && atmosphericHumidity in 7..10) updatedClimateTypes.add(tip)
                "SUBTROPSKA"  -> if (light in 6..9 && atmosphericHumidity in 5..8)  updatedClimateTypes.add(tip)
                "UMJERENA"-> if (light in 4..7 && atmosphericHumidity in 3..7) updatedClimateTypes.add(tip)
                "SUHA"->  if (light in 7..9 && atmosphericHumidity in 1..2) updatedClimateTypes.add(tip)
                "PLANINSKA"->if (light in 0..5 && atmosphericHumidity in 3..7) updatedClimateTypes.add(tip)
            }
        }
        if (light in 6..9 && atmosphericHumidity in 1..5)  updatedClimateTypes.add(KlimatskiTip.SREDOZEMNA)
        if (light in 8..10 && atmosphericHumidity in 7..10) updatedClimateTypes.add(KlimatskiTip.TROPSKA)
        if (light in 6..9 && atmosphericHumidity in 5..8)  updatedClimateTypes.add(KlimatskiTip.SUBTROPSKA)
        if (light in 4..7 && atmosphericHumidity in 3..7) updatedClimateTypes.add(KlimatskiTip.UMJERENA)
        if (light in 7..9 && atmosphericHumidity in 1..2) updatedClimateTypes.add(KlimatskiTip.SUHA)
       if (light in 0..5 && atmosphericHumidity in 3..7) updatedClimateTypes.add(KlimatskiTip.PLANINSKA)

        biljka.klimatskiTipovi = updatedClimateTypes
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