package fr.raisahmed.santy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.raisahmed.santy.*
import fr.raisahmed.santy.adapter.ExerciceAdapter
import fr.raisahmed.santy.adapter.ExerciceItemDecoration
import fr.raisahmed.santy.adapter.ProfilAdapter

class ProfilFragment(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_profil, container, false)

        val blaz = view.findViewById<TextView>(R.id.pseudo)
        val poids = view.findViewById<TextView>(R.id.lepoids)
        val pas = view.findViewById<TextView>(R.id.lespas)
        val param = view.findViewById<ImageView>(R.id.parametres)

        val repo = ProfilRepository()
        repo.updateData {
            blaz.text = ProfilRepository.Singleton.profil.nom
            poids.text = ProfilRepository.Singleton.profil.poids.toString()
            pas.text = ProfilRepository.Singleton.profil.pas.toString()

            param.setOnClickListener{
                // afficher la popup
                ParamPopup(context).show()
            }
        }

        return view
    }
}