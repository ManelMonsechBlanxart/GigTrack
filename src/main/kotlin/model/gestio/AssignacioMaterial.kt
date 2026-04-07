package model.gestio

import model.exceptions.DadaNoValidaException
import model.material.Material

class AssignacioMaterial(
    val material: Material,
    var quantitat: Int,
    var observacions: String = "",
    var imprescindible: Boolean = false
) {

    init {
        assert(quantitat > 0) { "La quantitat ha de ser major que 0" }

        if (quantitat <= 0) {
            throw DadaNoValidaException("La quantitat ha de ser major que 0")
        }
    }

    fun calcularSubtotal(): Double {
        return material.preuPerDia * quantitat
    }

    fun resum(): String {
        return "${material.nom} x$quantitat - subtotal: ${calcularSubtotal()}€"
    }
}