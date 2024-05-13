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
            val navController = rememberNavController()
            ZavrsniTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Blue
                ) {
                    NavHost(navController = navController, startDestination = "main_screen") {
                        composable("main_screen") {
                            StartScreen(navController = navController)
                        }
                        composable("registration_screen") {
                            RegisterScreen(navController = navController)
                        }
                        composable("gender_screen"){
                            GenderScreen(navController = navController)
                        }
                        composable("year_screen"){
                            YearScreen(navController = navController)
                        }
                        composable("height_screen"){
                            HeightScreen(navController = navController)
                        }
                        composable("weight_screen"){
                            WeightScreen(navController = navController)
                        }
                        composable("activity_screen"){
                            ActivityScreen(navController = navController)
                        }
                        composable("goal_screen"){
                            GoalScreen(navController = navController)
                        }
                    }
                }
            }
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
                navController.navigate("registration_screen")
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
                onClick = { navController.navigate("gender_screen")},
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

@Composable
fun GenderScreen(navController: NavController) {
    var isCircle1Selected by remember { mutableStateOf(false) }
    var isCircle2Selected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tell us about yourself!",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .clickable { isCircle1Selected = !isCircle1Selected }
                    .border(
                        width = 3.dp,
                        color = Blue,
                        shape = CircleShape
                    )
                    .background(if (isCircle1Selected) Blue else White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.male_symbol_icon),
                    contentDescription = "Circle 1",
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
                    .clickable { isCircle2Selected = !isCircle2Selected }
                    .border(
                        width = 3.dp,
                        color = Blue,
                        shape = CircleShape
                    )
                    .background(if (isCircle2Selected) Blue else White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.woman_symbol_icon),
                    contentDescription = "Circle 2",
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Button(
            onClick = {navController.navigate("year_screen") },
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            modifier = Modifier
                .padding(top = 16.dp)
                .height(56.dp)
                .fillMaxWidth(0.7f)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

    }
}

@Composable
fun YearScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "How old are you?",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 70.dp, bottom = 150.dp)
        )

        Box(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(0.3f)
                .height(10.dp)
                .background(color = Blue)
        )

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        var selectedYear by remember { mutableStateOf(currentYear) }

        val years = (10..80).toList()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 25.dp)
            ) {
                items(years.take(100).count()) { index ->
                    val year = years[index]
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 40.dp)
                            .clickable { selectedYear = year },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = year.toString(),
                            fontSize = 20.sp,
                            color = if (year == selectedYear) Color.Blue else Color.Black
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .padding(vertical = 16.dp)
                .height(10.dp)
                .background(color = Blue)
        )

        Button(
            onClick = { navController.navigate("height_screen")},
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            modifier = Modifier
                .padding(top = 130.dp)
                .height(56.dp)
                .fillMaxWidth(0.7f)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun HeightScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "How tall are you?",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 70.dp, bottom = 150.dp)
        )

        Box(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(0.3f)
                .height(10.dp)
                .background(color = Blue)
        )

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        var selectedYear by remember { mutableStateOf(currentYear) }

        val years = (150..200).toList()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 25.dp)
            ) {
                items(years.take(100).count()) { index ->
                    val year = years[index]
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 33.dp)
                            .clickable { selectedYear = year },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = year.toString(),
                            fontSize = 20.sp,
                            color = if (year == selectedYear) Color.Blue else Color.Black
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .padding(vertical = 16.dp)
                .height(10.dp)
                .background(color = Blue)
        )

        Button(
            onClick = {navController.navigate("weight_screen") },
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            modifier = Modifier
                .padding(top = 130.dp)
                .height(56.dp)
                .fillMaxWidth(0.7f)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun WeightScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "What is your weight?",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 70.dp, bottom = 150.dp)
        )

        Box(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(0.3f)
                .height(10.dp)
                .background(color = Blue)
        )

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        var selectedYear by remember { mutableStateOf(currentYear) }

        val years = (50..110).toList()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 25.dp)
            ) {
                items(years.take(100).count()) { index ->
                    val year = years[index]
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 39.dp)
                            .clickable { selectedYear = year },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = year.toString(),
                            fontSize = 20.sp,
                            color = if (year == selectedYear) Color.Blue else Color.Black
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .padding(vertical = 16.dp)
                .height(10.dp)
                .background(color = Blue)
        )

        Button(
            onClick = { navController.navigate("activity_screen")},
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            modifier = Modifier
                .padding(top = 130.dp)
                .height(56.dp)
                .fillMaxWidth(0.7f)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ActivityScreen(navController: NavController) {
    var selectedActivity by remember { mutableStateOf("") }

    val activityLevels = listOf(
        "Sedentary",
        "Lightly Active",
        "Moderately Active",
        "Very Active",
        "Athlete"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "How active are you?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 25.dp)
        )

        activityLevels.forEach { level ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { selectedActivity = level })
                    .padding(8.dp)
                    .background(
                        color = if (level == selectedActivity) LightPink else Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = level,
                    fontSize = 18.sp,
                    color = if (level == selectedActivity) Blue else LightPink,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Button(
            onClick = { navController.navigate("goal_screen")},
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            modifier = Modifier
                .padding(top = 60.dp)
                .height(56.dp)
                .fillMaxWidth(0.7f)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun GoalScreen(navController: NavController) {
    var selectedActivity by remember { mutableStateOf("") }

    val activityLevels = listOf(
        "Lose weight",
        "Build muscle",
        "Get leaner",
        "Flexibility",
        "Improve health"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "What is your goal?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 25.dp)
        )

        activityLevels.forEach { level ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { selectedActivity = level })
                    .padding(8.dp)
                    .background(
                        color = if (level == selectedActivity) LightPink else Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = level,
                    fontSize = 18.sp,
                    color = if (level == selectedActivity) Blue else LightPink,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Button(
            onClick = { navController.navigate("goal_screen")},
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            modifier = Modifier
                .padding(top = 60.dp)
                .height(56.dp)
                .fillMaxWidth(0.7f)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}





