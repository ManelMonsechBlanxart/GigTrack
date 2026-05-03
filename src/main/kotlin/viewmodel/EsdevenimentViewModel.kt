package viewmodel

import androidx.compose.runtime.mutableStateListOf
import model.gestio.Esdeveniment
import repository.Repository

class EsdevenimentViewModel(
    private val repository: Repository<Esdeveniment>
) {
    private val _events = mutableStateListOf<Esdeveniment>()
    val events: List<Esdeveniment> get() = _events

    init {
        _events.addAll(repository.findAll())
    }

    fun guardarEsdeveniment(esdeveniment: Esdeveniment) {
        repository.save(esdeveniment)

        val index = _events.indexOfFirst { it.id == esdeveniment.id }
        if (index >= 0) {
            _events[index] = esdeveniment
        } else {
            _events.add(esdeveniment)
        }
    }

    fun eliminarEsdeveniment(id: String) {
        repository.delete(id)
        _events.removeIf { it.id == id }
    }

    fun obtenirEsdeveniments(): List<Esdeveniment> {
        return events
    }
}