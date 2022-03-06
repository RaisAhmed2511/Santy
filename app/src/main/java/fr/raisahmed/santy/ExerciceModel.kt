package fr.raisahmed.santy

class ExerciceModel (
        val id: String = "exercice0",
        val name: String = "Pompe",
        val description: String = "Petite description",
        val imageUrl: String = "http://graven.yt/plante.jpg",
        val difficulty : String = "Facile",
        var liked: Boolean = false
        )