package ba.unsa.etf.rma.projekat.data.room

import androidx.room.TypeConverter
import ba.unsa.etf.rma.projekat.data.KlimatskiTip
import ba.unsa.etf.rma.projekat.data.MedicinskaKorist
import ba.unsa.etf.rma.projekat.data.Zemljiste
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    @TypeConverter
    fun fromMedicinskaKorist(value: MedicinskaKorist?): String? {
        return value?.name
    }

    @TypeConverter
    fun toMedicinskaKorist(value: String?): MedicinskaKorist? {
        return value?.let { MedicinskaKorist.valueOf(it) }
    }

    @TypeConverter
    fun fromMedicinskaKoristList(value: List<MedicinskaKorist>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<MedicinskaKorist>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toMedicinskaKoristList(value: String): List<MedicinskaKorist>? {
        val gson = Gson()
        val type = object : TypeToken<List<MedicinskaKorist>>() {}.type
        return gson.fromJson(value, type)
    }
    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStringList(value: String): List<String>? {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromKlimatskiTip(value: KlimatskiTip?): String? {
        return value?.name
    }

    @TypeConverter
    fun toKlimatskiTip(value: String?): KlimatskiTip? {
        return value?.let { KlimatskiTip.valueOf(it) }
    }

    @TypeConverter
    fun fromKlimatskiTipList(value: List<KlimatskiTip>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<KlimatskiTip>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toKlimatskiTipList(value: String): List<KlimatskiTip>? {
        val gson = Gson()
        val type = object : TypeToken<List<KlimatskiTip>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromZemljiste(value: Zemljiste?): String? {
        return value?.name
    }

    @TypeConverter
    fun toZemljiste(value: String?): Zemljiste? {
        return value?.let { Zemljiste.valueOf(it) }
    }

    @TypeConverter
    fun fromZemljisteList(value: List<Zemljiste>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Zemljiste>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toZemljisteList(value: String): List<Zemljiste>? {
        val gson = Gson()
        val type = object : TypeToken<List<Zemljiste>>() {}.type
        return gson.fromJson(value, type)
    }
}
