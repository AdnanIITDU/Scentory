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
    primary = MintGreen,
    secondary = SoftJade,
    tertiary = AmberAccent,
    background = DarkSlate,
    surface = MediumSlate,
    onPrimary = DarkSlate,
    onSecondary = DarkSlate,
    onTertiary = DarkSlate,
    onBackground = TextLight,
    onSurface = TextLight,
    error = WarningRed
  )

private val LightColorScheme =
  lightColorScheme(
    primary = DeepTeal,
    secondary = SoftJade,
    tertiary = MintGreen,
    background = LightBg,
    surface = CardLight,
    onPrimary = TextLight,
    onSecondary = TextLight,
    onTertiary = TextDark,
    onBackground = TextDark,
    onSurface = TextDark,
    error = WarningRed
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Set false to preserve our premium custom theme!
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
