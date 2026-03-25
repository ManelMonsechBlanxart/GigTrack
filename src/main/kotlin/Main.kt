import model.*
import java.time.LocalDate

fun main() {
    val client = Client("C1", "Pilu Bar", "600000000", "info@pilu.com")

    val esdeveniment = Esdeveniment(
        "E1",
        "Tardeo Remember",
        LocalDate.now(),
        "Esparreguera",
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

    val assignacio = AssignacioMaterial(altaveu, 2, "Equip principal", true)

    esdeveniment.afegirAssignacio(assignacio)
    client.afegirEsdeveniment(esdeveniment)

    println(client.resum())
    println(esdeveniment.resum())
    println("Cost total material: ${esdeveniment.costTotalMaterial()}€")
}