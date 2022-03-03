package fr.raisahmed.santy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.raisahmed.santy.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // charger notre Repository
        val repo = ExerciceRepository()

        // mettre à jour la liste d'exercices
        // le callback dans updateData permet d'effectuer les instructions entre crochets
        // après que les actions dans la méthode updateData soient effectués

        repo.updateData {
            // injecter le fragment dans notre boîte ( fragment_container )
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, HomeFragment(this))
            transaction.addToBackStack(null) // pour ne pas avoir de retour sur le composant
            transaction.commit()
        }

    }
}