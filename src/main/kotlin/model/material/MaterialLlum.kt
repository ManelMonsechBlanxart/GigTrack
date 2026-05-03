package model.material

import model.base.EstatMaterial
import model.exceptions.DadaNoValidaException

abstract class MaterialLlum(
    id: String,
    nom: String,
    marca: String,
    model: String,
    preuPerUnitat: Double,
    disponible: Boolean,
    estat: EstatMaterial,
    quantitat: Int,
    var potenciaW: Int
) : Material(id, nom, marca, model, preuPerUnitat, disponible, estat, quantitat) {

    init {
        if (potenciaW < 0) {
            throw DadaNoValidaException("La potència no pot ser negativa")
        }
    }

    fun getConsum(): Int {
        return potenciaW
    }
}