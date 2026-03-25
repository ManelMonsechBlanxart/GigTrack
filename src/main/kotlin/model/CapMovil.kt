package model

class CapMovil(
    id: String,
    nom: String,
    marca: String,
    model: String,
    preuPerDia: Double,
    disponible: Boolean,
    estat: EstatMaterial,
    potenciaW: Int
) : MaterialLlum(id, nom, marca, model, preuPerDia, disponible, estat, potenciaW) {

    override fun getTipus(): String {
        return "CapMovil"
    }

    override fun resum(): String {
        return "$nom - Cap mòbil $marca $model (${potenciaW}W)"
    }
}