package model.material

import model.base.EstatMaterial

abstract class MaterialSo(
    id: String,
    nom: String,
    marca: String,
    model: String,
    preuPerDia: Double,
    disponible: Boolean,
    estat: EstatMaterial
) : Material(id, nom, marca, model, preuPerDia, disponible, estat) {

    fun getFamilia(): String {
        return "So"
    }
}