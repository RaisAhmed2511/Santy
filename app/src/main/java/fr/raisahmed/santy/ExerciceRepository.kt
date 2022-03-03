package fr.raisahmed.santy

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.raisahmed.santy.ExerciceRepository.Singleton.databaseRef
import fr.raisahmed.santy.ExerciceRepository.Singleton.exerciceList

class ExerciceRepository {

    // Le singleton permet d'accéder aux valeurs qu'il possède dans toute l'application
    // sans avoir à les rafraîchir

    object Singleton{
        // se connecter à la référence "exercices"
        val databaseRef = FirebaseDatabase.getInstance().getReference("exercices")

        // créer une liste qui va contenir nos exercices
        val exerciceList = arrayListOf<ExerciceModel>()
    }

    fun updateData(callback: () -> Unit){
        // absorber les données depuis la databaseRef --> liste d'exercices
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // retirer les anciens exercices
                exerciceList.clear()
                // récolter la liste
                for (ds in snapshot.children) {
                    // construire un objet plante
                    val exercice = ds.getValue(ExerciceModel::class.java)

                    // vérifier que l'exercice n'est pas null
                    if(exercice != null){
                        // ajouter l'exercice à notre liste
                        exerciceList.add(exercice)
                    }
                }
                // actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

}