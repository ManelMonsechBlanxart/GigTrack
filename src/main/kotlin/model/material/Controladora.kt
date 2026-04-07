package model.material

import model.base.EstatMaterial

class Controladora(
    id: String,
    nom: String,
    marca: String,
    model: String,
    preuPerDia: Double,
    disponible: Boolean,
    estat: EstatMaterial
) : MaterialDJ(id, nom, marca, model, preuPerDia, disponible, estat) {

    override fun getTipus(): String {
        return "Controladora"
    }

    override fun resum(): String {
        return "$nom - Controladora $marca $model"
    }
}