package hr.ferit.zavrsni

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.zavrsni.ui.theme.Blue

@Composable
fun RegisterScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create your account",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Blue,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 3.dp,
                        color = Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 3.dp,
                        color = Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 3.dp,
                        color = Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Confirm Password") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 3.dp,
                        color = Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Button(
                onClick = { navController.navigate("GenderScreen")},
                colors = ButtonDefaults.buttonColors(containerColor = Blue),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(56.dp)
                    .fillMaxWidth(0.7f)
                    .clip(RoundedCornerShape(8.dp)),
            ) {
                Text(
                    text = "Create",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}
