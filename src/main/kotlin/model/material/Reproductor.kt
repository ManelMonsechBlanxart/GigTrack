package model.material

import model.base.EstatMaterial

class Reproductor(
    id: String,
    nom: String,
    marca: String,
    model: String,
    preuPerUnitat: Double,
    disponible: Boolean,
    estat: EstatMaterial,
    quantitat: Int
) : MaterialDJ(id, nom, marca, model, preuPerUnitat, disponible, estat, quantitat) {

    override fun getTipus(): String {
        return "Reproductor"
    }

    override fun resum(): String {
        return "$nom - Reproductor $marca $model - Unitats: $quantitat"
    }
}