package model

abstract class Material(
    id: String,
    nom: String,
    var marca: String,
    var model: String,
    var preuPerDia: Double,
    var disponible: Boolean,
    var estat: EstatMaterial
) : ElementSistema(id, nom) {

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