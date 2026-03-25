package model

class Client(
    val id: String,
    var nom: String,
    var telefon: String,
    var email: String
) {
    val esdeveniments: MutableList<Esdeveniment> = mutableListOf()

    fun afegirEsdeveniment(esdeveniment: Esdeveniment) {
        esdeveniments.add(esdeveniment)
    }

    fun eliminarEsdeveniment(esdevenimentId: String): Boolean {
        return esdeveniments.removeIf { it.id == esdevenimentId }
    }

    fun cercarEsdeveniment(esdevenimentId: String): Esdeveniment? {
        return esdeveniments.find { it.id == esdevenimentId }
    }

    fun resum(): String {
        return "$nom - $telefon - $email"
    }
}