package hr.ferit.zavrsni.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.R
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun Start(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.7f))
        Text(
            text = "MY FITNESS DIARY",
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            lineHeight = 70.sp,
            color = White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(0.4f))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.gym_dumbbell_icon),
            contentDescription = "Dumbell",
            modifier = Modifier
                .size(80.dp)
                .rotate(45f),
            tint = White
        )
        Spacer(modifier = Modifier.weight(0.4f))
        IconButton(
            onClick= {
                navController.navigate(route = AppNavigation.RegisterScreen.route)
            })
        {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.right_arrow_svgrepo_com),
                contentDescription = "Right arrow",
                modifier = Modifier,
                tint = White
            )
        }
        Spacer(modifier = Modifier.weight(0.7f))
    }
}

@Preview
@Composable
fun StartScreenPreview(){
    Start(navController = rememberNavController())
}