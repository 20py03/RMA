package hr.ferit.zavrsni.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.R
import hr.ferit.zavrsni.components.ButtonComponent
import hr.ferit.zavrsni.components.HeadingTextComponent
import hr.ferit.zavrsni.data.LoginViewModel
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun HeightScreen(navController: NavController, loginViewModel : LoginViewModel = viewModel()) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(28.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        HeadingTextComponent(value = "How tall are you?")

        Spacer(modifier = Modifier.height(80.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = Blue)
        )

        var selectedHeight by remember { mutableStateOf(0) }

        val heights = (150..200).toList()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                items(heights.size) { index ->
                    val height = heights[index]
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 28.dp)
                            .clickable { selectedHeight = height },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = height.toString(),
                            fontSize = 20.sp,
                            color = if (height == selectedHeight) Blue else Color.Black
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = Blue)
        )

        Spacer(modifier = Modifier.height(80.dp))

        ButtonComponent(
            value = stringResource(id = R.string.next),
            onButtonClicked = {
                if (loginViewModel.currentUser != null) {
                    val uid = loginViewModel.currentUser?.uid
                    if (uid != null) {
                        saveHeightToFirestore(selectedHeight, uid, navController)
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Please log in to continue",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            isEnabled = selectedHeight != 0
        )
    }
}

private fun saveHeightToFirestore(height: Int, uid:String, navController:NavController) {
    val db = FirebaseFirestore.getInstance()
    val profileData = hashMapOf(
        "height" to height.toString()
    )
    db.collection("profileData").document(uid)
        .set(profileData, SetOptions.merge())
        .addOnSuccessListener {
            navController.navigate(route =  AppNavigation.ActivityScreen.route)
        }
        .addOnFailureListener { e ->
            Log.d("error","Inside_OnFailureListener: $e")
        }
}
