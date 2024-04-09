package ba.unsa.etf.rma.projekat

import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NovaBiljkaActivity : AppCompatActivity() {
    private lateinit var biljka: Biljka
    private lateinit var medicinskaKoristListView: ListView
    private lateinit var klimatskiTipListView: ListView
    private lateinit var zemljisniTipListView: ListView
    private lateinit var profilOkusaListView: ListView
    private lateinit var jelaListView: ListView
    private lateinit var jelo: EditText
    private lateinit var dodajJeloBtn: Button
    private val listaJela = arrayListOf<String>()
    private lateinit var adapter5: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.nova_biljka)

        medicinskaKoristListView = findViewById(R.id.medicinskaKoristLV)
        klimatskiTipListView = findViewById(R.id.klimatskiTipLV)
        zemljisniTipListView = findViewById(R.id.zemljisniTipLV)
        profilOkusaListView = findViewById(R.id.profilOkusaLV)
        jelaListView = findViewById(R.id.jelaLV)
        jelo = findViewById(R.id.jeloET)
        dodajJeloBtn = findViewById(R.id.dodajJeloBtn)

        val adapter1 = ArrayAdapter<MedicinskaKorist>(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            MedicinskaKorist.entries.toTypedArray()
        )
        medicinskaKoristListView.adapter = adapter1

        val adapter2 = ArrayAdapter<KlimatskiTip>(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            KlimatskiTip.values()
        )
        klimatskiTipListView.adapter = adapter2

        val adapter3 = ArrayAdapter<Zemljiste>(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            Zemljiste.values()
        )
        zemljisniTipListView.adapter = adapter3

        val adapter4 = ArrayAdapter<ProfilOkusaBiljke>(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            ProfilOkusaBiljke.values()
        )
        profilOkusaListView.adapter = adapter4

        adapter5 = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaJela)
        jelaListView.adapter = adapter5

        var selectedJeloPosition: Int = -1

        jelaListView.setOnItemClickListener { parent, view, position, id ->
            selectedJeloPosition = position
            val selectedJelo = listaJela[position]
            dodajJeloBtn.text = "Izmijeni jelo"
            jelo.setText(selectedJelo)
        }

        dodajJeloBtn.setOnClickListener {
            if (dodajJeloBtn.text == "Izmijeni jelo") {
                val novoJelo = jelo.text.toString()
                if (novoJelo.isNotEmpty()) {
                    listaJela[selectedJeloPosition] = novoJelo
                    adapter5.notifyDataSetChanged()
                } else {
                    listaJela.removeAt(selectedJeloPosition)
                    adapter5.notifyDataSetChanged()
                }
                dodajJeloBtn.text = "Dodaj jelo"
                jelo.setText("")
            } else {
                addJelaToList()
            }
        }

    }
    private fun addJelaToList() {
        val novoJelo = jelo.text.toString()
        if (novoJelo.isNotEmpty()) {
            listaJela.add(0, novoJelo)
            adapter5.notifyDataSetChanged()
            jelo.setText("")
        }
    }
}
