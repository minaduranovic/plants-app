package ba.unsa.etf.rma.projekat

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.BeforeClass
import org.junit.Test

class DatabaseTest {
    companion object {
        lateinit var db: SupportSQLiteDatabase
        lateinit var context: Context
        lateinit var roomDb: BiljkaDatabase
        lateinit var biljkaDAO: BiljkaDatabase.BiljkaDAO

        @BeforeClass
        @JvmStatic
        fun napraviBazu() = runBlocking {
            context = ApplicationProvider.getApplicationContext()
            roomDb = Room.inMemoryDatabaseBuilder(context, BiljkaDatabase::class.java).build()
            biljkaDAO = roomDb.biljkaDao()
            biljkaDAO.getAllBiljkas()
            db = roomDb.openHelper.readableDatabase

        }
    }

    @Test
    fun isprazniBazuIProvjeri() = runBlocking {
        DatabaseTest.biljkaDAO.clearData()
        val allBiljkas = biljkaDAO.getAllBiljkas()
        assertTrue(allBiljkas.isEmpty())
    }
    @Test
    fun dodajBiljkuIProvjeri() = runBlocking {
        DatabaseTest.biljkaDAO.saveBiljka(
            Biljka(
                naziv = "Peršun (Petroselinum crispum)",
                porodica = "Apiaceae (štitarka)",
                medicinskoUpozorenje = "Osobe koje uzimaju lijekove za razrjeđivanje krvi trebaju izbjegavati konzumaciju peršuna u velikim količinama.",
                medicinskeKoristi = listOf(
                    MedicinskaKorist.PODRSKAIMUNITETU,
                    MedicinskaKorist.PROTIVBOLOVA
                ),
                profilOkusa = ProfilOkusaBiljke.AROMATICNO,
                jela = listOf("Čaj od peršuna", "Supa sa peršunom"),
                klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA),
                zemljisniTipovi = listOf(Zemljiste.ILOVACA, Zemljiste.KRECNJACKO)
            )
        )

        ViewMatchers.assertThat(DatabaseTest.biljkaDAO.getAllBiljkas().size, CoreMatchers.`is`(1))
    }



}