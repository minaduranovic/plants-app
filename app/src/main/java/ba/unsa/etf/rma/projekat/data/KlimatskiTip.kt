package ba.unsa.etf.rma.projekat.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

enum class KlimatskiTip(val opis: String) : Parcelable {
    @SerializedName("SREDOZEMNA")
    SREDOZEMNA("Mediteranska klima - suha, topla ljeta i blage zime"),

    @SerializedName("TROPSKA")
    TROPSKA("Tropska klima - topla i vlažna tokom cijele godine"),

    @SerializedName("SUBTROPSKA")
    SUBTROPSKA("Subtropska klima - blage zime i topla do vruća ljeta"),

    @SerializedName("UMJERENA")
    UMJERENA("Umjerena klima - topla ljeta i hladne zime"),

    @SerializedName("SUHA")
    SUHA("Sušna klima - niske padavine i visoke temperature tokom cijele godine"),

    @SerializedName("PLANINSKA")
    PLANINSKA("Planinska klima - hladne temperature i kratke sezone rasta");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KlimatskiTip> {
        override fun createFromParcel(parcel: Parcel): KlimatskiTip {
            return valueOf(parcel.readString()!!)
        }

        override fun newArray(size: Int): Array<KlimatskiTip?> {
            return arrayOfNulls(size)
        }
    }
}
