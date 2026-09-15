package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = TechPrimaryDark,
    onPrimary = TechOnPrimaryDark,
    primaryContainer = TechPrimaryContainerDark,
    onPrimaryContainer = TechOnPrimaryContainerDark,
    secondary = TechSecondaryDark,
    onSecondary = TechOnSecondaryDark,
    secondaryContainer = TechSecondaryContainerDark,
    onSecondaryContainer = TechOnSecondaryContainerDark,
    tertiary = TechTertiaryDark,
    onTertiary = TechOnTertiaryDark,
    tertiaryContainer = TechTertiaryContainerDark,
    onTertiaryContainer = TechOnTertiaryContainerDark,
    background = TechBackgroundDark,
    onBackground = TechOnBackgroundDark,
    surface = TechSurfaceDark,
    onSurface = TechOnSurfaceDark,
    surfaceVariant = TechSurfaceVariantDark,
    onSurfaceVariant = TechOnSurfaceVariantDark,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = TechPrimary,
    onPrimary = TechOnPrimary,
    primaryContainer = TechPrimaryContainer,
    onPrimaryContainer = TechOnPrimaryContainer,
    secondary = TechSecondary,
    onSecondary = TechOnSecondary,
    secondaryContainer = TechSecondaryContainer,
    onSecondaryContainer = TechOnSecondaryContainer,
    tertiary = TechTertiary,
    onTertiary = TechOnTertiary,
    tertiaryContainer = TechTertiaryContainer,
    onTertiaryContainer = TechOnTertiaryContainer,
    background = TechBackground,
    onBackground = TechOnBackground,
    surface = TechSurface,
    onSurface = TechOnSurface,
    surfaceVariant = TechSurfaceVariant,
    onSurfaceVariant = TechOnSurfaceVariant,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

