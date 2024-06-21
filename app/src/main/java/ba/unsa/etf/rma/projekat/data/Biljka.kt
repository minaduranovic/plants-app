package ba.unsa.etf.rma.projekat.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ba.unsa.etf.rma.projekat.data.room.Converters

@Entity(tableName = "biljka")
data class Biljka(
    @ColumnInfo("naziv") val naziv: String,
    @PrimaryKey (autoGenerate = true) val id: Long?=null,
    @ColumnInfo("porodica") var porodica: String,
    @ColumnInfo("medicinskoUpozorenje") var medicinskoUpozorenje: String?,
    @ColumnInfo("medicinskeKoristi") @TypeConverters(Converters::class) val medicinskeKoristi: List<MedicinskaKorist>,
    @ColumnInfo("profilOkusa") val profilOkusa: ProfilOkusaBiljke?,
    @ColumnInfo("jela") @TypeConverters(Converters::class) var jela: List<String>,
    @ColumnInfo("klimatskiTipovi") @TypeConverters(Converters::class) var klimatskiTipovi: List<KlimatskiTip>,
    @ColumnInfo("zemljisniTipovi") @TypeConverters(Converters::class) var zemljisniTipovi: List<Zemljiste>,
    @ColumnInfo("onlineChecked") var onlineChecked: Boolean = false
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readValue(Long::class.java.classLoader) as Long?,
        parcel.readString().toString(),
        parcel.readString(),
        parcel.createTypedArrayList(MedicinskaKorist) ?: emptyList(),
        parcel.readParcelable(ProfilOkusaBiljke::class.java.classLoader),
        parcel.createStringArrayList() ?: emptyList(),
        parcel.createTypedArrayList(KlimatskiTip) ?: emptyList(),
        parcel.createTypedArrayList(Zemljiste) ?: emptyList(),
        parcel.readByte() != 0.toByte()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(naziv)
        dest.writeValue(id)
        dest.writeString(porodica)
        dest.writeString(medicinskoUpozorenje)
        dest.writeTypedList(medicinskeKoristi)
        dest.writeParcelable(profilOkusa, flags)
        dest.writeStringList(jela)
        dest.writeTypedList(klimatskiTipovi)
        dest.writeTypedList(zemljisniTipovi)
        dest.writeByte(if (onlineChecked) 1 else 0)
    }


    companion object CREATOR : Parcelable.Creator<Biljka> {
        override fun createFromParcel(parcel: Parcel): Biljka {
            return Biljka(parcel)
        }

        override fun newArray(size: Int): Array<Biljka?> {
            return arrayOfNulls(size)
        }
    }
}