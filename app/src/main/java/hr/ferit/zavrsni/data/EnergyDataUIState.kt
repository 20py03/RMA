package hr.ferit.zavrsni.data

data class EnergyDataUIState (
    val energyData: EnergyData = EnergyData()
)
data class EnergyData(
    val tdee: String = "",
    val goalCalories: String = "",
    val protein: String = "",
    val fat: String = "",
    val carbohydrates: String = ""
)
