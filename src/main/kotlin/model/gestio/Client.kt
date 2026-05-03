package model.gestio

import model.base.Identificable
import model.exceptions.DadaNoValidaException
import model.exceptions.ElementDuplicatException
import model.exceptions.ElementNoTrobatException

class Client(
    override val id: String,
    var nom: String,
    var telefon: String,
    var email: String
) : Identificable {

    private val esdeveniments: MutableList<Esdeveniment> = mutableListOf()

    init {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        val telefonRegex = "^[0-9]{9}$".toRegex()

        if (!emailRegex.matches(email)) {
            throw DadaNoValidaException("Email no vàlid")
        }

        if (!telefonRegex.matches(telefon)) {
            throw DadaNoValidaException("Telèfon no vàlid")
        }
    }

    fun afegirEsdeveniment(esdeveniment: Esdeveniment) {
        if (esdeveniments.any { it.id == esdeveniment.id }) {
            throw ElementDuplicatException("Esdeveniment duplicat")
        }
        esdeveniments.add(esdeveniment)
    }

    fun eliminarEsdeveniment(id: String) {
        val removed = esdeveniments.removeIf { it.id == id }
        if (!removed) {
            throw ElementNoTrobatException("Esdeveniment no trobat")
        }
    }

    fun obtenirEsdevenimentsOrdenatsPerData(): List<Esdeveniment> {
        return esdeveniments.sortedBy { it.data }
    }

    fun resum(): String {
        return "$nom - $telefon - $email"
    }
}