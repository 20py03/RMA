package hr.ferit.zavrsni.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.R
import hr.ferit.zavrsni.data.Food
import hr.ferit.zavrsni.data.RecipeViewModel
import hr.ferit.zavrsni.data.Recipes.Recipe
import hr.ferit.zavrsni.data.validation.Validator
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.DarkGray
import hr.ferit.zavrsni.ui.theme.LightGray
import hr.ferit.zavrsni.ui.theme.White
import kotlinx.coroutines.delay


@Composable
fun NormalTextComponent(value:String){
    Text(
        text=value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = Blue,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(value:String){
    Text(
        text=value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = Blue,
        textAlign = TextAlign.Center
    )
}

@Composable
fun MyTextFieldComponent(labelValue: String, painterResource: Painter,
                         onTextSelected: (String) -> Unit,
                         errorStatus: Boolean = false,
                         errorMessage: String = ""
) {
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Blue,
            cursorColor = DarkGray,
            focusedTextColor = Blue,
            unfocusedTextColor = Blue,
            focusedContainerColor = White,
            unfocusedContainerColor = LightGray,
            errorContainerColor = White,
            errorTextColor = DarkGray
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = "")
        },
        isError = !errorStatus
    )
}

@Composable
fun PwdTextFieldComponent(labelValue: String, painterResource: Painter, onTextSelected: (String) -> Unit, errorStatus: Boolean =false) {
    val password = remember { mutableStateOf("") }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Blue,
            cursorColor = DarkGray,
            focusedTextColor = Blue,
            unfocusedTextColor = Blue,
            focusedContainerColor = White,
            unfocusedContainerColor = LightGray,
            errorContainerColor = White,
            errorTextColor = DarkGray
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = "")
        },
        isError = !errorStatus,
        trailingIcon = {
            val iconImage = if(passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription ="" )
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun ConfirmPwdTextFieldComponent(labelValue: String, painterResource: Painter, onTextSelected: (String) -> Unit, errorStatus: Boolean =false) {
    val password = remember { mutableStateOf("") }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Blue,
            cursorColor = DarkGray,
            focusedTextColor = Blue,
            unfocusedTextColor = Blue,
            focusedContainerColor = White,
            unfocusedContainerColor = LightGray,
            errorContainerColor = White,
            errorTextColor = DarkGray
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        maxLines = 1,
        value = password.value,
        onValueChange = {
            val sanitizedInput = it.replace(" ", "")
            password.value = sanitizedInput
            onTextSelected(sanitizedInput)
        },
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = "")
        },
        isError = !errorStatus,
        trailingIcon = {
            val iconImage = if(passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription ="" )
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun LoginPwdTextFieldComponent(labelValue: String, painterResource: Painter, onTextSelected: (String) -> Unit, errorStatus: Boolean =false) {
    val password = remember { mutableStateOf("") }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Blue,
            cursorColor = DarkGray,
            focusedTextColor = Blue,
            unfocusedTextColor = Blue,
            focusedContainerColor = White,
            unfocusedContainerColor = LightGray,
            errorContainerColor = White,
            errorTextColor = DarkGray
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = "")
        },
        isError = !errorStatus,
        trailingIcon = {
            val iconImage = if(passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription ="" )
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
    )
}

@Composable
fun ButtonComponent(value:String, onButtonClicked: () -> Unit, isEnabled:Boolean=false){
    Button(
        onClick = {
                onButtonClicked.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnabled
        ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Blue, LightGray)),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkGray)
        }
    }
}

@Composable
fun DividerComponent(){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color = Blue,
            thickness = 1.dp
        )

        Text(modifier = Modifier.padding(8.dp),
            text = "or", fontSize = 18.sp, color = DarkGray)
        
        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color = Blue,
            thickness = 1.dp
        )
    }
}

