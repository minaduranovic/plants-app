package ba.unsa.etf.rma.projekat

import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NovaBiljkaActivity : AppCompatActivity() {
    private lateinit var medicinskaKoristListView: ListView
    private lateinit var klimatskiTipListView: ListView
    private lateinit var zemljisniTipListView: ListView
    private lateinit var profilOkusaListView: ListView
    private lateinit var jelaListView: ListView
    private lateinit var jelo: EditText
    private lateinit var dodajJeloBtn: Button
    private lateinit var nazivBiljke: EditText
    private lateinit var porodicaBiljke: EditText
    private lateinit var medUpoz: EditText
    private lateinit var dodajBiljkuButton: Button

    private val listaJela = arrayListOf<String>()
    private lateinit var adapter5: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.nova_biljka)

        nazivBiljke = findViewById(R.id.nazivET)
        porodicaBiljke= findViewById(R.id.porodicaET)
        medUpoz=findViewById(R.id.medicinskoUpozorenjeET)
        medicinskaKoristListView = findViewById(R.id.medicinskaKoristLV)
        klimatskiTipListView = findViewById(R.id.klimatskiTipLV)
        zemljisniTipListView = findViewById(R.id.zemljisniTipLV)
        profilOkusaListView = findViewById(R.id.profilOkusaLV)
        jelaListView = findViewById(R.id.jelaLV)
        jelo = findViewById(R.id.jeloET)
        dodajJeloBtn = findViewById(R.id.dodajJeloBtn)
        dodajBiljkuButton= findViewById(R.id.dodajBiljkuBtn)


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

        dodajBiljkuButton.setOnClickListener{
            checkAllFields()
        }

    }
    private fun addJelaToList() {
        val novoJelo = jelo.text.toString()
        if (novoJelo.length<=2 || novoJelo.length>20) jelo.setError("Jelo mora biti u opsegu od 2 do 20 znakova!")
        fun List<String>.lowerCase(): List<String> = this.map { it.lowercase() }
        if (novoJelo.isNotEmpty()) {
            if (listaJela.lowerCase().contains(novoJelo.lowercase())) {
                jelo.setError("Ne mozete unijeti jelo koje vec postoji!")
            }
            listaJela.add(0, novoJelo)
            adapter5.notifyDataSetChanged()
            jelo.setText("")
        }
    }

    private fun checkAllFields(): Boolean {
        if (nazivBiljke.length()<=2 || nazivBiljke.length()>20) {
            nazivBiljke.setError("Naziv biljke mora biti u opsegu od 2 do 20 znakova!")
            return false
        }
        if (porodicaBiljke.length()<=2 || porodicaBiljke.length()>20) {
            porodicaBiljke.setError("Porodica biljke mora biti u opsegu od 2 do 20 znakova!")
            return false
        }
        if (medUpoz.length()<=2 || medUpoz.length()>20) {
            medUpoz.setError("Medicinsko upozorenje mora biti u opsegu od 2 do 20 znakova!")
            return false
        }
//        if (jelo.length()<=2 ||  jelo.length()>20) {
//            jelo.setError("Jelo mora biti u opsegu od 2 do 20 znakova!")
//            return false
//        }
        if (medicinskaKoristListView.checkedItemCount == 0) {
            Toast.makeText(this, "Odaberite barem jednu medicinsku korist!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (klimatskiTipListView.checkedItemCount == 0) {
            Toast.makeText(this, "Odaberite barem jedan klimatski tip!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (zemljisniTipListView.checkedItemCount == 0) {
            Toast.makeText(this, "Odaberite barem jedan zemljisni tip!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (profilOkusaListView.checkedItemCount == 0) {
            Toast.makeText(this, "Odaberite profil okusa biljke!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (jelaListView.adapter.count==0){
            Toast.makeText(this, "Morate dodati bar jedno jelo!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
