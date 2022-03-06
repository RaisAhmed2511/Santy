package fr.raisahmed.santy

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.raisahmed.santy.adapter.ExerciceAdapter

class ExercicePopup(
    private val adapter : ExerciceAdapter,
    private val currentExercice : ExerciceModel
    ) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // sert à ne pas avoir de titre sur la popup
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_exercice_details)
        setUpComponents()
        setUpCloseButton()
        setUpStarButton()
    }

    private fun updateStar(button : ImageView){
        if(currentExercice.liked){
            button.setImageResource(R.drawable.ic_like)
        } else {
            button.setImageResource(R.drawable.ic_unlike)
        }
    }

    private fun setUpStarButton() {
        // recuperer
        val starButton = findViewById<ImageView>(R.id.like_button)
        updateStar(starButton)
        // interaction
        starButton.setOnClickListener{
            currentExercice.liked = !currentExercice.liked
            val repo = ExerciceRepository()
            repo.updateExercice(currentExercice)
            updateStar(starButton)
        }
    }

    private fun setUpCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            // fermer la popup ( dismiss permet de fermer le composant actuel )
            dismiss()
        }
    }

    private fun setUpComponents() {
        // actualiser l'image de la plante
        val exerciceImage = findViewById<ImageView>(R.id.image_item)
        // on utilise Glide pour pouvoir utiliser des options d'images internet
        // sur notre contexte
        // et ensuite on prend l'url de l'image de l'exercice et on la load
        // dans la variable exerciceImage
        Glide.with(adapter.context).load(Uri.parse(currentExercice.imageUrl)).into(exerciceImage)

        // actualiser les autres éléments de l'exercice
        findViewById<TextView>(R.id.popup_exercice_name).text = currentExercice.name
        findViewById<TextView>(R.id.popup_exercice_little_description).text = currentExercice.description
        findViewById<TextView>(R.id.popup_difficulty_description).text = currentExercice.difficulty

    }

}