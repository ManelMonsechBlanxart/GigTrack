package model.material

import model.exceptions.DadaNoValidaException
import model.base.EstatMaterial

class Subwoofer(
    id: String,
    nom: String,
    marca: String,
    model: String,
    preuPerDia: Double,
    disponible: Boolean,
    estat: EstatMaterial,
    var polzades: Int,
    var actiu: Boolean
) : MaterialSo(id, nom, marca, model, preuPerDia, disponible, estat) {

    init {
        assert(polzades > 0) { "Les polzades han de ser majors que 0" }

        if (polzades <= 0) {
            throw DadaNoValidaException("Les polzades han de ser majors que 0")
        }
    }

    override fun getTipus(): String {
        return "Subwoofer"
    }

    override fun resum(): String {
        val tipusAlimentacio = if (actiu) "actiu" else "passiu"
        return "$nom - Subwoofer $marca $model (${polzades}\") $tipusAlimentacio"
    }
}