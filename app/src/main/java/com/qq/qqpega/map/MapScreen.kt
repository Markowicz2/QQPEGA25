package com.qq.qqpega.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.core.app.ActivityCompat
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.*
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
//import com.google.android.libraries.maps.model.CameraPosition
import com.google.maps.android.compose.*
import com.qq.qqpega.ui.theme.Purple40
import com.qq.qqpega.ui.theme.Purple80

@SuppressLint("MissingPermission")
@Composable
fun MapScreen() {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var userLocation by remember { mutableStateOf(LatLng(-15.7942, -47.8822)) } // Brasília como padrão

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                }
            }
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation as LatLng, 15f)
    }

    GoogleMap(
        modifier = Modifier.size(200.dp),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true)
    ) {
        Marker(
            state = MarkerState(position = userLocation),
            title = "Você está aqui"
        )
    }
}
@Composable
fun DestaquesSection() {
    Column {
        Text(
            "Em Destaque",
           //color = Color(0xFFF3E7C8),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            item {
                DestaqueCard(
                    titulo = "Bar do João",
                    subtitulo = "Promoção ★ 4,5",
                    tag = "Promoção",

                )
            }
            item {
                DestaqueCard(
                    titulo = "Evento hoje",
                    subtitulo = "",
                    tag = null
                )
            }
            item {
                DestaqueCard(
                    titulo = "Evento hoje",
                    subtitulo = "",
                    tag = null
                )
            }
            item {
                DestaqueCard(
                    titulo = "Evento hoje",
                    subtitulo = "",
                    tag = null
                )
            }
            item {
                DestaqueCard(
                    titulo = "Evento hoje",
                    subtitulo = "",
                    tag = null
                )
            }

        }
    }
}

@Composable
fun DestaqueCard(titulo: String, subtitulo: String, tag: String?) {
    Box(
        modifier = Modifier
            .padding(end = 12.dp)
            .width(200.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Purple80)

    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            tag?.let {
                Text(
                    text = tag,
                    //color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        //.background(Color(0xFFAD8F54), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            if (subtitulo.isNotEmpty()) {
                Text(subtitulo,  fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CategoriasSection() {
    val categorias = listOf(
        "Todos" to Icons.Default.Menu,
       // "Bares" to Icons.Default.LocalBar,
        //"Restaurantes" to Icons.Default.Restaurant,
      //  "Festas" to Icons.Default.Celebration
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        categorias.forEach { (label, icon) ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = label)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    label,

                    fontWeight = if (label == "Todos") FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}
/*
@Composable
fun MapaSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.LightGray) // Placeholder do mapa
    ) {
        // Simulação de marcador no mapa
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(32.dp)
                .background(Color.Red, CircleShape)
        )

        // Card flutuante
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF221B3F))
                .fillMaxWidth(0.9f)
                .height(80.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.DarkGray)
                ) {
                    // Imagem do bar aqui
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Bar do Pedro", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("1,2 km avaiá", color = Color.White, fontSize = 12.sp)
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(1.dp, Color(0xFFF3E7C8)),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("Ver mais", color = Color(0xFFF3E7C8), fontSize = 12.sp)
                }
            }
        }
    }
}
*/

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Purple40)
            .padding(16.dp)
    ) {

        CategoriasSection()
        DestaquesSection()

        MapScreen()

    }
}

