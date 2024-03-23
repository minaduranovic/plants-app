package ba.unsa.etf.rma.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

    class BiljkaListAdapter(private var biljke: List<Biljka>,  private var mod: String ="",

                            ) :
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
                        this.biljkaProfilOkusa.visibility = View.GONE
                        this.biljkaJela.elementAt(0).visibility = View.GONE
                        this.biljkaJela.elementAt(1).visibility = View.GONE
                        this.biljkaJela.elementAt(2).visibility = View.GONE
                        this.biljkaPorodica.visibility = View.GONE
                        this.biljkaZemljisniTip.visibility = View.GONE
                        this.biljkaKlimatskiTip.visibility = View.GONE
                        this.biljkaKoristi.elementAt(0).visibility = View.VISIBLE
                        this.biljkaKoristi.elementAt(1).visibility = View.VISIBLE
                        this.biljkaKoristi.elementAt(2).visibility = View.VISIBLE
                        this.biljkaUpozorenje.visibility = View.VISIBLE
                    }

                    "Kuharski" -> {
                        this.biljkaKoristi.elementAt(0).visibility = View.GONE
                        this.biljkaKoristi.elementAt(1).visibility = View.GONE
                        this.biljkaKoristi.elementAt(2).visibility = View.GONE
                        this.biljkaUpozorenje.visibility = View.GONE
                        this.biljkaPorodica.visibility = View.GONE
                        this.biljkaZemljisniTip.visibility = View.GONE
                        this.biljkaKlimatskiTip.visibility = View.GONE
                        this.biljkaJela.elementAt(0).visibility = View.VISIBLE
                        this.biljkaJela.elementAt(1).visibility = View.VISIBLE
                        this.biljkaJela.elementAt(2).visibility = View.VISIBLE
                        this.biljkaProfilOkusa.visibility = View.VISIBLE

                    }

                    "Botanički" -> {
                        this.biljkaProfilOkusa.visibility = View.GONE
                        this.biljkaKoristi.elementAt(0).visibility = View.GONE
                        this.biljkaKoristi.elementAt(1).visibility = View.GONE
                        this.biljkaKoristi.elementAt(2).visibility = View.GONE
                        this.biljkaUpozorenje.visibility = View.GONE
                        this.biljkaJela.elementAt(0).visibility = View.GONE
                        this.biljkaJela.elementAt(1).visibility = View.GONE
                        this.biljkaJela.elementAt(2).visibility = View.GONE
                        this.biljkaPorodica.visibility = View.VISIBLE
                        this.biljkaZemljisniTip.visibility = View.VISIBLE
                        this.biljkaKlimatskiTip.visibility = View.VISIBLE
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

            holder.biljkaPorodica.text = biljke[position].porodica
            holder.biljkaProfilOkusa.text = biljke[position].profilOkusa.toString()
            holder.biljkaKlimatskiTip.text = biljke[position].klimatskiTipovi.elementAt(0).toString()
            holder.biljkaZemljisniTip.text = biljke[position].zemljisniTipovi.elementAt(0).toString()

            val koristi = biljke[position].medicinskeKoristi.toList()
            val jela = biljke[position].jela

            holder.biljkaKoristi.elementAt(0).text = koristi.getOrNull(0)?.toString() ?: "korist 1"
            holder.biljkaKoristi.elementAt(1).text = koristi.getOrNull(1)?.toString() ?: "korist 2"
            holder.biljkaKoristi.elementAt(2).text = koristi.getOrNull(2)?.toString() ?: "korist 3"

            holder.biljkaJela.elementAt(0).text = jela.getOrNull(0) ?: "jelo 1"
            holder.biljkaJela.elementAt(1).text = jela.getOrNull(1) ?: "jelo 2"
            holder.biljkaJela.elementAt(2).text = jela.getOrNull(2) ?: "jelo 3"

            holder.prikazModova(mod)

            holder.itemView.setOnClickListener {
                val selectedPlant = biljke[position]

                when (mod) {
                    "Medicinski" -> {
                        if (biljke.any { it != selectedPlant && it.medicinskeKoristi.intersect(selectedPlant.medicinskeKoristi).isNotEmpty() }) {
                            updateBiljke(biljke)
                        }
                    }
                    "Kuharski" -> {
                        if (biljke.any { it != selectedPlant && (it.jela.intersect(selectedPlant.jela).isNotEmpty() || it.profilOkusa == selectedPlant.profilOkusa) }) {
                            updateBiljke(biljke)
                        }
                    }
                    "Botanički" -> {
                        if (biljke.any { it != selectedPlant && it.porodica == selectedPlant.porodica && (it.klimatskiTipovi.intersect(selectedPlant.klimatskiTipovi).isNotEmpty() || it.zemljisniTipovi.intersect(selectedPlant.zemljisniTipovi).isNotEmpty()) }) {
                        updateBiljke(biljke)
                        }
                    }
                }
            }
//        holder.itemView.setOnClickListener{ onItemClicked(biljke[position]}



        }
        fun update(mod: String) {
            this.mod = mod
            notifyDataSetChanged()
        }
        fun updateBiljke(biljke: List<Biljka>){
            this.biljke= biljke
            notifyDataSetChanged()
        }

    }

