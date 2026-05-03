package repository

import com.google.gson.*
import model.base.EstatMaterial
import model.material.*
import java.lang.reflect.Type

class MaterialAdapter : JsonSerializer<Material>, JsonDeserializer<Material> {

    override fun serialize(
        src: Material,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val jsonObject = JsonObject()

        jsonObject.addProperty("tipusMaterial", src.getTipus())
        jsonObject.addProperty("id", src.id)
        jsonObject.addProperty("nom", src.nom)
        jsonObject.addProperty("marca", src.marca)
        jsonObject.addProperty("model", src.model)
        jsonObject.addProperty("preuPerUnitat", src.preuPerUnitat)
        jsonObject.addProperty("disponible", src.disponible)
        jsonObject.addProperty("estat", src.estat.name)
        jsonObject.addProperty("quantitat", src.quantitat)

        when (src) {
            is Altaveu -> {
                jsonObject.addProperty("polzades", src.polzades)
                jsonObject.addProperty("actiu", src.actiu)
            }

            is Subwoofer -> {
                jsonObject.addProperty("polzades", src.polzades)
                jsonObject.addProperty("actiu", src.actiu)
            }

            is CapMovil -> {
                jsonObject.addProperty("potenciaW", src.potenciaW)
            }

            is Wash -> {
                jsonObject.addProperty("potenciaW", src.potenciaW)
            }
        }

        return jsonObject
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Material {
        val obj = json.asJsonObject

        val tipus = obj.get("tipusMaterial")?.asString
            ?: throw JsonParseException("Falta el camp tipusMaterial")

        val id = obj.get("id").asString
        val nom = obj.get("nom").asString
        val marca = obj.get("marca").asString
        val modelMaterial = obj.get("model").asString
        val preu = obj.get("preuPerUnitat").asDouble
        val disponible = obj.get("disponible").asBoolean
        val estat = EstatMaterial.valueOf(obj.get("estat").asString)
        val quantitat = obj.get("quantitat")?.asInt ?: 1

        return when (tipus) {
            "Altaveu" -> Altaveu(
                id, nom, marca, modelMaterial, preu, disponible, estat,
                quantitat,
                obj.get("polzades").asInt,
                obj.get("actiu").asBoolean
            )

            "Subwoofer" -> Subwoofer(
                id, nom, marca, modelMaterial, preu, disponible, estat,
                quantitat,
                obj.get("polzades").asInt,
                obj.get("actiu").asBoolean
            )

            "Controladora" -> Controladora(
                id, nom, marca, modelMaterial, preu, disponible, estat, quantitat
            )

            "Reproductor" -> Reproductor(
                id, nom, marca, modelMaterial, preu, disponible, estat, quantitat
            )

            "CapMovil" -> CapMovil(
                id, nom, marca, modelMaterial, preu, disponible, estat,
                quantitat,
                obj.get("potenciaW").asInt
            )

            "Wash" -> Wash(
                id, nom, marca, modelMaterial, preu, disponible, estat,
                quantitat,
                obj.get("potenciaW").asInt
            )

            else -> throw JsonParseException("Tipus de material desconegut: $tipus")
        }
    }
}