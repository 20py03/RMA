package hr.ferit.zavrsni

import android.app.Application
import com.google.firebase.FirebaseApp

class FitnessApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}