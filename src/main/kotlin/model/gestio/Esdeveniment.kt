package model.gestio

import model.exceptions.DadaNoValidaException
import model.exceptions.ElementDuplicatException
import model.exceptions.ElementNoTrobatException
import java.time.LocalDate

class Esdeveniment(
    val id: String,
    var titol: String,
    var data: LocalDate,
    var lloc: String,
    var cachet: Double
) {
    val assignacions: MutableList<AssignacioMaterial> = mutableListOf()

    init {
        assert(id.isNotBlank()) { "L'id de l'esdeveniment no pot estar buit" }
        assert(titol.isNotBlank()) { "El títol no pot estar buit" }
        assert(lloc.isNotBlank()) { "El lloc no pot estar buit" }
        assert(cachet >= 0) { "El caixet no pot ser negatiu" }

        if (id.isBlank()) {
            throw DadaNoValidaException("L'id de l'esdeveniment no pot estar buit")
        }
        if (titol.isBlank()) {
            throw DadaNoValidaException("El títol no pot estar buit")
        }
        if (lloc.isBlank()) {
            throw DadaNoValidaException("El lloc no pot estar buit")
        }
        if (cachet < 0) {
            throw DadaNoValidaException("El caixet no pot ser negatiu")
        }
    }

    fun afegirAssignacio(assignacio: AssignacioMaterial) {
        val jaExisteix = assignacions.any { it.material.id == assignacio.material.id }
        if (jaExisteix) {
            throw ElementDuplicatException("Ja existeix una assignació per a aquest material")
        }
        assignacions.add(assignacio)
    }

    fun eliminarAssignacioPerMaterial(materialId: String): Boolean {
        val eliminat = assignacions.removeIf { it.material.id == materialId }
        if (!eliminat) {
            throw ElementNoTrobatException("No s'ha trobat cap assignació amb aquest material")
        }
        return eliminat
    }

    fun cercarAssignacio(materialId: String): AssignacioMaterial {
        return assignacions.find { it.material.id == materialId }
            ?: throw ElementNoTrobatException("No s'ha trobat cap assignació amb aquest material")
    }

    fun costTotalMaterial(): Double {
        return assignacions.sumOf { it.calcularSubtotal() }
    }

    fun obtenirAssignacionsImprescindibles(): List<AssignacioMaterial> {
        return assignacions.filter { it.imprescindible }
    }

    fun obtenirAssignacionsOrdenadesPerSubtotal(): List<AssignacioMaterial> {
        return assignacions.sortedByDescending { it.calcularSubtotal() }
    }

    fun resum(): String {
        return "$titol - $data - $lloc"
    }
}