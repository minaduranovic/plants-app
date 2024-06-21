package ba.unsa.etf.rma.projekat.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

enum class Zemljiste(val naziv: String) : Parcelable {
    @SerializedName("PJESKOVITO")
    PJESKOVITO("Pjeskovito zemljište"),

    @SerializedName("GLINENO")
    GLINENO("Glinеno zemljište"),

    @SerializedName("ILOVACA")
    ILOVACA("Ilovača"),

    @SerializedName("CRNICA")
    CRNICA("Crnica"),

    @SerializedName("SLJUNKOVITO")
    SLJUNKOVITO("Šljunovito zemljište"),

    @SerializedName("KRECNJACKO")
    KRECNJACKO("Krečnjačko zemljište");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Zemljiste> {
        override fun createFromParcel(parcel: Parcel): Zemljiste {
            return valueOf(parcel.readString()!!)
        }

        override fun newArray(size: Int): Array<Zemljiste?> {
            return arrayOfNulls(size)
        }
    }
}
