package model.gestio

import model.exceptions.DadaNoValidaException

class AssignacioMaterial(
    var materialId: String,
    var quantitat: Int
) {

    init {
        if (materialId.isBlank()) {
            throw DadaNoValidaException("L'id del material no pot estar buit")
        }

        if (quantitat <= 0) {
            throw DadaNoValidaException("La quantitat ha de ser major que 0")
        }
    }

    fun resum(): String {
        return "$materialId x$quantitat"
    }
}