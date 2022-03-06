package fr.raisahmed.santy.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.raisahmed.santy.ExerciceModel
import fr.raisahmed.santy.ExerciceRepository
import fr.raisahmed.santy.ExerciceRepository.Singleton.downloadUri
import fr.raisahmed.santy.MainActivity
import fr.raisahmed.santy.R
import org.w3c.dom.Text
import java.util.*

@Suppress("DEPRECATION")
class AddExerciceFragment(
    private val context : MainActivity
) : Fragment(){

    private var file : Uri? = null
    private var uploadedImage : ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_exercice, container, false)

        // récupérer uploadedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        // recuperer le button pour charger
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        // lorsqu'on clique dessus ça ouvre les images du telephone
        pickupImageButton.setOnClickListener{
            pickupImage()
        }

        // recuperer le bouton confirmer
        val confirmCutton = view.findViewById<Button>(R.id.confirm_button)
        confirmCutton.setOnClickListener{ sendForm(view) }

        return view
    }

    private fun sendForm(view : View) {
        val repo = ExerciceRepository()

        repo.uploadImage(file!!){
            val exerciceName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val exerciceDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val difficulty = view.findViewById<Spinner>(R.id.difficulty_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri

            // creer un nouvel objet ExerciceModel
            val exercice = ExerciceModel(
                UUID.randomUUID().toString(),
                exerciceName,
                exerciceDescription,
                downloadImageUrl.toString(),
                difficulty
            )

            // envoyer en bdd
            repo.insertExercice(exercice)
        }

    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK){
            // verifier si les données réceptionnés sont nulles
            if(data == null || data.data == null) return

            // recupérer l'image
            file = data.data

            // mettre à jour l'aperçu de l'image
            uploadedImage?.setImageURI(file)

        }
    }

}