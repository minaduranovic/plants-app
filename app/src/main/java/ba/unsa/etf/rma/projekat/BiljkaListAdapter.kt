package ba.unsa.etf.rma.projekat

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import kotlin.Result

class BiljkaListAdapter(
    private var biljke: List<Biljka>,
    private val context: Context,
    private var mod: String = "Medicinski"

) : RecyclerView.Adapter<BiljkaListAdapter.BiljkaViewHolder>() {

    private val trefleDAO = TrefleDAO()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val biljkaImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val biljkaTitle: TextView = itemView.findViewById(R.id.nazivItem)
        val biljkaUpozorenje: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val biljkaKoristi: List<TextView> = listOf(
            itemView.findViewById(R.id.korist1Item),
            itemView.findViewById(R.id.korist2Item),
            itemView.findViewById(R.id.korist3Item)
        )
        val biljkaJela: List<TextView> = listOf(
            itemView.findViewById(R.id.jelo1Item),
            itemView.findViewById(R.id.jelo2Item),
            itemView.findViewById(R.id.jelo3Item)
        )
        val biljkaProfilOkusa: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val biljkaPorodica: TextView = itemView.findViewById(R.id.porodicaItem)
        val biljkaZemljisniTip: TextView = itemView.findViewById(R.id.zemljisniTipItem)
        val biljkaKlimatskiTip: TextView = itemView.findViewById(R.id.klimatskiTipItem)


        fun prikazModova(mod: String) {
            when (mod) {
                "Medicinski" -> {
                    biljkaProfilOkusa.visibility = View.GONE
                    biljkaJela.elementAt(0).visibility = View.GONE
                    biljkaJela.elementAt(1).visibility = View.GONE
                    biljkaJela.elementAt(2).visibility = View.GONE
                    biljkaPorodica.visibility = View.GONE
                    biljkaZemljisniTip.visibility = View.GONE
                    biljkaKlimatskiTip.visibility = View.GONE
                    biljkaKoristi.elementAt(0).visibility = View.VISIBLE
                    biljkaKoristi.elementAt(1).visibility = View.VISIBLE
                    biljkaKoristi.elementAt(2).visibility = View.VISIBLE
                    biljkaUpozorenje.visibility = View.VISIBLE
                }

                "Kuharski" -> {
                    biljkaKoristi.elementAt(0).visibility = View.GONE
                    biljkaKoristi.elementAt(1).visibility = View.GONE
                    biljkaKoristi.elementAt(2).visibility = View.GONE
                    biljkaUpozorenje.visibility = View.GONE
                    biljkaPorodica.visibility = View.GONE
                    biljkaZemljisniTip.visibility = View.GONE
                    biljkaKlimatskiTip.visibility = View.GONE
                    biljkaJela.elementAt(0).visibility = View.VISIBLE
                    biljkaJela.elementAt(1).visibility = View.VISIBLE
                    biljkaJela.elementAt(2).visibility = View.VISIBLE
                    biljkaProfilOkusa.visibility = View.VISIBLE

                }

                "Botanički" -> {
                    biljkaProfilOkusa.visibility = View.GONE
                    biljkaKoristi.elementAt(0).visibility = View.GONE
                    biljkaKoristi.elementAt(1).visibility = View.GONE
                    biljkaKoristi.elementAt(2).visibility = View.GONE
                    biljkaUpozorenje.visibility = View.GONE
                    biljkaJela.elementAt(0).visibility = View.GONE
                    biljkaJela.elementAt(1).visibility = View.GONE
                    biljkaJela.elementAt(2).visibility = View.GONE
                    biljkaPorodica.visibility = View.VISIBLE
                    biljkaZemljisniTip.visibility = View.VISIBLE
                    biljkaKlimatskiTip.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.biljka_item, parent, false)
        return BiljkaViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        if (biljke.isEmpty() || position >= biljke.size) {
            Log.e(
                "BiljkaListAdapter",
                "prazno"
            )
            return
        }

        val biljka = biljke[position]
        holder.biljkaTitle.text = biljka.naziv

        val koristi = biljka.medicinskeKoristi.toList()
        val jela = biljka.jela


        if (mod == "Medicinski") {
            holder.biljkaUpozorenje.text = biljka.medicinskoUpozorenje
            holder.biljkaKoristi.elementAt(0).text = koristi.getOrNull(0)?.opis ?: ""
            holder.biljkaKoristi.elementAt(1).text = koristi.getOrNull(1)?.opis ?: ""
            holder.biljkaKoristi.elementAt(2).text = koristi.getOrNull(2)?.opis ?: ""
        } else if (mod == "Kuharski") {
            holder.biljkaProfilOkusa.text = biljka.profilOkusa?.opis?:""
            holder.biljkaJela.elementAt(0).text = jela.getOrNull(0) ?: ""
            holder.biljkaJela.elementAt(1).text = jela.getOrNull(1) ?: ""
            holder.biljkaJela.elementAt(2).text = jela.getOrNull(2) ?: ""
        } else if (mod == "Botanički") {
            holder.biljkaPorodica.text = biljka.porodica
            holder.biljkaKlimatskiTip.text =
                biljka.klimatskiTipovi.getOrNull(0)?.opis?:""
            holder.biljkaZemljisniTip.text =
                biljka.zemljisniTipovi.getOrNull(0)?.naziv?:""
        }
        trefleDAO.setContext(context)
        var biljkaDatabase: BiljkaDatabase = BiljkaDatabase.getInstance(context)

        val id=context.resources.getIdentifier("ic_launcher", "mipmap", context.packageName)
        holder.biljkaImage.setImageResource(id)

        coroutineScope.launch {
            val bitmapServis = trefleDAO.getImage(biljka)
            val biljkaBitmapDb = biljka.id?.let { biljkaDatabase.biljkaDao().getBitmapById(it) }
//
            if (biljkaBitmapDb==null) {

                biljka.id?.let { biljkaDatabase.biljkaDao().addImage(it, bitmapServis) }
                holder.biljkaImage.setImageBitmap(bitmapServis)

            }



        }

        holder.prikazModova(mod)

        holder.itemView.setOnClickListener {
            val selectedPlant = biljke[position]
            if (!MainActivity.flagReset){
            when (mod ) {
                "Medicinski" -> filterMedicinski(selectedPlant)
                "Kuharski" -> filterKuharski(selectedPlant)
                "Botanički" -> filterBotanicki(selectedPlant)
            }
                }
        }


    }

    private fun filterMedicinski(selectedPlant: Biljka) {
        val filteredList = biljke.filter {
            it == selectedPlant || it.medicinskeKoristi.any { korist ->
                selectedPlant.medicinskeKoristi.any { it.opis == korist.opis }
            }
        }
        updateBiljke(filteredList)
    }

    private fun filterKuharski(selectedPlant: Biljka) {
        val filteredList = biljke.filter {
            it == selectedPlant || it.jela.intersect(selectedPlant.jela)
                .isNotEmpty() || it.profilOkusa == selectedPlant.profilOkusa
        }
        updateBiljke(filteredList)
    }

    private fun filterBotanicki(selectedPlant: Biljka) {
        val filteredList = biljke.filter {
            it == selectedPlant || (it.porodica == selectedPlant.porodica && (it.klimatskiTipovi.intersect(
                selectedPlant.klimatskiTipovi
            ).isNotEmpty() && it.zemljisniTipovi.intersect(selectedPlant.zemljisniTipovi)
                .isNotEmpty()))
        }
        updateBiljke(filteredList)
    }

    fun updateMod(mod: String) {
        this.mod = mod
        notifyDataSetChanged()
    }

    fun updateBiljke(biljke2: List<Biljka>) {
        biljke = biljke2
//        Log.d("BiljkaListAdapter", "updateovano")
//        Log.d("BiljkaListAdapter", "filtrirane biljke : ${biljke.size}")
        notifyDataSetChanged()
    }
}
