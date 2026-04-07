package model.material

import model.base.ElementSistema
import model.base.EstatMaterial
import model.exceptions.DadaNoValidaException

abstract class Material(
    id: String,
    nom: String,
    var marca: String,
    var model: String,
    var preuPerDia: Double,
    var disponible: Boolean,
    var estat: EstatMaterial
) : ElementSistema(id, nom) {

    init {
        assert(preuPerDia >= 0) { "El preu per dia no pot ser negatiu" }

        if (marca.isBlank()) {
            throw DadaNoValidaException("La marca no pot estar buida")
        }
        if (model.isBlank()) {
            throw DadaNoValidaException("El model no pot estar buit")
        }
        if (preuPerDia < 0) {
            throw DadaNoValidaException("El preu per dia no pot ser negatiu")
        }
    }

    open fun getTipus(): String {
        return "Material"
    }

    fun marcarDisponible() {
        disponible = true
        estat = EstatMaterial.DISPONIBLE
    }

    fun marcarReservat() {
        disponible = false
        estat = EstatMaterial.RESERVAT
    }

    override fun resum(): String {
        return "$nom - $marca $model (${getTipus()})"
    }
}