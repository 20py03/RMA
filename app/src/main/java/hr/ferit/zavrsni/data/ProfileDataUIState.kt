package hr.ferit.zavrsni.data

data class ProfileDataUIState(
    var name: String="",
    val gender: String="",
    val age:String="",
    val weight:String="",
    val height:String="",
    val activity:String="",
    val goal:String="",
)

fun ProfileDataUIState.toMap(): Map<String, Any> {
    return mapOf(
        "name" to name,
        "age" to age,
        "weight" to weight,
        "height" to height,
        "goal" to goal,
        "activity" to activity,
        // add other fields here
    )
}


