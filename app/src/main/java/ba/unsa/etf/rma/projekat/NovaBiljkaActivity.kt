package ba.unsa.etf.rma.projekat

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var uslikajBiljkuButton: Button

    private val listaJela = arrayListOf<String>()
    private lateinit var adapter5: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.nova_biljka)

        nazivBiljke = findViewById(R.id.nazivET)
        porodicaBiljke = findViewById(R.id.porodicaET)
        medUpoz = findViewById(R.id.medicinskoUpozorenjeET)
        medicinskaKoristListView = findViewById(R.id.medicinskaKoristLV)
        klimatskiTipListView = findViewById(R.id.klimatskiTipLV)
        zemljisniTipListView = findViewById(R.id.zemljisniTipLV)
        profilOkusaListView = findViewById(R.id.profilOkusaLV)
        jelaListView = findViewById(R.id.jelaLV)
        jelo = findViewById(R.id.jeloET)
        dodajJeloBtn = findViewById(R.id.dodajJeloBtn)
        dodajBiljkuButton = findViewById(R.id.dodajBiljkuBtn)
        uslikajBiljkuButton = findViewById(R.id.uslikajBiljkuBtn)

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

        dodajBiljkuButton.setOnClickListener {
            if (checkAllFields()) {
                val novaBiljka = dodajNovuBiljku()
//                val intent = Intent().apply {
//                    putExtra("nova_biljka", novaBiljka)
//                }
//                setResult(Activity.RESULT_OK, intent)
//                finish()
            }
        }

        val uslikajBiljkuBtn: Button = findViewById(R.id.uslikajBiljkuBtn)
        uslikajBiljkuBtn.setOnClickListener {

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePicture.launch(takePictureIntent)

        }
    }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap
                val imageView: ImageView = findViewById(R.id.slikaIV)
                imageView.visibility = View.VISIBLE
                imageView.setImageBitmap(imageBitmap)
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val imageView: ImageView = findViewById(R.id.slikaIV)
            imageView.visibility = View.VISIBLE
            imageView.setImageBitmap(imageBitmap)
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    private fun addJelaToList() {
        val novoJelo = jelo.text.toString()
        if (novoJelo.length <= 2 || novoJelo.length > 20) jelo.setError("Jelo mora biti u opsegu od 2 do 20 znakova!")
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
        if (nazivBiljke.length() <= 2 || nazivBiljke.length() > 20) {
            nazivBiljke.setError("Naziv biljke mora biti u opsegu od 2 do 20 znakova!")
            return false
        }
        if (porodicaBiljke.length() <= 2 || porodicaBiljke.length() > 20) {
            porodicaBiljke.setError("Porodica biljke mora biti u opsegu od 2 do 20 znakova!")
            return false
        }
        if (medUpoz.length() <= 2 || medUpoz.length() > 20) {
            medUpoz.setError("Medicinsko upozorenje mora biti u opsegu od 2 do 20 znakova!")
            return false
        }
//        if (jelo.length()<=2 ||  jelo.length()>20) {
//            jelo.setError("Jelo mora biti u opsegu od 2 do 20 znakova!")
//            return false
//        }
        if (medicinskaKoristListView.checkedItemCount == 0) {
            Toast.makeText(this, "Odaberite barem jednu medicinsku korist!", Toast.LENGTH_SHORT)
                .show()
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
        if (jelaListView.adapter.count == 0) {
            Toast.makeText(this, "Morate dodati bar jedno jelo!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    private fun dodajNovuBiljku(): Biljka {
        val naziv = nazivBiljke.text.toString()
        val porodica = porodicaBiljke.text.toString()
        val medicinskoUpozorenje = medUpoz.text.toString()
        val medicinskeKoristi = mutableListOf<MedicinskaKorist>()
        val klimatskiTipovi = mutableListOf<KlimatskiTip>()
        val zemljisniTipovi = mutableListOf<Zemljiste>()

        for (i in 0 until medicinskaKoristListView.count) {
            if (medicinskaKoristListView.isItemChecked(i)) {
                medicinskeKoristi.add(MedicinskaKorist.values()[i])
            }
        }
        for (i in 0 until klimatskiTipListView.count) {
            if (klimatskiTipListView.isItemChecked(i)) {
                klimatskiTipovi.add(KlimatskiTip.values()[i])
            }
        }
        for (i in 0 until zemljisniTipListView.count) {
            if (zemljisniTipListView.isItemChecked(i)) {
                zemljisniTipovi.add(Zemljiste.values()[i])
            }
        }
        val profilOkusa = ProfilOkusaBiljke.values()[profilOkusaListView.checkedItemPosition]
        val jela = listaJela

        val novaBiljka = Biljka(
            naziv,
            porodica,
            medicinskoUpozorenje,
            medicinskeKoristi,
            profilOkusa,
            jela,
            klimatskiTipovi,
            zemljisniTipovi
        )

//        listaBiljaka.add(novaBiljka)

//        Toast.makeText(this, "Nova biljka dodana!", Toast.LENGTH_SHORT).show()

//        finish()
        return novaBiljka
    }


}
