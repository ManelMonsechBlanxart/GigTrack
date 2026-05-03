package model.material

import model.base.EstatMaterial

class Controladora(
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
        return "Controladora"
    }

    override fun resum(): String {
        return "$nom - Controladora $marca $model - Unitats: $quantitat"
    }
}