package ba.unsa.etf.rma.projekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

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
                    "Medicinski" -> biljkeAdapter.updateMod("Medicinski")
                    "Kuharski" -> biljkeAdapter.updateMod("Kuharski")
                    "Botanički" -> biljkeAdapter.updateMod("Botanički")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val resetButton: Button = findViewById<Button>(R.id.resetBtn)
        resetButton.setOnClickListener {
            biljkeList = getBiljkeList()
            biljkeAdapter.updateBiljke(biljkeList)
        }

        biljkeView = findViewById(R.id.biljkeRV)
        biljkeView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        biljkeAdapter = BiljkaListAdapter(listOf())
        biljkeView.adapter = biljkeAdapter
        biljkeAdapter.updateBiljke(biljkeList)

        val dodajButton: Button = findViewById<Button>(R.id.novaBiljkaBtn)
        dodajButton.setOnClickListener {
            val intent2 = Intent(this, NovaBiljkaActivity::class.java)
            startActivity(intent2)

        }

        val bundle = intent.extras
        val nazivBiljke = bundle?.getString("nazivBiljke")
        val porodicaBiljke = bundle?.getString("porodicaBiljke")
        val medicinskoUpozorenje = bundle?.getString("medicinskoUpozorenje")
        val listaJela = bundle?.getStringArrayList("listaJela")
        val medicinskeKoristi = bundle?.getIntegerArrayList("medicinskaKoristChecked")?.map { MedicinskaKorist.entries[it] }
        val profilOkusaCheckedPosition = bundle?.getInt("profilOkusaChecked", 0)
        val profilOkusa = ProfilOkusaBiljke.entries[profilOkusaCheckedPosition ?: 0]
        val klimatskiTipovi = bundle?.getIntegerArrayList("klimatskiTipChecked")?.map { KlimatskiTip.entries[it] }
        val zemljisniTipovi = bundle?.getIntegerArrayList("zemljisniTipChecked")?.map { Zemljiste.entries[it] }


        val novaBiljka = Biljka(
            nazivBiljke ?: "",
            porodicaBiljke ?: "",
            medicinskoUpozorenje ?: "",
            medicinskeKoristi ?: emptyList(),
            profilOkusa,
            listaJela ?: emptyList(),
            klimatskiTipovi ?: emptyList(),
            zemljisniTipovi ?: emptyList()
        )
        if (nazivBiljke!=null) {
            val noveBiljke = biljke + novaBiljka
            biljkeAdapter.updateBiljke(noveBiljke)
        }
    }

}
