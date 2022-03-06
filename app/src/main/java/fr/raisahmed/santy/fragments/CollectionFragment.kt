package fr.raisahmed.santy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.raisahmed.santy.ExerciceRepository.Singleton.exerciceList
import fr.raisahmed.santy.MainActivity
import fr.raisahmed.santy.R
import fr.raisahmed.santy.adapter.ExerciceAdapter
import fr.raisahmed.santy.adapter.ExerciceItemDecoration

class CollectionFragment(
    private val context : MainActivity
)    : Fragment()
 {

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         val view = inflater?.inflate(R.layout.fragment_collection, container, false)

         // récupérer mon recycler view
         val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
         // lambda avec filter pour prendre uniquement les exercices likés
         collectionRecyclerView.adapter = ExerciceAdapter(context, exerciceList.filter { it.liked }, R.layout.item_vertical_exercices)
         collectionRecyclerView.layoutManager = LinearLayoutManager(context)
         collectionRecyclerView.addItemDecoration(ExerciceItemDecoration())

         return view
     }



}