package me.fabiansuarez.compose.lab.netflixbasicnavigation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.screen.HomeScreen
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.screen.LoginScreen
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.screen.RegisterScreen
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.screen.RegisterStep2Screen
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.screen.RegisterStep3Screen

// ─── Rutas ───────────────────────────────────────────────────────────────────
// Cada pantalla tiene un nombre único (su "dirección"). Se definen en un solo
// lugar para no escribir strings sueltos por el código y evitar errores de tipeo.
object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val REGISTER_STEP_2 = "register_step_2"
    const val REGISTER_STEP_3 = "register_step_3"
    const val HOME = "home"
}

// ─── Grafo de navegación ─────────────────────────────────────────────────────
// - NavController: el "control remoto" que ejecuta navigate() / popBackStack().
// - NavHost: el contenedor que muestra la pantalla del destino actual.
// - composable(ruta): asocia una ruta con la pantalla que debe mostrarse.
@Composable
fun NetflixNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,   // la app arranca en el inicio de sesión
        modifier = modifier
    ) {

        // 1) Login → Home  |  Login → Register
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Routes.HOME) {
                        // Se saca el Login de la pila: al pulsar Atrás en Home
                        // se sale de la app en lugar de volver al Login.
                        popUpTo(Routes.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER) {
                        launchSingleTop = true   // evita duplicar la pantalla
                    }
                }
            )
        }

        // 2) Registro paso 1 → paso 2  |  paso 1 → Login
        composable(Routes.REGISTER) {
            RegisterScreen(
                onBackClick = { navController.popBackStack() },
                onNextClick = {
                    navController.navigate(Routes.REGISTER_STEP_2) {
                        launchSingleTop = true
                    }
                },
                // "¿Ya tienes cuenta? Inicia sesión." → vuelve al Login que ya
                // está en la pila (no crea un Login nuevo encima).
                onLoginClick = {
                    navController.popBackStack(Routes.LOGIN, inclusive = false)
                }
            )
        }

        // 3) Registro paso 2 → paso 3  |  paso 2 → paso 1
        composable(Routes.REGISTER_STEP_2) {
            RegisterStep2Screen(
                onBackClick = { navController.popBackStack() },
                onNextClick = {
                    navController.navigate(Routes.REGISTER_STEP_3) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // 4) Registro paso 3 → paso 2  |  paso 3 → Home
        composable(Routes.REGISTER_STEP_3) {
            RegisterStep3Screen(
                onBackClick = { navController.popBackStack() },
                onStartMembershipClick = {
                    navController.navigate(Routes.HOME) {
                        // Se limpia toda la pila (Login + pasos de registro) para
                        // que desde Home no se pueda volver al formulario de pago.
                        popUpTo(Routes.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // 5) Home → Login (cerrar sesión desde el avatar de perfil)
        composable(Routes.HOME) {
            HomeScreen(
                onLogoutClick = {
                    navController.navigate(Routes.LOGIN) {
                        // Se saca Home de la pila: tras cerrar sesión, Atrás no
                        // devuelve al contenido.
                        popUpTo(Routes.HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
