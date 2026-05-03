package model.material

import model.base.EstatMaterial

abstract class MaterialDJ(
    id: String,
    nom: String,
    marca: String,
    model: String,
    preuPerUnitat: Double,
    disponible: Boolean,
    estat: EstatMaterial,
    quantitat: Int
) : Material(id, nom, marca, model, preuPerUnitat, disponible, estat, quantitat) {

    fun getFamilia(): String {
        return "DJ"
    }
}