package model

class Altaveu(
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

    override fun getTipus(): String {
        return "Altaveu"
    }

    override fun resum(): String {
        val tipusAlimentacio = if (actiu) "actiu" else "passiu"
        return "$nom - Altaveu $marca $model (${polzades}\") $tipusAlimentacio"
    }
}