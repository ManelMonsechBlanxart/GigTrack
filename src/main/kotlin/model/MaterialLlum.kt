package model

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

    fun getConsum(): Int {
        return potenciaW
    }
}