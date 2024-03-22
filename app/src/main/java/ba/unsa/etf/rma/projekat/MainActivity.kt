package ba.unsa.etf.rma.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BiljkaListAdapter(private var biljke: List<Biljka>) :
    RecyclerView.Adapter<BiljkaListAdapter.BiljkaViewHolder>() {
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
                    this.biljkaProfilOkusa.setVisibility(View.GONE)
                    this.biljkaJela.elementAt(0).setVisibility(View.GONE)
                    this.biljkaJela.elementAt(1).setVisibility(View.GONE)
                    this.biljkaJela.elementAt(2).setVisibility(View.GONE)
                    this.biljkaPorodica.setVisibility(View.GONE)
                    this.biljkaZemljisniTip.setVisibility(View.GONE)
                    this.biljkaKlimatskiTip.setVisibility(View.GONE)
                    this.biljkaKoristi.elementAt(0).setVisibility(View.VISIBLE)
                    this.biljkaKoristi.elementAt(1).setVisibility(View.VISIBLE)
                    this.biljkaKoristi.elementAt(2).setVisibility(View.VISIBLE)
                    this.biljkaUpozorenje.setVisibility(View.VISIBLE)
                }

                "Kuharski" -> {
                    this.biljkaKoristi.elementAt(0).setVisibility(View.GONE)
                    this.biljkaKoristi.elementAt(1).setVisibility(View.GONE)
                    this.biljkaKoristi.elementAt(2).setVisibility(View.GONE)
                    this.biljkaUpozorenje.setVisibility(View.GONE)
                    this.biljkaPorodica.setVisibility(View.GONE)
                    this.biljkaZemljisniTip.setVisibility(View.GONE)
                    this.biljkaKlimatskiTip.setVisibility(View.GONE)
                    this.biljkaProfilOkusa.setVisibility(View.VISIBLE)
                    this.biljkaJela.elementAt(0).setVisibility(View.VISIBLE)
                    this.biljkaJela.elementAt(1).setVisibility(View.VISIBLE)
                    this.biljkaJela.elementAt(2).setVisibility(View.VISIBLE)
                }

                "Botanicki" -> {
                    this.biljkaKoristi.elementAt(0).setVisibility(View.GONE)
                    this.biljkaKoristi.elementAt(1).setVisibility(View.GONE)
                    this.biljkaKoristi.elementAt(2).setVisibility(View.GONE)
                    this.biljkaUpozorenje.setVisibility(View.GONE)
                    this.biljkaProfilOkusa.setVisibility(View.GONE)
                    this.biljkaJela.elementAt(0).setVisibility(View.GONE)
                    this.biljkaJela.elementAt(1).setVisibility(View.GONE)
                    this.biljkaJela.elementAt(2).setVisibility(View.GONE)
                    this.biljkaPorodica.setVisibility(View.VISIBLE)
                    this.biljkaZemljisniTip.setVisibility(View.VISIBLE)
                    this.biljkaKlimatskiTip.setVisibility(View.VISIBLE)
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

        holder.biljkaTitle.text = biljke[position].naziv
        holder.biljkaUpozorenje.text = biljke[position].medicinskoUpozorenje

        holder.biljkaPorodica.text=biljke[position].porodica
        holder.biljkaProfilOkusa.text= biljke[position].profilOkusa.toString()
        holder.biljkaKlimatskiTip.text=biljke[position].klimatskiTipovi.elementAt(0).toString()
        holder.biljkaZemljisniTip.text=biljke[position].zemljisniTipovi.elementAt(0).toString()

        val koristi = biljke[position].medicinskeKoristi.toList()
        val jela = biljke[position].jela.toList()

        holder.biljkaKoristi.elementAt(0).text = koristi.getOrNull(0)?.toString() ?: "korist 1"
        holder.biljkaKoristi.elementAt(1).text = koristi.getOrNull(1)?.toString() ?: "korist 2"
        holder.biljkaKoristi.elementAt(2).text = koristi.getOrNull(2)?.toString() ?: "korist 3"

        holder.biljkaJela.elementAt(0).text = jela.getOrNull(0)?.toString() ?: "jelo 1"
        holder.biljkaJela.elementAt(1).text = jela.getOrNull(1)?.toString() ?: "jelo 2"
        holder.biljkaJela.elementAt(2).text = jela.getOrNull(2)?.toString() ?: "jelo 3"

        if(mod.isNotBlank()) holder.prikazModova(mod)


    }
    var mod: String = ""
    fun update(mod: String) {
        this.mod = mod
        notifyDataSetChanged()
    }
    fun updateBiljke(biljke: List<Biljka>){
        this.biljke= biljke
        notifyDataSetChanged()
    }

}

class MainActivity : AppCompatActivity() {
///

    private lateinit var biljkeView: RecyclerView
    private lateinit var biljkeAdapter: BiljkaListAdapter
    private var biljkeList = getBiljkeList()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.modSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.modSpinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()

                when (selectedItem) {
                    "Medicinski" -> biljkeAdapter.update("Medicinski")
                    "Kuharski" -> biljkeAdapter.update("Kuharski")
                    "Botanicki" -> biljkeAdapter.update("Botanicki")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        biljkeView = findViewById(R.id.blijkeRV)
        biljkeView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        biljkeAdapter = BiljkaListAdapter(listOf())
        biljkeView.adapter = biljkeAdapter
        biljkeAdapter.updateBiljke(biljkeList)

    }

}