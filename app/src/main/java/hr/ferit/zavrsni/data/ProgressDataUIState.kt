package hr.ferit.zavrsni.data

data class ProgressDataUIState (
    val beforeWeight: String = "",
    val currentWeight: String = "",
    val afterWeight: String = "",
    val note1: String = "",
    val note2: String = "",
    val steps: Int = 0
)