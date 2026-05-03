package model.material

import model.base.EstatMaterial
import model.base.Identificable
import model.exceptions.DadaNoValidaException

abstract class Material(
    override val id: String,
    var nom: String,
    var marca: String,
    var model: String,
    var preuPerUnitat: Double,
    var disponible: Boolean,
    var estat: EstatMaterial,
    var quantitat: Int
) : Identificable {

    init {
        assert(id.isNotBlank()) { "L'id no pot estar buit" }
        assert(nom.isNotBlank()) { "El nom no pot estar buit" }
        assert(preuPerUnitat >= 0) { "El preu no pot ser negatiu" }
        assert(quantitat >= 0) { "La quantitat no pot ser negativa" }

        if (id.isBlank()) {
            throw DadaNoValidaException("L'id no pot estar buit")
        }
        if (nom.isBlank()) {
            throw DadaNoValidaException("El nom no pot estar buit")
        }
        if (preuPerUnitat < 0) {
            throw DadaNoValidaException("El preu no pot ser negatiu")
        }
        if (quantitat < 0) {
            throw DadaNoValidaException("La quantitat no pot ser negativa")
        }
    }

    abstract fun getTipus(): String

    abstract fun resum(): String
}