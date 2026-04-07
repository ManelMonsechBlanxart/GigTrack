package model.base

abstract class ElementSistema(
    val id: String,
    var nom: String
) {
    abstract fun resum(): String
}