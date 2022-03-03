package fr.raisahmed.santy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.raisahmed.santy.ExerciceModel
import fr.raisahmed.santy.MainActivity
import fr.raisahmed.santy.R
import fr.raisahmed.santy.adapter.ExerciceAdapter
import fr.raisahmed.santy.adapter.ExerciceItemDecoration

class HomeFragment(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        // creer une liste qui va stocker les exercices
        val exercicesList = arrayListOf<ExerciceModel>()

        // enregistrer une première exercice dans notre liste
        exercicesList.add(
            ExerciceModel(
            "Abdos",
            "au sol sans machines",
            "https://cdn.pixabay.com/photo/2016/02/16/19/16/abdominal-1203880_960_720.jpg",
            false
        )
        )

        exercicesList.add(
            ExerciceModel(
                "Développé couché",
                "avec barre et poids",
                "https://media.istockphoto.com/photos/young-man-putting-effort-in-on-a-bench-press-picture-id155135228?s=612x612",
                true
            )
        )

        // recuperer le recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = ExerciceAdapter(context, exercicesList, R.layout.item_horizontal_exercice)

        // recuperer second recyclerview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = ExerciceAdapter(context, exercicesList, R.layout.item_vertical_exercices)
        verticalRecyclerView.addItemDecoration(ExerciceItemDecoration())

        return view
    }

}