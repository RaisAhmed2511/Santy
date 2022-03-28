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
import fr.raisahmed.santy.*

class ProfilAdapter(
    val context: MainActivity,
    private val profil: ProfilModel,
    val layoutId : Int
    ) : RecyclerView.Adapter<ProfilAdapter.ViewHolder>() {

    // boite pour ranger tout les composants à controler
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val nom:TextView? = view.findViewById(R.id.pseudo)
        val poids:TextView? = view.findViewById(R.id.lepoids)
        val pas:TextView? = view.findViewById(R.id.lespas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // recuperer les informations de l'exercice
        val leprof = profil

        // recupérer le repository
        val repo = ProfilRepository()

        // mettre à jour le nom et la description de l'exercice
        holder.nom?.text = leprof.nom
        holder.poids?.text = leprof.poids.toString()
        holder.pas?.text = leprof.pas.toString()
    }

    override fun getItemCount(): Int {
        return 1
    }

}