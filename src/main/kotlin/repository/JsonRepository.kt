package repository

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import model.base.Identificable
import model.material.Material
import repository.MaterialAdapter
import java.io.File
import java.time.LocalDate

class JsonRepository<T : Identificable>(
    private val filePath: String,
    private val clazz: Class<T>
) : Repository<T> {

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDate::class.java, object : com.google.gson.JsonSerializer<LocalDate> {
            override fun serialize(
                src: LocalDate,
                typeOfSrc: java.lang.reflect.Type,
                context: com.google.gson.JsonSerializationContext
            ) = com.google.gson.JsonPrimitive(src.toString())
        })
        .registerTypeAdapter(LocalDate::class.java, object : com.google.gson.JsonDeserializer<LocalDate> {
            override fun deserialize(
                json: com.google.gson.JsonElement,
                typeOfT: java.lang.reflect.Type,
                context: com.google.gson.JsonDeserializationContext
            ): LocalDate = LocalDate.parse(json.asString)
        })
        .registerTypeHierarchyAdapter(Material::class.java, MaterialAdapter())
        .create()

    override fun save(item: T) {
        try {
            val items = findAll().toMutableList()
            val index = items.indexOfFirst { it.id == item.id }

            if (index >= 0) {
                items[index] = item
            } else {
                items.add(item)
            }

            writeAll(items)
        } catch (e: Exception) {
            println("Error guardant dades: ${e.message}")
        }
    }

    override fun findAll(): List<T> {
        return try {
            val file = File(filePath)

            if (!file.exists()) {
                emptyList()
            } else {
                val json = file.readText()

                if (json.isBlank()) {
                    emptyList()
                } else {
                    val type = TypeToken.getParameterized(List::class.java, clazz).type
                    gson.fromJson(json, type) ?: emptyList()
                }
            }
        } catch (e: Exception) {
            println("Error llegint dades: ${e.message}")
            emptyList()
        }
    }

    override fun delete(id: String) {
        try {
            val items = findAll().filter { it.id != id }
            writeAll(items)
        } catch (e: Exception) {
            println("Error eliminant dades: ${e.message}")
        }
    }

    private fun writeAll(items: List<T>) {
        val file = File(filePath)
        file.parentFile?.mkdirs()

        val json = if (clazz == Material::class.java) {
            val jsonArray = com.google.gson.JsonArray()
            items.forEach {
                jsonArray.add(gson.toJsonTree(it, Material::class.java))
            }
            gson.toJson(jsonArray)
        } else {
            gson.toJson(items)
        }

        file.writeText(json)
    }
}