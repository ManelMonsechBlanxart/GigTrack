package model

import java.time.LocalDate

class Esdeveniment(
    val id: String,
    var titol: String,
    var data: LocalDate,
    var lloc: String,
    var cachet: Double
) {
    val assignacions: MutableList<AssignacioMaterial> = mutableListOf()

    fun afegirAssignacio(assignacio: AssignacioMaterial) {
        assignacions.add(assignacio)
    }

    fun eliminarAssignacioPerMaterial(materialId: String): Boolean {
        return assignacions.removeIf { it.material.id == materialId }
    }

    fun cercarAssignacio(materialId: String): AssignacioMaterial? {
        return assignacions.find { it.material.id == materialId }
    }

    fun costTotalMaterial(): Double {
        return assignacions.sumOf { it.calcularSubtotal() }
    }

    fun resum(): String {
        return "$titol - $data - $lloc"
    }
}