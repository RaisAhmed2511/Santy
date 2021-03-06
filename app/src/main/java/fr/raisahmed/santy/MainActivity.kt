package fr.raisahmed.santy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.raisahmed.santy.fragments.*

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
                R.id.profil -> {
                    loadFragment(ProfilFragment(this), R.string.profil)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.graph -> {
                    loadFragment(GraphFragment(this), R.string.graph)
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

        // mettre ?? jour la liste d'exercices
        // le callback dans updateData permet d'effectuer les instructions entre crochets
        // apr??s que les actions dans la m??thode updateData soient effectu??s

        repo.updateData {
            // injecter le fragment dans notre bo??te ( fragment_container )
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null) // pour ne pas avoir de retour sur le composant
            transaction.commit()
        }

    }
}