package model.material

import model.base.EstatMaterial

class Wash(
    id: String,
    nom: String,
    marca: String,
    model: String,
    preuPerDia: Double,
    disponible: Boolean,
    estat: EstatMaterial,
    potenciaW: Int
) : MaterialLlum(id, nom, marca, model, preuPerDia, disponible, estat, potenciaW) {

    override fun getTipus(): String {
        return "Wash"
    }

    override fun resum(): String {
        return "$nom - Wash $marca $model (${potenciaW}W)"
    }
}