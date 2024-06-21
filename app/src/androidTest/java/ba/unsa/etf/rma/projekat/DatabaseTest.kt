package ba.unsa.etf.rma.projekat

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import ba.unsa.etf.rma.projekat.data.Biljka
import ba.unsa.etf.rma.projekat.data.room.BiljkaDatabase
import ba.unsa.etf.rma.projekat.data.KlimatskiTip
import ba.unsa.etf.rma.projekat.data.MedicinskaKorist
import ba.unsa.etf.rma.projekat.data.ProfilOkusaBiljke
import ba.unsa.etf.rma.projekat.data.Zemljiste
import junit.framework.TestCase.assertEquals
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
        val biljka = Biljka(
            id = 0,
            naziv = "Test1 biljka",
            porodica = "Test1 porodica",
            medicinskoUpozorenje = null,
            medicinskeKoristi = listOf(),
            profilOkusa = null,
            jela = listOf(),
            klimatskiTipovi = listOf(),
            zemljisniTipovi = listOf(),
            onlineChecked = false
        )
        val uspjesno= biljkaDAO.saveBiljka(biljka)
        val allBiljkas = biljkaDAO.getAllBiljkas()
        assertTrue(uspjesno)
        assertEquals(1, allBiljkas.size)
        assertEquals(biljka.porodica, allBiljkas[0].porodica)
        assertEquals(biljka.naziv, allBiljkas[0].naziv)
    }

}