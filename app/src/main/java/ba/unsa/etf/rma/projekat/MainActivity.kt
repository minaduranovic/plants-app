package ba.unsa.etf.rma.projekat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var biljkeView: RecyclerView
    private lateinit var biljkeAdapter: BiljkaListAdapter
    private var biljkeList = getBiljkeList()
    companion object{
        var flagReset = false

    }

    //    private lateinit var noveBiljke: List<Biljka>
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        context = this
        val trefleDAO = TrefleDAO()
        trefleDAO.setContext(context)
        val pretragaEditText: EditText = findViewById(R.id.pretragaET)
        val brzaPretraga: Button = findViewById(R.id.brzaPretraga)
        val spinnerBoja: Spinner = findViewById(R.id.bojaSPIN)

        super.onCreate(savedInstanceState)


        pretragaEditText.visibility = View.GONE
        brzaPretraga.visibility = View.GONE
        spinnerBoja.visibility = View.GONE

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
                    "Medicinski" -> {
                        biljkeAdapter.updateMod("Medicinski")
                        biljkeAdapter.updateBiljke(biljke)
                        pretragaEditText.visibility = View.GONE
                        brzaPretraga.visibility = View.GONE
                        spinnerBoja.visibility = View.GONE
                        flagReset=false
                    }

                    "Kuharski" -> {
                        biljkeAdapter.updateMod("Kuharski")
                        biljkeAdapter.updateBiljke(biljke)
                        pretragaEditText.visibility = View.GONE
                        brzaPretraga.visibility = View.GONE
                        spinnerBoja.visibility = View.GONE
                        flagReset=false
                    }

                    "Botanički" -> {
                        biljkeAdapter.updateBiljke(biljke)
                        pretragaEditText.visibility = View.VISIBLE
                        brzaPretraga.visibility = View.VISIBLE
                        spinnerBoja.visibility = View.VISIBLE
                        biljkeAdapter.updateMod("Botanički")
                        flagReset=false

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.boje_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBoja.adapter = adapter
        }


        val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

        brzaPretraga.setOnClickListener {
            val pretragaText = pretragaEditText.text.toString()
            val selectedColor = spinnerBoja.selectedItem.toString()
//            biljkeAdapter.updateMod("Botanički")

            if (pretragaText.isNotEmpty() && selectedColor.isNotEmpty()) {
                coroutineScope.launch {
                    val filteredPlants =
                        trefleDAO.getPlantsWithFlowerColor(selectedColor, pretragaText)
//                    Log.d("MainActivity", "filtrirane biljke iz maina : ${filteredPlants.size}")
                    biljkeAdapter.updateBiljke(filteredPlants)
                    flagReset=true
                }
            }
        }

        val resetButton: Button = findViewById<Button>(R.id.resetBtn)
        resetButton.setOnClickListener {
            biljkeList = getBiljkeList()
            biljkeAdapter.updateBiljke(biljke)
        }

        biljkeView = findViewById(R.id.biljkeRV)
        biljkeView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        biljkeAdapter = BiljkaListAdapter(listOf(), context)
        biljkeView.adapter = biljkeAdapter
        biljkeAdapter.updateBiljke(biljke)

        val dodajButton: Button = findViewById<Button>(R.id.novaBiljkaBtn)
        dodajButton.setOnClickListener {
            val intent2 = Intent(this, NovaBiljkaActivity::class.java)
            startActivity(intent2)

        }
//
//        val bundle = intent.extras
//        val nazivBiljke = bundle?.getString("nazivBiljke")
//        val porodicaBiljke = bundle?.getString("porodicaBiljke")
//        val medicinskoUpozorenje = bundle?.getString("medicinskoUpozorenje")
//        val listaJela = bundle?.getStringArrayList("listaJela")
//        val medicinskeKoristi = bundle?.getIntegerArrayList("medicinskaKoristChecked")?.map { MedicinskaKorist.entries[it] }
//        val profilOkusaCheckedPosition = bundle?.getInt("profilOkusaChecked", 0)
//        val profilOkusa = ProfilOkusaBiljke.entries[profilOkusaCheckedPosition ?: 0]
//        val klimatskiTipovi = bundle?.getIntegerArrayList("klimatskiTipChecked")?.map { KlimatskiTip.entries[it] }
//        val zemljisniTipovi = bundle?.getIntegerArrayList("zemljisniTipChecked")?.map { Zemljiste.entries[it] }
//
//
//        val novaBiljka = Biljka(
//            nazivBiljke ?: "",
//            porodicaBiljke ?: "",
//            medicinskoUpozorenje ?: "",
//            medicinskeKoristi ?: emptyList(),
//            profilOkusa,
//            listaJela ?: emptyList(),
//            klimatskiTipovi ?: emptyList(),
//            zemljisniTipovi ?: emptyList()
//        )
        val novaBiljka: Biljka? = intent.getParcelableExtra(NovaBiljkaActivity.NOVA_BILJKA)
        if (novaBiljka != null) {
            if (novaBiljka.naziv != null) {
                biljke += novaBiljka
                biljkeAdapter.updateBiljke(biljke)
            }
        }
    }

}
