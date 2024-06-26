package ba.unsa.etf.rma.projekat.data.room

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ba.unsa.etf.rma.projekat.data.Biljka

@Entity (
    foreignKeys = [ForeignKey(entity = Biljka:: class,
        parentColumns = ["id"],
        childColumns = ["idBiljke"],
        onDelete = ForeignKey. CASCADE)])
data class BiljkaBitmap(
    @PrimaryKey (autoGenerate = true) val id: Long?=null,
    @ColumnInfo(name="idBiljke") val idBiljke: Long,
    @TypeConverters (BitmapConverter:: class) val bitmap: Bitmap
)