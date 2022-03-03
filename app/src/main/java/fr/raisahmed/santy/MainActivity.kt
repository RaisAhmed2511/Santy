package fr.raisahmed.santy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.raisahmed.santy.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // injecter le fragment dans notre boîte

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, HomeFragment(this))
        transaction.addToBackStack(null) // pour ne pas avoir de retour sur le composant
        transaction.commit()
    }
}