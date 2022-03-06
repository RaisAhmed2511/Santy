package fr.raisahmed.santy.adapter

import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.raisahmed.santy.ExerciceModel
import fr.raisahmed.santy.ExerciceRepository
import fr.raisahmed.santy.MainActivity
import fr.raisahmed.santy.R

class ExerciceAdapter(
    private val context: MainActivity,
    private val exercicesList: List<ExerciceModel>,
    val layoutId : Int
    ) : RecyclerView.Adapter<ExerciceAdapter.ViewHolder>() {

    // boite pour ranger tout les composants à controler
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val exerciceImage = view.findViewById<ImageView>(R.id.image_item)
        val exerciceName:TextView? = view.findViewById(R.id.name_item)
        val exerciceDescription:TextView? = view.findViewById(R.id.description_id)
        val likeIcon = view.findViewById<ImageView>(R.id.like_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // recuperer les informations de l'exercice
        val currentExercice = exercicesList[position]

        // recupérer le repository
        val repo = ExerciceRepository()

        // utiliser glide pour récupérer l'image à partir de son lien
        Glide.with(context).load(Uri.parse(currentExercice.imageUrl)).into(holder.exerciceImage)

        // mettre à jour le nom et la description de l'exercice
        holder.exerciceName?.text = currentExercice.name
        holder.exerciceDescription?.text = currentExercice.description
        if(currentExercice.liked){
            holder.likeIcon.setImageResource(R.drawable.ic_like)
        } else{
            holder.likeIcon.setImageResource(R.drawable.ic_unlike)
        }

        // rajouter une intéraction sur cette étoile
        holder.likeIcon.setOnClickListener{
            // inverser si le bouton est like ou non
            currentExercice.liked = !currentExercice.liked
            // mettre à jour l'objet exercice
            repo.updateExercice(currentExercice)
        }
    }

    override fun getItemCount(): Int {
        return exercicesList.size
    }

}