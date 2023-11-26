package com.example.weather_app.fonts

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import com.example.weather_app.R

object Fonts {
    @OptIn(ExperimentalTextApi::class)
    val boldFontFamily by lazy {
        FontFamily(
            Font(
                R.font.montserrat_variable, variationSettings = FontVariation.Settings(
                    FontVariation.weight(600),
                    FontVariation.width(30f),
                    FontVariation.slant(-6f)
                )
            )
        )
    }
    @OptIn(ExperimentalTextApi::class)
    val normalFontFamily by lazy {
        FontFamily(
            Font(
                R.font.montserrat_variable, variationSettings = FontVariation.Settings(
                    FontVariation.weight(400),
                    FontVariation.width(30f),
                    FontVariation.slant(-6f)
                )
            )
        )
    }


    // You can add more fonts or variations here if needed
}
