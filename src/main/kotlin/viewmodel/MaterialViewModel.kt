package viewmodel

import androidx.compose.runtime.mutableStateListOf
import model.material.Material
import repository.Repository

class MaterialViewModel(
    private val repository: Repository<Material>
) {
    private val _materials = mutableStateListOf<Material>()
    val materials: List<Material> get() = _materials

    init {
        _materials.addAll(repository.findAll())
    }

    fun guardarMaterial(material: Material) {
        repository.save(material)

        val index = _materials.indexOfFirst { it.id == material.id }
        if (index >= 0) {
            _materials[index] = material
        } else {
            _materials.add(material)
        }
    }

    fun eliminarMaterial(id: String) {
        repository.delete(id)
        _materials.removeIf { it.id == id }
    }

    fun obtenirMaterials(): List<Material> {
        return materials
    }
}