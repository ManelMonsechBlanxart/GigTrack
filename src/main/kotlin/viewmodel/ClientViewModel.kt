package viewmodel

import androidx.compose.runtime.mutableStateListOf
import model.gestio.Client
import repository.Repository

class ClientViewModel(
    private val repository: Repository<Client>
) {
    private val _clients = mutableStateListOf<Client>()
    val clients: List<Client> get() = _clients

    init {
        _clients.addAll(repository.findAll())
    }

    fun guardarClient(client: Client) {
        repository.save(client)

        val index = _clients.indexOfFirst { it.id == client.id }
        if (index >= 0) {
            _clients[index] = client
        } else {
            _clients.add(client)
        }
    }

    fun eliminarClient(id: String) {
        repository.delete(id)
        _clients.removeIf { it.id == id }
    }

    fun obtenirClients(): List<Client> {
        return clients
    }
}