package model.material

import model.exceptions.DadaNoValidaException
import model.base.EstatMaterial

abstract class MaterialLlum(
    id: String,
    nom: String,
    marca: String,
    model: String,
    preuPerDia: Double,
    disponible: Boolean,
    estat: EstatMaterial,
    var potenciaW: Int
) : Material(id, nom, marca, model, preuPerDia, disponible, estat) {

    init {
        assert(potenciaW >= 0) { "La potència no pot ser negativa" }

        if (potenciaW < 0) {
            throw DadaNoValidaException("La potència no pot ser negativa")
        }
    }

    fun getConsum(): Int {
        return potenciaW
    }
}