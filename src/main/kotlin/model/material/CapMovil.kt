package model.material

import model.base.EstatMaterial

class CapMovil(
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
        return "CapMovil"
    }

    override fun resum(): String {
        return "$nom - Cap mòbil $marca $model (${potenciaW}W) - Unitats: $quantitat"
    }
}