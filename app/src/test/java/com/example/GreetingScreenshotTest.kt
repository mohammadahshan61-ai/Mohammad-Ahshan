package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.example.model.ProjectMetrics
import com.example.ui.components.ProjectHeroCard
import com.example.ui.theme.MyApplicationTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun greeting_screenshot() {
    val sampleMetrics = ProjectMetrics(
      projectName = "Project Overview",
      appId = "com.aistudio.projectoverview.krvxqn",
      versionName = "1.0",
      versionCode = 1,
      targetSdk = 36,
      minSdk = 24,
      compileSdk = "API 36",
      modulesCount = 7,
      operationalModulesCount = 7,
      passingChecks = 9,
      totalChecks = 9,
      buildType = "Debug Build",
      kotlinDsl = "Gradle Kotlin DSL",
      composeBom = "2024.09.00+"
    )

    composeTestRule.setContent {
      MyApplicationTheme {
        ProjectHeroCard(metrics = sampleMetrics)
      }
    }

    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/greeting.png")
  }
}

