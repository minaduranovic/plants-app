package ba.unsa.etf.rma.projekat.data.room

import android.content.Context
import android.graphics.Bitmap
import androidx.room.*
import ba.unsa.etf.rma.projekat.TrefleDAO
import ba.unsa.etf.rma.projekat.data.Biljka

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 6)
@TypeConverters(Converters::class, BitmapConverter::class)
abstract class BiljkaDatabase: RoomDatabase() {

    @Dao
    interface BiljkaDAO {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(biljka: Biljka): Long

        @Update
        suspend fun update(biljka: Biljka)

        @Delete
        suspend fun delete(biljka: Biljka)

        @Query("SELECT * FROM biljka WHERE naziv = :naziv")
        suspend fun getByName(naziv: String): Biljka?

        @Query("SELECT * FROM biljka WHERE id = :idBiljke")
        suspend fun getById(idBiljke: Long): Biljka?

        @Query("SELECT * FROM biljka")
        suspend fun getAll(): List<Biljka>

        @Query("DELETE FROM biljka")
        suspend fun clearAll()

        @Transaction
        suspend fun saveBiljka(biljka: Biljka): Boolean {
            val id = insert(biljka)
            return id != -1L
        }

        @Transaction
        suspend fun fixOfflineBiljka(): Int {
            val offlineBiljke = getAll().filter { !it.onlineChecked }
            var updatedCount = 0
            for (biljka in offlineBiljke) {
                val original = biljka.copy()
                val trefle = TrefleDAO()
                trefle.fixData(biljka)
                if (biljka != original) {
                    update(biljka)
                    updatedCount++
                }
            }
            return updatedCount
        }

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertBitmap(biljkaBitmap: BiljkaBitmap): Long

        @Query("SELECT * FROM biljkabitmap WHERE idBiljke = :idBiljke")
        suspend fun getBitmapById(idBiljke: Long): BiljkaBitmap?

        @Query("DELETE FROM biljkabitmap")
        suspend fun clearAllBitmaps()

        @Transaction
        suspend fun getAllBiljkas(): List<Biljka> = getAll()

        @Transaction
        suspend fun clearData() {
            clearAll()
            clearAllBitmaps()
        }

        @Transaction
        suspend fun addImage(idBiljke: Long, bitmap: Bitmap): Boolean {
            val biljka = getById(idBiljke) ?: return false
            val existingBitmap = getBitmapById(idBiljke)
            if (existingBitmap != null) {
                return false
            }
            val id = insertBitmap(BiljkaBitmap(idBiljke = idBiljke, bitmap = bitmap))
            return id != -1L
        }
    }

    abstract fun biljkaDao(): BiljkaDAO

    companion object {
        @Volatile
        private var instance: BiljkaDatabase? = null

        fun getInstance(context: Context): BiljkaDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): BiljkaDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                BiljkaDatabase::class.java,
                "biljke-db"
            ).build()
        }
    }
}
