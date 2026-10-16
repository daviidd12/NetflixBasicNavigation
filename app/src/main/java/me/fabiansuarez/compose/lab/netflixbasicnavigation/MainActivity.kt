package me.fabiansuarez.compose.lab.netflixbasicnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import me.fabiansuarez.compose.lab.netflixbasicnavigation.navigation.NetflixNavHost
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.theme.NetflixBasicNavigationTheme
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.theme.NetflixBlack

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NetflixBasicNavigationTheme {
                NetflixNavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(NetflixBlack)
                )
            }
        }
    }
}
