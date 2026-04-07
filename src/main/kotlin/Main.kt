import model.base.EstatMaterial
import model.gestio.AssignacioMaterial
import model.gestio.Client
import model.gestio.Esdeveniment
import model.material.Altaveu
import model.material.Subwoofer
import java.time.LocalDate

fun main() {
    try {
        val client = Client("C1", "Client Test", "600000000", "test@test.com")

        val esdeveniment = Esdeveniment(
            "E1",
            "Sessió DJ",
            LocalDate.now(),
            "Barcelona",
            350.0
        )

        val altaveu = Altaveu(
            "M1",
            "Altaveu principal",
            "JBL",
            "PRX",
            40.0,
            true,
            EstatMaterial.DISPONIBLE,
            15,
            true
        )

        val subwoofer = Subwoofer(
            "M2",
            "Subwoofer principal",
            "RCF",
            "SUB 8004",
            50.0,
            true,
            EstatMaterial.DISPONIBLE,
            18,
            true
        )

        val assignacio1 = AssignacioMaterial(altaveu, 2, "Equip principal", true)
        val assignacio2 = AssignacioMaterial(subwoofer, 1, "Refors de greus", false)

        esdeveniment.afegirAssignacio(assignacio1)
        esdeveniment.afegirAssignacio(assignacio2)
        client.afegirEsdeveniment(esdeveniment)

        println(client.resum())
        println(esdeveniment.resum())
        println("Cost total material: ${esdeveniment.costTotalMaterial()}€")

        println("Assignacions imprescindibles:")
        esdeveniment.obtenirAssignacionsImprescindibles().forEach {
            println(it.resum())
        }

        println("Esdeveniments ordenats per data:")
        client.obtenirEsdevenimentsOrdenatsPerData().forEach {
            println(it.resum())
        }

    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}