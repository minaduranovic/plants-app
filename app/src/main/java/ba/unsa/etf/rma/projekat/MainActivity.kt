package ba.unsa.etf.rma.projekat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
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
            val intent = Intent(this, NovaBiljkaActivity::class.java)
//            startActivityForResult(intent, NOVA_BILJKA_REQUEST_CODE)
            startActivity(intent)
        }




    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == NOVA_BILJKA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            val novaBiljka = data?.getParcelableExtra<Biljka>("nova_biljka")
//            novaBiljka?.let {
//                // Dodavanje nove biljke u listu biljaka i ažuriranje adaptera
//                biljkeList.add(it)
//                biljkeAdapter.updateBiljke(biljkeList)
//            }
//        }
//    }
//
//    companion object {
//        const val NOVA_BILJKA_REQUEST_CODE = 1
//    }




}
