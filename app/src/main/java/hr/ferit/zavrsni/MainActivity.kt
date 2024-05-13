package hr.ferit.zavrsni

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.LightPink
import hr.ferit.zavrsni.ui.theme.White
import hr.ferit.zavrsni.ui.theme.ZavrsniTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZavrsniTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Blue
                ) {
                    DisplayNavComponent()
                }
            }
        }
    }
}

@Composable
fun DisplayNavComponent(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "StartScreen") {
        composable("StartScreen") {
            StartScreen(navController = navController)
        }
        composable("RegisterScreen") {
            RegisterScreen(navController = navController)
        }
        composable("GenderScreen") {
            GenderScreen(navController = navController)
        }
        composable("YearScreen") {
            YearScreen(navController = navController)
        }
        composable("HeightScreen") {
            HeightScreen(navController = navController)
        }
        composable("WeightScreen") {
            WeightScreen(navController = navController)
        }
        composable("ActivityScreen") {
            ActivityScreen(navController = navController)
        }
        composable("GoalScreen") {
            GoalScreen(navController = navController)
        }
        composable("HomeScreen"){
            HomeScreen(navController = navController)
        }
    }
}

@Composable
fun StartScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
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
                navController.navigate("RegisterScreen")
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















