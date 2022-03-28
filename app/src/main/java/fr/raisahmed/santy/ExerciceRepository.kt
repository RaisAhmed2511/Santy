package fr.raisahmed.santy

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.raisahmed.santy.ExerciceRepository.Singleton.databaseRef
import fr.raisahmed.santy.ExerciceRepository.Singleton.downloadUri
import fr.raisahmed.santy.ExerciceRepository.Singleton.exerciceList
import fr.raisahmed.santy.ExerciceRepository.Singleton.storageReference
import java.net.URI
import java.util.*

class ExerciceRepository {

    // Le singleton permet d'accéder aux valeurs qu'il possède dans toute l'application
    // sans avoir à les rafraîchir

    object Singleton{

        // donner le lien pour acceder au bucket
        private val BUCKET_URL : String = "gs://santy-4e19d.appspot.com"

        // se connecter à notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // se connecter à la référence "exercices"
        val databaseRef = FirebaseDatabase.getInstance().getReference("exercices")

        // créer une liste qui va contenir nos exercices
        val exerciceList = arrayListOf<ExerciceModel>()

        // contenir le lien de l'image courante
        var downloadUri : Uri? = null
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

    // créer unne fonction pour envoyer des fichiers sur le storage
    fun uploadImage(file : Uri, callback: () -> Unit){
        // vérifier que ce fichier n'est pas null
        if(file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            // démarrer la tache d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                // si il y a eu un problème lros de l'envoi du fichier
                if(!task.isSuccessful){
                    task.exception?.let { throw it }
                }

                return@Continuation ref.downloadUrl
            }).addOnCompleteListener{ task ->
                // verifier si tout a bien fonctionné
                if(task.isSuccessful){
                    // récupérer l'image
                    downloadUri = task.result
                    callback()
                }
            }
        }
    }

    // mettre à jour un objet exercice en base de données
    fun updateExercice(exercice : ExerciceModel){
        databaseRef.child(exercice.id).setValue(exercice)
    }

    // inserer un nouvel exercice
    fun insertExercice(exercice : ExerciceModel){
        databaseRef.child(exercice.id).setValue(exercice)
    }
}