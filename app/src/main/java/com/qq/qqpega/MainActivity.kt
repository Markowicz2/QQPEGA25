package com.qq.qqpega

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.FirebaseApp
import com.qq.qqpega.login.LoginScreen
import com.qq.qqpega.map.CategoriasSection
import com.qq.qqpega.map.DestaquesSection
import com.qq.qqpega.map.MapScreen
import android.Manifest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            /*MaterialTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen {
                            navController.navigate("map") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                    composable("map") {
                        MapScreen()
                    }
                }
            }*/
            //MapScreen()
            SolicitarPermissaoLocalizacao {
                MapScreen()
            }
            //MapScreen()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1B1534))
                    .padding(16.dp)
            ) {

                CategoriasSection()
                DestaquesSection()
                MapScreen()

            }
            //CategoriasSection()
        }
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SolicitarPermissaoLocalizacao(onPermissaoConcedida: @Composable () -> Unit) {
    val permissoes = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        permissoes.launchMultiplePermissionRequest()
    }

    if (permissoes.allPermissionsGranted) {
        onPermissaoConcedida()
    } else {
        // Apenas exibe uma mensagem caso o usuário ainda não aceitou
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Permissão de localização necessária")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { permissoes.launchMultiplePermissionRequest() }) {
                    Text("Permitir")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1534))
            .padding(16.dp)
    ) {

        CategoriasSection()
        DestaquesSection()

        MapScreen()

    }
}