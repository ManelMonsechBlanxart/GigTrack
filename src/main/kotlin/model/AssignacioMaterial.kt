package model

class AssignacioMaterial(
    val material: Material,
    var quantitat: Int,
    var observacions: String = "",
    var imprescindible: Boolean = false
) {

    fun calcularSubtotal(): Double {
        return material.preuPerDia * quantitat
    }

    fun resum(): String {
        return "${material.nom} x$quantitat - subtotal: ${calcularSubtotal()}€"
    }
}