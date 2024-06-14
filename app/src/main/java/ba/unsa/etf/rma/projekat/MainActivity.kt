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
    private var biljkeList = listOf<Biljka>()

    private lateinit var biljkaDatabase: BiljkaDatabase

    companion object {
        var flagReset = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        val trefleDAO = TrefleDAO()
        trefleDAO.setContext(context)
        val pretragaEditText: EditText = findViewById(R.id.pretragaET)
        val brzaPretraga: Button = findViewById(R.id.brzaPretraga)
        val spinnerBoja: Spinner = findViewById(R.id.bojaSPIN)
        val spinner: Spinner = findViewById(R.id.modSpinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.modSpinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        biljkeAdapter = BiljkaListAdapter(listOf(), context)
        biljkaDatabase = BiljkaDatabase.getInstance(this)

        biljkeView = findViewById(R.id.biljkeRV)
        biljkeView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        biljkeView.adapter = biljkeAdapter

        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val biljkeFromDb = biljkaDatabase.biljkaDao().getAllBiljkas()
            biljkeAdapter.updateBiljke(biljkeFromDb)

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
                            biljkeAdapter.updateBiljke(biljkeFromDb)
                            pretragaEditText.visibility = View.GONE
                            brzaPretraga.visibility = View.GONE
                            spinnerBoja.visibility = View.GONE
                            flagReset = false
                        }
                        "Kuharski" -> {
                            biljkeAdapter.updateMod("Kuharski")
                            biljkeAdapter.updateBiljke(biljkeFromDb)
                            pretragaEditText.visibility = View.GONE
                            brzaPretraga.visibility = View.GONE
                            spinnerBoja.visibility = View.GONE
                            flagReset = false
                        }
                        "Botanički" -> {
                            biljkeAdapter.updateBiljke(biljkeFromDb)
                            pretragaEditText.visibility = View.VISIBLE
                            brzaPretraga.visibility = View.VISIBLE
                            spinnerBoja.visibility = View.VISIBLE
                            biljkeAdapter.updateMod("Botanički")
                            flagReset = false
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        pretragaEditText.visibility = View.GONE
        brzaPretraga.visibility = View.GONE
        spinnerBoja.visibility = View.GONE

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

            if (pretragaText.isNotEmpty() && selectedColor.isNotEmpty()) {
                coroutineScope.launch {
                    val filteredPlants = trefleDAO.getPlantsWithFlowerColor(selectedColor, pretragaText)
                    biljkeAdapter.updateBiljke(filteredPlants)
                    flagReset = true
                }
            }
        }

        val resetButton: Button = findViewById<Button>(R.id.resetBtn)
        resetButton.setOnClickListener {
            scope.launch {
                biljkeList = biljkaDatabase.biljkaDao().getAllBiljkas()
                biljkeAdapter.updateBiljke(biljkeList)
            }
        }

        val dodajButton: Button = findViewById<Button>(R.id.novaBiljkaBtn)
        dodajButton.setOnClickListener {
            val intent2 = Intent(this, NovaBiljkaActivity::class.java)
            startActivity(intent2)
        }

        val novaBiljka: Biljka? = intent.getParcelableExtra(NovaBiljkaActivity.NOVA_BILJKA)
        if (novaBiljka != null) {
            Log.d("main ","zasto se ne dodajeee${novaBiljka.naziv}?" )
        }
        if (novaBiljka != null) {
            scope.launch {
                Log.d("main ","zasto se ne dodajeee?")
                biljkaDatabase.biljkaDao().saveBiljka(novaBiljka)
                val biljkeFromDb = biljkaDatabase.biljkaDao().getAllBiljkas()
                biljkeAdapter.updateBiljke(biljkeFromDb)
            }
        }
    }
}
