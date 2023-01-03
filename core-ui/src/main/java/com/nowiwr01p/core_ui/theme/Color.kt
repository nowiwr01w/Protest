package com.nowiwr01p.core_ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@get:Composable
val Colors.mainBackgroundColor: Color
    get() = Color(0xFF3f4257)

/**
 * Text
 */
val lightTextPrimary = Color(0xFF191A1C)
val darkTextPrimary = Color.White

@get:Composable
val Colors.textPrimary: Color
    get() = if (isLight) lightTextPrimary else darkTextPrimary

@get:Composable
val Colors.textPrimaryInverted: Color
    get() = if (isLight) darkTextPrimary else lightTextPrimary

val lightTextSecondary = Color(0xFF9194A6)
val darkTextSecondary = Color(0xFF9194A6)

@get:Composable
val Colors.textColorSecondary: Color
    get() = if (isLight) lightTextSecondary else darkTextSecondary

@get:Composable
val Colors.textColorSecondaryInverse: Color
    get() = if (isLight) darkTextSecondary else lightTextSecondary

val lightTextLink = Color(0xFF007AFF)
val darkTextLink = Color(0xFF0A84FF)

@get:Composable
val Colors.link: Color
    get() = if (isLight) lightTextLink else darkTextLink

val lightTextPositive = Color(0xFF32B253)
val darkTextPositive = Color(0xFF31BD55)

@get:Composable
val Colors.textPositive: Color
    get() = if (isLight) lightTextPositive else darkTextPositive

val lightTextNegative = Color(0xFFE34446)
val darkTextNegative = Color(0xFFF23D3F)

@get:Composable
val Colors.textNegative: Color
    get() = if (isLight) lightTextNegative else darkTextNegative

/**
 * Graphics
 */
val lightGraphicsPrimary = Color(0xFF191A1C)
val darkGraphicsPrimary = Color.White

@get:Composable
val Colors.graphicsPrimary: Color
    get() = if (isLight) lightGraphicsPrimary else darkGraphicsPrimary

@get:Composable
val Colors.graphicPrimaryInverted: Color
    get() = if (isLight) darkGraphicsPrimary else lightGraphicsPrimary

val lightGraphicsSecondary = Color(0xFF9194A6)
val darkGraphicsSecondary = Color(0xFF777880)

@get:Composable
val Colors.graphicsSecondary: Color
    get() = if (isLight) lightGraphicsSecondary else darkGraphicsSecondary

val lightGraphicTertiary = Color(0xFFCFD1DB)
val darkGraphicTertiary = Color(0xFF3D3D41)
@get:Composable
val Colors.graphicsTertiary: Color
    get() = if (isLight) lightGraphicTertiary else darkGraphicTertiary

val lightGraphicsNeutral = Color(0xFFE8EAF0)
val darkGraphicsNeutral = Color(0xFF2A2B2E)

@get:Composable
val Colors.graphicsNeutral: Color
    get() = if (isLight) lightGraphicsNeutral else darkGraphicsNeutral

val lightGraphicsBlue = Color(0xFF007AFF)
val darkGraphicsBlue = Color(0xFF0A84FF)

@get:Composable
val Colors.graphicsBlue: Color
    get() = if (isLight) lightGraphicsBlue else darkGraphicsBlue

val lightGraphicsRed = Color(0xFFE34446)
val darkGraphicsRed = Color(0xFFF23D3F)

@get:Composable
val Colors.graphicsRed: Color
    get() = if (isLight) lightGraphicsRed else darkGraphicsRed

val lightGraphicsIndigo = Color(0xFF5759CF)
val darkGraphicsIndigo = Color(0xFF5759CF)

@get:Composable
val Colors.graphicsIndigo: Color
    get() = if (isLight) lightGraphicsIndigo else darkGraphicsIndigo


val lightGraphicsOrange = Color(0xFFF19938)
val darkGraphicsOrange = Color(0xFFFF9F0A)

@get:Composable
val Colors.graphicsOrange: Color
    get() = if (isLight) lightGraphicsOrange else darkGraphicsOrange

val lightGraphicsGreen = Color(0xFF32B153)
val darkGraphicsGreen = Color(0xFF31BD55)

@get:Composable
val Colors.graphicsGreen: Color
    get() = if (isLight) lightGraphicsGreen else darkGraphicsGreen

val lightGraphicsYellow = Color(0xFFF7CD46)
val darkGraphicsYellow = Color(0xFFFFD60A)

@get:Composable
val Colors.graphicsYellow: Color
    get() = if (isLight) lightGraphicsYellow else darkGraphicsYellow

val lightGraphicsPurple = Color(0xFFA35AD7)
val darkGraphicsPurple = Color(0xFFBF5AF2)

@get:Composable
val Colors.graphicsPurple: Color
    get() = if (isLight) lightGraphicsPurple else darkGraphicsPurple

/**
 *  Graphics Transparent
 */

val lightGraphicsGreenTransparent = Color(0x1A32B153)

val darkGraphicsGreenTransparent = Color(0x1A31BD55)

@get:Composable
val Colors.graphicsGreenTransparent: Color
    get() = if (isLight) lightGraphicsGreenTransparent else darkGraphicsGreenTransparent

val lightGraphicsRedTransparent = Color(0x1AE34446)

val darkGraphicsRedTransparent = Color(0x1AF23D3F)

@get:Composable
val Colors.graphicsRedTransparent: Color
    get() = if (isLight) lightGraphicsRedTransparent else darkGraphicsRedTransparent


/**
 * Background
 */

