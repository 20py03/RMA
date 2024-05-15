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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import hr.ferit.zavrsni.screens.ActivityScreen
import hr.ferit.zavrsni.screens.GenderScreen
import hr.ferit.zavrsni.screens.GoalScreen
import hr.ferit.zavrsni.screens.HeightScreen
import hr.ferit.zavrsni.screens.HomeScreen
import hr.ferit.zavrsni.screens.ProfileScreen
import hr.ferit.zavrsni.screens.RecipeScreen
import hr.ferit.zavrsni.screens.RegisterScreen
import hr.ferit.zavrsni.screens.WeightScreen
import hr.ferit.zavrsni.screens.YearScreen
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.White
import hr.ferit.zavrsni.ui.theme.ZavrsniTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZavrsniTheme {
                AppNavGraph()
            }
        }
    }
}















