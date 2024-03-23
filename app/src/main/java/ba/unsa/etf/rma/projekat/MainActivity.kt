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


class MainActivity : AppCompatActivity() {
///

    private lateinit var biljkeView: RecyclerView
    private lateinit var biljkeAdapter: BiljkaListAdapter
    private var biljkeList = getBiljkeList()

//    fun filterMedicinski(biljke: List<Biljka>): List<Biljka> {
//        val medicinskeKoristi = biljke.flatMap { it.medicinskeKoristi }
//        return biljke.filter { biljka ->
//            biljka.medicinskeKoristi.any { korist ->
//                medicinskeKoristi.any { it.opis == korist.opis && it != korist }
//            }
//        }
//    }
//
//    fun filterKuharski(biljke: List<Biljka>): List<Biljka> {
//        val jela = biljke.flatMap { it.jela }
//        val profilOkusa = biljke.map { it.profilOkusa }.distinct()
//        return biljke.filter { biljka ->
//            biljka.jela.any { jelo ->
//                jela.any { it == jelo } || profilOkusa.any { it == biljka.profilOkusa }
//            }
//        }
//    }
//
//    fun filterBotanicki(biljke: List<Biljka>): List<Biljka> {
//        val profilOkusa = biljke.map { it.profilOkusa }.distinct()
//        val zemljisniTipovi = biljke.flatMap { it.zemljisniTipovi }.distinct()
//        val klimatskiTipovi = biljke.flatMap { it.klimatskiTipovi }.distinct()
//        return biljke.filter { biljka ->
//            profilOkusa.any { it == biljka.profilOkusa } &&
//                    (biljka.zemljisniTipovi.any { it in zemljisniTipovi } ||
//                            biljka.klimatskiTipovi.any { it in klimatskiTipovi })
//        }
//    }

//private fun filtriraj(mod: String){
//    when (mod) {
//        "Medicinski" -> filterMedicinski(mod)
//        "Kuharski" -> biljkeAdapter.update("Kuharski")
//        "Botanički" -> biljkeAdapter.update("Botanički")
//    }
//}
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
                    "Botanički" -> biljkeAdapter.update("Botanički")
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