val lightBackgroundPrimary = Color.White
val darkBackgroundPrimary = Color(0xFF111112)

@get:Composable
val Colors.backgroundPrimary: Color
    get() = if (isLight) lightBackgroundPrimary else darkBackgroundPrimary

val lightBackgroundSecondary = Color(0xFFF4F5F8)
val darkBackgroundSecondary = Color(0xFF26262A)

@get:Composable
val Colors.backgroundSecondary: Color
    get() = if (isLight) lightBackgroundSecondary else darkBackgroundSecondary

val lightBackgroundBlue = Color(0xFFE6F2FF)
val darkBackgroundBlue = Color(0xFF102031)

@get:Composable
val Colors.backgroundBlue: Color
    get() = if (isLight) lightBackgroundBlue else darkBackgroundBlue

val lightBackgroundRed = Color(0xFFFCECED)
val darkBackgroundRed = Color(0xFF2E1718)

@get:Composable
val Colors.backgroundRed: Color
    get() = if (isLight) lightBackgroundRed else darkBackgroundRed

val lightBackgroundIndigo = Color(0xFFEEEEFA)
val darkBackgroundIndigo = Color(0xFF1B1B2E)

@get:Composable
val Colors.backgroundIndigo: Color
    get() = if (isLight) lightBackgroundIndigo else darkBackgroundIndigo

val lightBackgroundOrange = Color(0xFFFEF5EB)
val darkBackgroundOrange = Color(0xFF302311)

@get:Composable
val Colors.backgroundOrange: Color
    get() = if (isLight) lightBackgroundOrange else darkBackgroundOrange

val lightBackgroundGreen = Color(0xFFEBF7EE)
val darkBackgroundGreen = Color(0xFF15271B)

@get:Composable
val Colors.backgroundGreen: Color
    get() = if (isLight) lightBackgroundGreen else darkBackgroundGreen

val lightBackgroundYellow = Color(0xFFFEFAED)
val darkBackgroundYellow = Color(0xFF302B11)

@get:Composable
val Colors.backgroundYellow: Color
    get() = if (isLight) lightBackgroundYellow else darkBackgroundYellow

val lightBackgroundPurple = Color(0xFFF6EFFB)
val darkBackgroundPurple = Color(0xFF281A2F)

@get:Composable
val Colors.backgroundPurple: Color
    get() = if (isLight) lightBackgroundPurple else darkBackgroundPurple

val lightBackgroundSpecial = Color.White
val darkBackgroundSpecial = Color(0xFF1C1C1E)

@get:Composable
val Colors.backgroundSpecial: Color
    get() = if (isLight) lightBackgroundSpecial else darkBackgroundSpecial

@get:Composable
val Colors.backgroundSpecialInverse: Color
    get() = if (isLight) darkBackgroundSpecial else lightBackgroundSpecial

val lightBackgroundBrand = Color(0xFF007AFF)
val darkBackgroundBrand = Color(0xFF0A84FF)

@get:Composable
val Colors.backgroundBrand: Color
    get() = if (isLight) lightBackgroundBrand else darkBackgroundBrand

/**
 * Background Transparent
 */

val lightBackgroundBrandTransparent = Color(0x1A007AFF)
val darkBackgroundBrandTransparent = Color(0x1A0A84FF)

@get:Composable
val Colors.backgroundBrandTransparent: Color
    get() = if (isLight) lightBackgroundBrandTransparent else darkBackgroundBrandTransparent

val lightBackgroundSecondaryTransparent = Color(0x1AF4F5F8)
val darkBackgroundSecondaryTransparent = Color(0x1A26262A)

@get:Composable
val Colors.backgroundSecondaryTransparent: Color
    get() = if (isLight) lightBackgroundSecondaryTransparent else darkBackgroundSecondaryTransparent

val lightBackgroundTransparentRed = Color(0x16E34446)
val darkBackgroundTransparentRed = Color(0x20F23D3F)

@get:Composable
val Colors.backgroundTransparentRed: Color
    get() = if (isLight) lightBackgroundTransparentRed else darkBackgroundTransparentRed

/**
 * Other
 */

val bottomMenuBackgroundLight = Color(0xFFFFFFFF)
val bottomMenuBackgroundDark = Color(0xFF111112)

val shimmerNight = Color(0xFF36363B)

val lightOrangeTransparent = Color(0x1AF19938)
val darkOrangeTransparent = Color(0x1AFF9F0A)
val lightPattern = Color(0xFFDCDEE6)
val darkPattern = Color(0xFF353538)

val lightShadowColor = Color(0xFF191A1C)
val darkShadowColor = Color(0xFF000000)
val lightStoryGroupTextColor = Color(0xFF000000)
val darkStoryGroupTextColor = Color(0xFFFFFFFF)

@get:Composable
val Colors.shimmer: Color
    get() = if (isLight) Color.White else shimmerNight

@get:Composable
val Colors.bottomMenuBackground: Color
    get() = if (isLight) bottomMenuBackgroundLight else bottomMenuBackgroundDark

@get:Composable
val Colors.orangeTransparent: Color
    get() = if (isLight) lightOrangeTransparent else darkOrangeTransparent

@get:Composable
val Colors.patternColor: Color
    get() = if (isLight) lightPattern else darkPattern

@get:Composable
val Colors.shadowColor: Color
    get() = if (isLight) lightShadowColor else darkShadowColor

@get:Composable
val Colors.storyGroupTextColor: Color
    get() = if (isLight) lightStoryGroupTextColor else darkStoryGroupTextColor


