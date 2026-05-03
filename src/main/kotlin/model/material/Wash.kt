package model.material

import model.base.EstatMaterial

class Wash(
    id: String,
    nom: String,
    marca: String,
    model: String,
    preuPerUnitat: Double,
    disponible: Boolean,
    estat: EstatMaterial,
    quantitat: Int,
    potenciaW: Int
) : MaterialLlum(id, nom, marca, model, preuPerUnitat, disponible, estat, quantitat, potenciaW) {

    override fun getTipus(): String {
        return "Wash"
    }

    override fun resum(): String {
        return "$nom - Wash $marca $model (${potenciaW}W) - Unitats: $quantitat"
    }
}