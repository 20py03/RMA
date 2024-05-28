package hr.ferit.zavrsni.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalFocusManager
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
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.R
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.DarkBlue
import hr.ferit.zavrsni.ui.theme.DarkGray
import hr.ferit.zavrsni.ui.theme.LightBlue
import hr.ferit.zavrsni.ui.theme.LightGray
import hr.ferit.zavrsni.ui.theme.LightPink
import hr.ferit.zavrsni.ui.theme.White


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
                         errorStatus: Boolean = false
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
            color = DarkBlue,
            thickness = 1.dp
        )

        Text(modifier = Modifier.padding(8.dp),
            text = "or", fontSize = 18.sp, color = DarkBlue)
        
        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color = DarkBlue,
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
fun EmptySquareWithBorder() {
    Box(
        modifier = Modifier
            .size(170.dp)
            .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
            .padding(bottom = 10.dp)
            .border(2.dp, Blue, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {

    }
}

@Composable
fun EmotionLabel(text : String){
    Text(text = text, color= DarkGray, fontSize = 25.sp, fontWeight = FontWeight.Bold)
}






