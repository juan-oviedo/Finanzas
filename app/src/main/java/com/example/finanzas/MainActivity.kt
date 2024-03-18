package com.example.finanzas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import java.util.Date

class MainActivity<SharedPreferences> : AppCompatActivity() {

    private lateinit var add_income: CardView
    private lateinit var tags: CardView
    private lateinit var add_cost: CardView
    private lateinit var balance: CardView
    private lateinit var help: CardView

    private val PREFS_NAME = "MyPrefsFile"
    private val FIRST_TIME = "first_time"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAttributes()
        initComponents()
        initListeners()
    }

    private fun initAttributes() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean(FIRST_TIME, true)
        if (isFirstTime) {
            showPopup()

            // Update the flag to indicate that the popup has been shown
            val editor = sharedPreferences.edit()
            editor.putBoolean(FIRST_TIME, false)
            editor.apply()
        }
    }

    private fun showPopup() {
        // Create a TextView to hold the popup text
        val textView = TextView(this)
        textView.text = "Muchas gracias por probar la aplicacion\n" +
                        "Cosas que se pueden hacer:\n" +
                        "- Agregar un egreso/ingreso\n" +
                        "- Agregar una etiqueta para clasificar los egresos/ingresos\n" +
                        "- Listar las etiquetas\n" +
                        "- Borrar/Editar las etiquetas\n" +
                        "\tetiquetas -> listar etiquetas -> click en la que se quiera modificar\n" +
                        "- Calcular Balance ya sea personalizado o por mes\n" +
                        "- Calcular Balance por tags ya sea personalizado o por mes\n" +
                        "- Listar los egresos/ingresos que estan con un tag\n" +
                        "\tbalance -> balance por tag -> seleccionar fecha -> click en la etiqueta que se quiera ver\n" +
                        "- Editar/Borrar egreso/ingreso\n" +
                        "\thacer click en el egreso/ingreso de la lista ya sea en balance comun o balance por tag"

        // Create a AlertDialog.Builder to build the dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Bienvenido")
        builder.setView(textView)
        builder.setPositiveButton("OK") { dialog, _ ->
            // Handle button click if needed
            dialog.dismiss()
        }

        // Create and show the AlertDialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun initComponents(){
        add_income = findViewById(R.id.Add_income)
        tags = findViewById(R.id.Tags)
        add_cost = findViewById(R.id.Add_cost)
        balance = findViewById(R.id.Balance)
        help = findViewById(R.id.Help)
    }

    private fun initListeners(){
        add_income.setOnClickListener{ navigateToAddIncome() }
        tags.setOnClickListener{navigateToTag()}
        add_cost.setOnClickListener { navigateToAddCost() }
        balance.setOnClickListener { navigateToBalance() }
        help.setOnClickListener { showPopup() }
    }

    private fun navigateToAddIncome (){
        val intent = Intent(this, AddIncomeActivity::class.java)
        intent.putExtra("isIncome", true)
        startActivity(intent)
    }

    private fun navigateToTag (){
        val intent = Intent(this, TagsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAddCost (){
        val intent = Intent(this, AddIncomeActivity::class.java)
        intent.putExtra("isIncome", false)
        startActivity(intent)
    }

    private fun navigateToBalance(){
        val intent = Intent(this, BalanceActivity::class.java)
        startActivity(intent)
    }


}