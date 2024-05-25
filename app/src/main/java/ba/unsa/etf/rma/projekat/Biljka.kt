package ba.unsa.etf.rma.projekat

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Biljka(
    @SerializedName("common_name") val naziv: String?,
    @SerializedName("family") var porodica: String?,
    @SerializedName("medicinskoUpozorenje") var medicinskoUpozorenje: String?,
    @SerializedName("medicinskeKoristi") val medicinskeKoristi: List<MedicinskaKorist>,
    @SerializedName("profilOkusa") val profilOkusa: ProfilOkusaBiljke?,
    @SerializedName("jela") var jela: List<String>,
    @SerializedName("klimatskiTipovi") var klimatskiTipovi: List<KlimatskiTip>,
    @SerializedName("zemljisniTipovi") var zemljisniTipovi: List<Zemljiste>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()?.map { MedicinskaKorist.valueOf(it) } ?: emptyList(),
        parcel.readSerializable() as ProfilOkusaBiljke,
        parcel.createStringArrayList() ?: emptyList(),
        parcel.createStringArrayList()?.map { KlimatskiTip.valueOf(it) } ?: emptyList(),
        parcel.createStringArrayList()?.map { Zemljiste.valueOf(it) } ?: emptyList()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(naziv)
        dest.writeString(porodica)
        dest.writeString(medicinskoUpozorenje)
        dest.writeStringList(medicinskeKoristi.map { it.name })
        dest.writeSerializable(profilOkusa)
        dest.writeStringList(jela)
        dest.writeStringList(klimatskiTipovi.map { it.name })
        dest.writeStringList(zemljisniTipovi.map { it.name })
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