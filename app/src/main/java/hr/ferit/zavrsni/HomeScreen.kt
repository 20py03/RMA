package hr.ferit.zavrsni

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.DarkBlue
import hr.ferit.zavrsni.ui.theme.LightPink
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(340.dp)
                .fillMaxSize()
                .padding(10.dp)
                .background(color = Blue, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Text(
                        text = "1800 \nkcal eaten",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "200 \nkcal left",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "Carbs\n 120G",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Protein\n 130G",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Fat\n 80G",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                val (iconColors, setIconColors) = remember {
                    mutableStateOf(List(7) { LightPink })
                }

                // Funkcija za promjenu boje ikone na klik
                fun toggleIconColor(index: Int) {
                    val newColors = iconColors.toMutableList()
                    newColors[index] = if (iconColors[index] == LightPink) DarkBlue else LightPink
                    setIconColors(newColors)
                }

                // Kompozicija koja sadrži red s ikonama
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // IconButton komponenta za svaku ikonu
                    repeat(7) { index ->
                        IconButton(onClick = { toggleIconColor(index) }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.water_glass_color_icon),
                                contentDescription = "Right arrow",
                                modifier = Modifier,
                                tint = iconColors[index]
                            )
                        }
                    }
                }

            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            EmptySquareWithBorder()
            EmptySquareWithBorder()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            EmptySquareWithBorder()
            EmptySquareWithBorder()
        }

        Footer(navController = navController)
        }
}

@Composable
fun EmptySquareWithBorder() {
    Box(
        modifier = Modifier
            .size(170.dp)
            .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
            .padding(bottom = 10.dp)
            .border(1.dp, Color.Blue, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {

    }
}


