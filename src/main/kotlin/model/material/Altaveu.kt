package model.material

import model.base.EstatMaterial
import model.exceptions.DadaNoValidaException

class Altaveu(
    id: String,
    nom: String,
    marca: String,
    model: String,
    preuPerUnitat: Double,
    disponible: Boolean,
    estat: EstatMaterial,
    quantitat: Int,
    var polzades: Int,
    var actiu: Boolean
) : MaterialSo(id, nom, marca, model, preuPerUnitat, disponible, estat, quantitat) {

    init {
        if (polzades <= 0) {
            throw DadaNoValidaException("Les polzades han de ser majors que 0")
        }
    }

    override fun getTipus(): String {
        return "Altaveu"
    }

    override fun resum(): String {
        val tipusAlimentacio = if (actiu) "actiu" else "passiu"
        return "$nom - $marca $model (${polzades}\") $tipusAlimentacio - Unitats: $quantitat"
    }
}