package hr.ferit.zavrsni.data

sealed class ProfileUIEvent {
    data class WeightChanged(val weight:String) : ProfileUIEvent()
    data class HeightChanged(val height:String) : ProfileUIEvent()
    data class AgeChanged(val age:String) : ProfileUIEvent()
    data class ActivityChanged(val activity:String) : ProfileUIEvent()
    data class GoalChanged(val goal:String) : ProfileUIEvent()
    object EditButtonClicked: ProfileUIEvent()

}