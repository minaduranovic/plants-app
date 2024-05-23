package ba.unsa.etf.rma.projekat

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

enum class ProfilOkusaBiljke(val opis: String) : Parcelable {
    @SerializedName("MENTA")
    MENTA("Mentol - osvježavajući, hladan ukus"),

    @SerializedName("CITRUSNI")
    CITRUSNI("Citrusni - osvježavajući, aromatičan"),

    @SerializedName("SLATKI")
    SLATKI("Sladak okus"),

    @SerializedName("BEZUKUSNO")
    BEZUKUSNO("Obični biljni okus - travnat, zemljast ukus"),

    @SerializedName("LJUTO")
    LJUTO("Ljuto ili papreno"),

    @SerializedName("KORIJENASTO")
    KORIJENASTO("Korenast - drvenast i gorak ukus"),

    @SerializedName("AROMATICNO")
    AROMATICNO("Začinski - topli i aromatičan ukus"),

    @SerializedName("GORKO")
    GORKO("Gorak okus");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfilOkusaBiljke> {
        override fun createFromParcel(parcel: Parcel): ProfilOkusaBiljke {
            return valueOf(parcel.readString()!!)
        }

        override fun newArray(size: Int): Array<ProfilOkusaBiljke?> {
            return arrayOfNulls(size)
        }
    }
}
