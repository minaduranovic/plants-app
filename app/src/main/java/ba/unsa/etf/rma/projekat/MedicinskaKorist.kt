package ba.unsa.etf.rma.projekat

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

enum class MedicinskaKorist(val opis: String) : Parcelable {
    @SerializedName("SMIRENJE")
    SMIRENJE("Smirenje - za smirenje i relaksaciju"),

    @SerializedName("PROTUUPALNO")
    PROTUUPALNO("Protuupalno - za smanjenje upale"),

    @SerializedName("PROTIVBOLOVA")
    PROTIVBOLOVA("Protivbolova - za smanjenje bolova"),

    @SerializedName("REGULACIJAPRITISKA")
    REGULACIJAPRITISKA("Regulacija pritiska - za regulaciju visokog/niskog pritiska"),

    @SerializedName("REGULACIJAPROBAVE")
    REGULACIJAPROBAVE("Regulacija probave"),

    @SerializedName("PODRSKAIMUNITETU")
    PODRSKAIMUNITETU("Podrška imunitetu");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MedicinskaKorist> {
        override fun createFromParcel(parcel: Parcel): MedicinskaKorist {
            return valueOf(parcel.readString()!!)
        }

        override fun newArray(size: Int): Array<MedicinskaKorist?> {
            return arrayOfNulls(size)
        }
    }
}