@Composable
fun ClicableLoginTextComponent(tryingToLogin: Boolean=true , onTextSelected : (String) -> Unit){
    val initialtext = if(tryingToLogin) "Already have an account?" else "Don't have an account yet?"
    val loginText = if(tryingToLogin) " Login" else " Register"

    val annotatedString = buildAnnotatedString {
        append(initialtext)
        withStyle(style = SpanStyle(color = Blue)){
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text=annotatedString,
        onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also {span->
                Log.d("ClicableTextComponent", "{${span.item}}")

                if (span.item == loginText){
                    onTextSelected(span.item)
                }
            }

    })
}

@Composable
fun Footer(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FooterIcon(imageVector = ImageVector.vectorResource(id = R.drawable.home_icon)) {
            navController.navigate(route = AppNavigation.HomeScreen.route)
        }
        FooterIcon(imageVector = ImageVector.vectorResource(id = R.drawable.challenge_target_icon)) {
            navController.navigate(route = AppNavigation.ProgressScreen.route)
        }
        FooterIcon(imageVector = ImageVector.vectorResource(id = R.drawable.cooking_chef_cap_icon)) {
            navController.navigate(route = AppNavigation.RecipeScreen.route)
        }
        FooterIcon(imageVector = ImageVector.vectorResource(id = R.drawable.person_profile_image_icon)) {
            navController.navigate(route = AppNavigation.ProfileScreen.route)
        }
    }
}

@Composable
fun FooterIcon(imageVector: ImageVector, onClick: () -> Unit) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = DarkGray,
        modifier = Modifier
            .size(40.dp)
            .clickable(onClick = onClick)
    )
}


@Composable
fun MealSquare(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(160.dp)
            .padding(10.dp)
            .border(2.dp, Blue, RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, fontSize = 18.sp, color = DarkGray)
        }
    }
}
@Composable
fun EmotionLabel(text : String){
    Text(text = text, color= DarkGray, fontSize = 25.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun ClicableTextComponent(onTextSelected : (String) -> Unit){
    val initialtext = "Add your product "
    val addText = "here"

    val annotatedString = buildAnnotatedString {
        append(initialtext)
        withStyle(style = SpanStyle(color = Blue)){
            pushStringAnnotation(tag = initialtext, annotation = addText)
            append(addText)
        }
    }

    ClickableText(modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text=annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also {span->
                    Log.d("ClicableTextComponent", "{${span.item}}")

                    if (span.item == addText){
                        onTextSelected(span.item)
                    }
                }

        })
}

@Composable
fun ProfileImage(imageResource: Int) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(color = Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = imageResource),
            contentDescription = "Profile picture",
            modifier = Modifier.size(100.dp),
            tint = DarkGray
        )
    }
}

@Composable
fun ProfileInfo(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Blue,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
fun RecipeList(recipes: List<Recipe>, recipeViewModel: RecipeViewModel) {
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            recipeViewModel.fetchRecipes("feca3b7189e0457ca2ceba2b6a089ee6")
            delay(500)
            refreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing = true },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(recipes) { recipe ->
                RecipeItem(recipe)
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blue
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(recipe.image),
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = recipe.title ?: "N/A",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Ready in minutes: ${recipe.readyInMinutes}",
                fontSize = 18.sp,
                color = DarkGray
            )
            Text(
                text = "Servings: ${recipe.servings}",
                fontSize = 18.sp,
                color = DarkGray
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Instructions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )
            Text(
                text = recipe.instructions ?: "No instructions available",
                fontSize = 16.sp,
                color = DarkGray
            )
        }
    }
}

@Composable
fun AddFoodDialog(food: Food, onDismiss: () -> Unit, onAdd: (Int) -> Unit) {
    var grams by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add ${food.name}") },
        text = {
            Column {
                Text("Enter the amount in grams:")
                TextField(
                    value = grams,
                    onValueChange = { grams = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val gramsInt = grams.toIntOrNull() ?: 0
                if (gramsInt > 0) {
                    onAdd(gramsInt)
                }
                onDismiss()
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}


@Composable
fun ProfileTextField(label: String, state: MutableState<TextFieldValue>) {
    Column {
        Text(text = label, color = Blue, modifier = Modifier.padding(8.dp))
        BasicTextField(
            value = state.value,
            onValueChange = { state.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}