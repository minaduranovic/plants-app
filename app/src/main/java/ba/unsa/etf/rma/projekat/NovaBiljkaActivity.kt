package ba.unsa.etf.rma.projekat

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.SparseBooleanArray
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

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

        val adapter1 = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            MedicinskaKorist.entries.map { it.opis }
        )
        medicinskaKoristListView.adapter = adapter1

        val adapter2 = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            KlimatskiTip.entries.map { it.opis }
        )
        klimatskiTipListView.adapter = adapter2

        val adapter3 = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            Zemljiste.entries.map { it.naziv }
        )
        zemljisniTipListView.adapter = adapter3

        val adapter4 = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            ProfilOkusaBiljke.entries.map { it.opis }
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

                if (novoJelo.isNotEmpty() && !listaJela.contains(novoJelo) && novoJelo.length >= 2 && novoJelo.length <= 20) {
                    listaJela[selectedJeloPosition] = novoJelo
                    adapter5.notifyDataSetChanged()
                } else if (novoJelo.isNotEmpty() && listaJela.contains(novoJelo)) {
                    jelo.error = "Ne mozete unijeti jelo koje vec postoji!"
                } else if (novoJelo.length == 1 || novoJelo.length > 20) {
                    jelo.error = "Jelo mora biti u opsegu od 2 do 20 znakova!"
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
            var f1 = validirajPolja()
            var f2 = validirajLV()
            if (f1 && f2) {
                val intent = Intent(this, MainActivity::class.java)
                val bundle = Bundle().apply {
                    putString("nazivBiljke", nazivBiljke.text.toString())
                    putString("porodicaBiljke", porodicaBiljke.text.toString())
                    putString("medicinskoUpozorenje", medUpoz.text.toString())
                    putStringArrayList("listaJela", listaJela)
                    putIntegerArrayList(
                        "medicinskaKoristChecked",
                        medicinskaKoristListView.checkedItemPositions.toArrayList()
                    )
                    putIntegerArrayList(
                        "klimatskiTipChecked",
                        klimatskiTipListView.checkedItemPositions.toArrayList()
                    )
                    putIntegerArrayList(
                        "zemljisniTipChecked",
                        zemljisniTipListView.checkedItemPositions.toArrayList()
                    )
                    putInt("profilOkusaChecked", profilOkusaListView.checkedItemPosition)
                }
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        uslikajBiljkuButton.setOnClickListener {

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePicture.launch(takePictureIntent)

        }
    }

    var takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    val imageView: ImageView = findViewById(R.id.slikaIV)
                    imageView.visibility = View.VISIBLE
                    imageView.setImageBitmap(imageBitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Došlo je do greške.", Toast.LENGTH_SHORT).show()
            }
        }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
                val imageBitmap = data?.extras?.get("data") as? Bitmap
                val imageView: ImageView = findViewById(R.id.slikaIV)
                imageView.visibility = View.VISIBLE
                imageView.setImageBitmap(imageBitmap)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Došlo je do greške.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    private fun addJelaToList() {
        val novoJelo = jelo.text.toString()
        if (novoJelo.length < 2 || novoJelo.length > 20) {
            jelo.error = "Jelo mora biti u opsegu od 2 do 20 znakova!"
            return
        }
        if (novoJelo.isNotEmpty()) {
            if (listaJela.map { it.lowercase() }.contains(novoJelo.lowercase())) {
                jelo.error = "Ne mozete unijeti jelo koje vec postoji!"
            } else {
                listaJela.add(0, novoJelo)
                adapter5.notifyDataSetChanged()
                jelo.setText("")
            }
        }
    }

    private fun validirajPolja(): Boolean {
        var flag = true

        if (nazivBiljke.text.length !in 2..20) {
            nazivBiljke.error = "Naziv biljke mora biti u opsegu od 2 do 20 znakova!"
            flag = false
        }
        if (porodicaBiljke.text.length !in 2..20) {
            porodicaBiljke.error = "Porodica biljke mora biti u opsegu od 2 do 20 znakova!"
            flag = false
        }
        if (medUpoz.text.length !in 2..20) {
            medUpoz.error = "Medicinsko upozorenje mora biti u opsegu od 2 do 20 znakova!"
            flag = false
        }
        return flag
    }

    private fun validirajLV(): Boolean {

        var flag = true

        if (medicinskaKoristListView.checkedItemCount == 0) {
            Toast.makeText(this, "Odaberite barem jednu medicinsku korist!", Toast.LENGTH_SHORT)
                .show()
            flag = false
        }
        if (klimatskiTipListView.checkedItemCount == 0) {
            Toast.makeText(this, "Odaberite barem jedan klimatski tip!", Toast.LENGTH_SHORT).show()
            flag = false
        }
        if (zemljisniTipListView.checkedItemCount == 0) {
            Toast.makeText(this, "Odaberite barem jedan zemljisni tip!", Toast.LENGTH_SHORT).show()
            flag = false
        }
        if (profilOkusaListView.checkedItemCount == 0) {
            Toast.makeText(this, "Odaberite profil okusa biljke!", Toast.LENGTH_SHORT).show()
            flag = false
        }
        if (jelaListView.adapter.count == 0) {
            Toast.makeText(this, "Morate dodati bar jedno jelo!", Toast.LENGTH_SHORT).show()
            flag = false
        }
        return flag
    }

    private fun SparseBooleanArray.toArrayList(): ArrayList<Int> {
        val odabrane = ArrayList<Int>()
        for (i in 0 until size()) {
            if (valueAt(i)) {
                odabrane.add(keyAt(i))
            }
        }
        return odabrane
    }
}

