package fr.raisahmed.santy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.raisahmed.santy.ExerciceModel
import fr.raisahmed.santy.ExerciceRepository.Singleton.exerciceList
import fr.raisahmed.santy.MainActivity
import fr.raisahmed.santy.R
import fr.raisahmed.santy.adapter.ExerciceAdapter
import fr.raisahmed.santy.adapter.ExerciceItemDecoration

class HomeFragment(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        // recuperer le recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = ExerciceAdapter(context, exerciceList, R.layout.item_horizontal_exercice)

        // recuperer second recyclerview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = ExerciceAdapter(context, exerciceList, R.layout.item_vertical_exercices)
        verticalRecyclerView.addItemDecoration(ExerciceItemDecoration())

        return view
    }

}