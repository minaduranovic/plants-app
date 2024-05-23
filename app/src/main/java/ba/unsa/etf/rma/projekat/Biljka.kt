package ba.unsa.etf.rma.projekat

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Biljka(
    @SerializedName("naziv") val naziv: String,
    @SerializedName("porodica") val porodica: String,
    @SerializedName("medicinskoUpozorenje") val medicinskoUpozorenje: String,
    @SerializedName("medicinskeKoristi") val medicinskeKoristi: List<MedicinskaKorist>,
    @SerializedName("profilOkusa") val profilOkusa: ProfilOkusaBiljke,
    @SerializedName("jela") val jela: List<String>,
    @SerializedName("klimatskiTipovi") val klimatskiTipovi: List<KlimatskiTip>,
    @SerializedName("zemljisniTipovi") val zemljisniTipovi: List<Zemljiste>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(MedicinskaKorist)!!,
        parcel.readParcelable(ProfilOkusaBiljke::class.java.classLoader)!!,
        parcel.createStringArrayList()!!,
        parcel.createTypedArrayList(KlimatskiTip)!!,
        parcel.createTypedArrayList(Zemljiste)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(naziv)
        parcel.writeString(porodica)
        parcel.writeString(medicinskoUpozorenje)
        parcel.writeTypedList(medicinskeKoristi)
        parcel.writeParcelable(profilOkusa, flags)
        parcel.writeStringList(jela)
        parcel.writeTypedList(klimatskiTipovi)
        parcel.writeTypedList(zemljisniTipovi)
    }

    override fun describeContents(): Int {
        return 0
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
