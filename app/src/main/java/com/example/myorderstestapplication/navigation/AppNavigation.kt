package com.example.myorderstestapplication.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myorderstestapplication.ui.screens.AccountScreen
import com.example.myorderstestapplication.ui.screens.HomeScreen
import com.example.myorderstestapplication.ui.screens.MyOrdersScreen
import com.example.myorderstestapplication.ui.screens.PaymentsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

sealed class Screen(val route: String) {
    object Home     : Screen("home")
    object Orders   : Screen("orders")
    object Payments : Screen("payments")
    object Account  : Screen("account")
}

// Tab order used to decide slide direction
private val tabOrder = listOf(
    Screen.Home.route,
    Screen.Orders.route,
    Screen.Payments.route,
    Screen.Account.route
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {

    AnimatedNavHost(
        navController    = navController,
        startDestination = Screen.Orders.route
    ) {

        composable(
            route = Screen.Home.route,
            enterTransition  = { slideEnter(initialState, targetState) },
            exitTransition   = { slideExit(initialState, targetState) },
            popEnterTransition  = { slideEnter(initialState, targetState) },
            popExitTransition   = { slideExit(initialState, targetState) }
        ) { HomeScreen() }

        composable(
            route = Screen.Orders.route,
            enterTransition  = { slideEnter(initialState, targetState) },
            exitTransition   = { slideExit(initialState, targetState) },
            popEnterTransition  = { slideEnter(initialState, targetState) },
            popExitTransition   = { slideExit(initialState, targetState) }
        ) { MyOrdersScreen() }

        composable(
            route = Screen.Payments.route,
            enterTransition  = { slideEnter(initialState, targetState) },
            exitTransition   = { slideExit(initialState, targetState) },
            popEnterTransition  = { slideEnter(initialState, targetState) },
            popExitTransition   = { slideExit(initialState, targetState) }
        ) { PaymentsScreen() }

        composable(
            route = Screen.Account.route,
            enterTransition  = { slideEnter(initialState, targetState) },
            exitTransition   = { slideExit(initialState, targetState) },
            popEnterTransition  = { slideEnter(initialState, targetState) },
            popExitTransition   = { slideExit(initialState, targetState) }
        ) { AccountScreen() }
    }
}

// ─── Helpers ──────────────────────────────────────────────────────────────────

private const val ANIM_DURATION = 300

/**
 * Returns +1 if navigating right (higher tab index), -1 if navigating left.
 */
@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedContentTransitionScope<*>.tabDirection(
    from: androidx.navigation.NavBackStackEntry,
    to:   androidx.navigation.NavBackStackEntry
): Int {
    val fromIdx = tabOrder.indexOf(from.destination.route)
    val toIdx   = tabOrder.indexOf(to.destination.route)
    return if (toIdx > fromIdx) 1 else -1
}

@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedContentTransitionScope<androidx.navigation.NavBackStackEntry>.slideEnter(
    from: androidx.navigation.NavBackStackEntry,
    to:   androidx.navigation.NavBackStackEntry
): EnterTransition {
    val dir = tabDirection(from, to)
    return slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth * dir },
        animationSpec  = tween(ANIM_DURATION)
    ) + fadeIn(animationSpec = tween(ANIM_DURATION))
}

@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedContentTransitionScope<androidx.navigation.NavBackStackEntry>.slideExit(
    from: androidx.navigation.NavBackStackEntry,
    to:   androidx.navigation.NavBackStackEntry
): ExitTransition {
    val dir = tabDirection(from, to)
    return slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth * dir },
        animationSpec = tween(ANIM_DURATION)
    ) + fadeOut(animationSpec = tween(ANIM_DURATION))
}