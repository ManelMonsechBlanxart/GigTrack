package model.gestio

import model.base.Identificable
import model.exceptions.DadaNoValidaException
import java.time.LocalDate

class Esdeveniment(
    override val id: String,
    var titol: String,
    var data: LocalDate,
    var lloc: String,
    var cachet: Double,
    var clientId: String,
    var assignacionsMaterial: MutableList<AssignacioMaterial> = mutableListOf()
) : Identificable {

    init {
        if (id.isBlank()) {
            throw DadaNoValidaException("L'id de l'esdeveniment no pot estar buit")
        }
        if (titol.isBlank()) {
            throw DadaNoValidaException("El títol no pot estar buit")
        }
        if (lloc.isBlank()) {
            throw DadaNoValidaException("El lloc no pot estar buit")
        }
        if (clientId.isBlank()) {
            throw DadaNoValidaException("Cal seleccionar un client")
        }
        if (cachet < 0) {
            throw DadaNoValidaException("El preu no pot ser negatiu")
        }
    }

    fun resum(): String {
        return "$titol - $data - $lloc"
    }
}