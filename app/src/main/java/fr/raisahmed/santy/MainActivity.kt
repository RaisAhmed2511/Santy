package fr.raisahmed.santy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.raisahmed.santy.fragments.AddExerciceFragment
import fr.raisahmed.santy.fragments.CollectionFragment
import fr.raisahmed.santy.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this), R.string.trend_exercices)

        // importer la bottomnavigationview
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.trend_exercices)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this), R.string.liked_exercices)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_exercice_page -> {
                    loadFragment(AddExerciceFragment(this), R.string.ajouter)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }



    }

    private fun loadFragment(fragment: Fragment, string : Int) {
        // charger notre Repository
        val repo = ExerciceRepository()

        // actualiser le titre de la page
        findViewById<TextView>(R.id.page_title).text = resources.getString(string)

        // mettre à jour la liste d'exercices
        // le callback dans updateData permet d'effectuer les instructions entre crochets
        // après que les actions dans la méthode updateData soient effectués

        repo.updateData {
            // injecter le fragment dans notre boîte ( fragment_container )
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null) // pour ne pas avoir de retour sur le composant
            transaction.commit()
        }

    }
}