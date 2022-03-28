package fr.raisahmed.santy

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.RequiresApi
import fr.raisahmed.santy.adapter.ExerciceAdapter
import java.util.*

class ParamPopup(
    private val context : MainActivity
)
    : Dialog(context) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // sert à ne pas avoir de titre sur la popup
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_param_profil)

        val confirmButton = this.findViewById<Button>(R.id.confirmer)

        confirmButton.setOnClickListener{
            System.out.println("dans le listener")
            sendForm()

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendForm() {
        val repo = ProfilRepository()

            val nom = this.findViewById<EditText>(R.id.nouvnom).text.toString()
            val poids = this.findViewById<EditText>(R.id.nouvpoids).text.toString().toInt()
            val pas = this.findViewById<EditText>(R.id.nouvpas).text.toString().toInt()


        System.out.println(poids)
            // creer un nouvel objet ExerciceModel
            val profile = ProfilModel(
                nom,
                poids,
                pas
            )

            // envoyer en bdd
            repo.updateProfil(profile)


    }
}