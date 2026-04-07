package model.gestio

import model.exceptions.DadaNoValidaException
import model.exceptions.ElementDuplicatException
import model.exceptions.ElementNoTrobatException

class Client(
    val id: String,
    var nom: String,
    var telefon: String,
    var email: String
) {
    val esdeveniments: MutableList<Esdeveniment> = mutableListOf()

    init {
        assert(id.isNotBlank()) { "L'id del client no pot estar buit" }
        assert(nom.isNotBlank()) { "El nom del client no pot estar buit" }

        if (id.isBlank()) {
            throw DadaNoValidaException("L'id del client no pot estar buit")
        }
        if (nom.isBlank()) {
            throw DadaNoValidaException("El nom del client no pot estar buit")
        }
        if (!validarTelefon(telefon)) {
            throw DadaNoValidaException("El telèfon no té un format vàlid")
        }
        if (!validarEmail(email)) {
            throw DadaNoValidaException("L'email no té un format vàlid")
        }
    }

    fun validarTelefon(telefon: String): Boolean {
        val regex = "^[0-9]{9}$".toRegex()
        return regex.matches(telefon)
    }

    fun validarEmail(email: String): Boolean {
        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return regex.matches(email)
    }

    fun afegirEsdeveniment(esdeveniment: Esdeveniment) {
        val jaExisteix = esdeveniments.any { it.id == esdeveniment.id }
        if (jaExisteix) {
            throw ElementDuplicatException("Ja existeix un esdeveniment amb aquest id")
        }
        esdeveniments.add(esdeveniment)
    }

    fun eliminarEsdeveniment(esdevenimentId: String): Boolean {
        val eliminat = esdeveniments.removeIf { it.id == esdevenimentId }
        if (!eliminat) {
            throw ElementNoTrobatException("No s'ha trobat cap esdeveniment amb aquest id")
        }
        return eliminat
    }

    fun cercarEsdeveniment(esdevenimentId: String): Esdeveniment {
        return esdeveniments.find { it.id == esdevenimentId }
            ?: throw ElementNoTrobatException("No s'ha trobat cap esdeveniment amb aquest id")
    }

    fun obtenirEsdevenimentsOrdenatsPerData(): List<Esdeveniment> {
        return esdeveniments.sortedBy { it.data }
    }

    fun obtenirEsdevenimentsAmbCachetSuperior(importMinim: Double): List<Esdeveniment> {
        return esdeveniments.filter { it.cachet > importMinim }
    }

    fun resum(): String {
        return "$nom - $telefon - $email"
    }
}