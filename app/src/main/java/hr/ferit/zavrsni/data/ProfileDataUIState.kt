package hr.ferit.zavrsni.data

data class ProfileDataUIState (
    val name: String="",
    val gender: String="",
    val age:String="",
    val weight:String="",
    val height:String="",
    val activity:String="",
    val goal:String="",

    val beforeWeight: String = "",
    val currentWeight: String = "",
    val afterWeight: String = "",
    val note1: String = "",
    val note2: String = "",
)
