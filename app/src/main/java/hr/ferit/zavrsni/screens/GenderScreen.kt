package hr.ferit.zavrsni.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.R
import hr.ferit.zavrsni.components.ButtonComponent
import hr.ferit.zavrsni.components.HeadingTextComponent
import hr.ferit.zavrsni.data.LoginUIEvent
import hr.ferit.zavrsni.data.LoginViewModel
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.White


@Composable
fun GenderScreen(navController: NavController, loginViewModel:LoginViewModel= viewModel()) {
    var male by remember { mutableStateOf(false) }
    var female by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeadingTextComponent(value = "Tell us about yourself!")

        Spacer(modifier = Modifier.height(50.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .clickable {
                        male = true
                        female = false
                        selectedGender = "male"
                    }
                    .border(
                        width = 3.dp,
                        color = Blue,
                        shape = CircleShape
                    )
                    .background(if (male) Blue else White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.male_symbol_icon),
                    contentDescription = "Male Symbol",
                    modifier = Modifier.size(80.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .clickable {
                        female = true
                        male = false
                        selectedGender = "female"
                    }
                    .border(
                        width = 3.dp,
                        color = Blue,
                        shape = CircleShape
                    )
                    .background(if (female) Blue else White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.woman_symbol_icon),
                    contentDescription = "Female Symbol",
                    modifier = Modifier.size(80.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(50.dp))

        ButtonComponent(
            value = stringResource(id = R.string.next),
            onButtonClicked = {
                if (loginViewModel.currentUser != null) {
                    val uid = loginViewModel.currentUser?.uid
                    if(uid!=null){
                        saveGenderToFirestore(selectedGender, uid, navController)
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Please log in to continue",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            isEnabled = selectedGender.isNotEmpty()
        )
    }
}

private fun saveGenderToFirestore(gender: String, uid:String, navController:NavController) {
    val db = FirebaseFirestore.getInstance()
    val profileData = hashMapOf(
        "gender" to gender
    )

    db.collection("profileData").document(uid)
        .set(profileData)
        .addOnSuccessListener {
            navController.navigate(route =  AppNavigation.YearScreen.route)
        }
        .addOnFailureListener { e ->
            Log.d("error","Inside_OnFailureListener: $e")
        }
}