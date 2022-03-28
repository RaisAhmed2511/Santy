package fr.raisahmed.santy

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
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
import fr.raisahmed.santy.ProfilRepository.Singleton.incrementation
import fr.raisahmed.santy.ProfilRepository.Singleton.laliste
import fr.raisahmed.santy.ProfilRepository.Singleton.profil
import java.net.URI
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class ProfilRepository {

    // Le singleton permet d'accéder aux valeurs qu'il possède dans toute l'application
    // sans avoir à les rafraîchir

    object Singleton{

        // donner le lien pour acceder au bucket
        private val BUCKET_URL : String = "gs://santy-4e19d.appspot.com"

        // se connecter à notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // se connecter à la référence "exercices"
        val databaseRef = FirebaseDatabase.getInstance().getReference("profil")

        // créer une liste qui va contenir nos exercices
        var profil = ProfilModel()
        var laliste = ArrayList<Int>()
        var incrementation = 0

    }

    fun updateData(callback: () -> Unit){
        // absorber les données depuis la databaseRef --> liste d'exercices
        val databaseRef = FirebaseDatabase.getInstance().getReference("profil")
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // récolter la liste
                for (ds in snapshot.children) {
                    // construire un objet plante
                    val prof = ds.getValue(ProfilModel::class.java)

                    if (prof != null) {
                        System.out.println(prof.nom)
                    }

                    // vérifier que l'exercice n'est pas null
                    if(prof != null){
                        profil.nom = prof.nom
                        profil.poids = prof.poids
                        profil.pas = prof.pas
                    }
                }
                // actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }


    fun findPas(callback: () -> Unit){
        // absorber les données depuis la databaseRef --> liste d'exercices
        val databaseRef = FirebaseDatabase.getInstance().getReference("pas")
        laliste.clear()
        var i : Long = 0
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                // récolter la liste
                for (ds in snapshot.children) {
                    // construire un objet plante

                    val prof = ds.getValue(i::class.java)

                    if (prof != null) {
                        System.out.println("la ici" + prof)
                        laliste.add(prof.toInt())
                    }


                }

                // actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}



        })
    }

    fun getValeur(callback: () -> Unit){
        val databaseRef = FirebaseDatabase.getInstance().getReference("icrem")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // récolter la liste
                System.out.println("la fameuse valeur de la mort : " + snapshot.child("icrem1").getValue().toString().toInt())
                incrementation = snapshot.child("icrem1").getValue().toString().toInt()
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}
        })


    }



    // mettre à jour un objet exercice en base de données
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProfil(profile : ProfilModel){
        var databaseRef = FirebaseDatabase.getInstance().getReference("profil")
        databaseRef.child("profile").setValue(profile)

        getValeur{}
        if(incrementation!=0){
            System.out.println("laaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
            databaseRef = FirebaseDatabase.getInstance().getReference("pas")
            databaseRef.child("pas" + incrementation).setValue(profile.pas)
            var databaseRef2 = FirebaseDatabase.getInstance().getReference("icrem")
            System.out.println("le temp : " + incrementation)
            databaseRef2.child("icrem1").setValue(incrementation+1)

        }


    }
}