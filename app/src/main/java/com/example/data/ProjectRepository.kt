package com.example.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.BuildConfig
import com.example.model.AppModule
import com.example.model.ConfigHealth
import com.example.model.ConfigItem
import com.example.model.DiagnosticResult
import com.example.model.ModuleCategory
import com.example.model.ModuleStatus
import com.example.model.ProjectMetrics
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProjectRepository(private val context: Context) {

  fun getProjectMetrics(): ProjectMetrics {
    val modules = getImplementedModules()
    val operational = modules.count { it.status == ModuleStatus.OPERATIONAL || it.status == ModuleStatus.ACTIVE }
    val configItems = getConfigurationStatus()
    val passing = configItems.count { it.status == ConfigHealth.OPTIMAL || it.status == ConfigHealth.NORMAL }

    return ProjectMetrics(
      projectName = "Project Overview",
      appId = BuildConfig.APPLICATION_ID,
      versionName = BuildConfig.VERSION_NAME,
      versionCode = BuildConfig.VERSION_CODE,
      targetSdk = 36,
      minSdk = 24,
      compileSdk = "API 36 (VanillaIceCream / Android 15+)",
      modulesCount = modules.size,
      operationalModulesCount = operational,
      passingChecks = passing,
      totalChecks = configItems.size,
      buildType = if (BuildConfig.DEBUG) "Debug Build" else "Release Build",
      kotlinDsl = "Gradle Kotlin DSL (8.12+)",
      composeBom = "2024.09.00+"
    )
  }

  fun getImplementedModules(): List<AppModule> {
    return listOf(
      AppModule(
        id = "module_compose_ui",
        name = "Jetpack Compose UI & Material 3",
        category = ModuleCategory.UI,
        status = ModuleStatus.OPERATIONAL,
        version = "BOM 2024.09.00+",
        description = "Modern declarative UI framework utilizing Material Design 3 tokens, dynamic theming, and edge-to-edge window insets.",
        features = listOf(
          "Full Edge-to-Edge window insets compliance",
          "M3 dynamic light and dark color schemes",
          "Material Symbols & Icons Extended integration",
          "Surface tonal elevation and fluid spring transitions"
        ),
        dependencies = listOf(
          "androidx.compose.material3",
          "androidx.compose.ui",
          "androidx.compose.foundation",
          "androidx.activity.compose"
        ),
        healthStatus = "Active & Rendering",
        leadTech = "Jetpack Compose"
      ),
      AppModule(
        id = "module_room_db",
        name = "Room Database & Local Persistence",
        category = ModuleCategory.DATA,
        status = ModuleStatus.OPERATIONAL,
        version = "2.6.1",
        description = "Offline-first SQLite abstraction layer with KSP compile-time query verification and Kotlin Coroutine Flow support.",
        features = listOf(
          "KSP compile-time schema verification",
          "Async Coroutine DAO flow queries",
          "Type converters and robust schema migrations",
          "Transactional SQLite data consistency"
        ),
        dependencies = listOf(
          "androidx.room.runtime",
          "androidx.room.ktx",
          "androidx.room.compiler (KSP)"
        ),
        healthStatus = "KSP Generated & Ready",
        leadTech = "AndroidX Room"
      ),
      AppModule(
        id = "module_retrofit_network",
        name = "Retrofit HTTP & Moshi JSON",
        category = ModuleCategory.NETWORK,
        status = ModuleStatus.OPERATIONAL,
        version = "2.11.0",
        description = "Type-safe REST client backed by OkHttp3 connection pooling and Moshi reflection/codegen serialization.",
        features = listOf(
          "Type-safe HTTP API declarations",
          "OkHttp3 logging and interceptor chain",
          "Moshi JSON codegen model serialization",
          "Resilient error handling and connection retries"
        ),
        dependencies = listOf(
          "com.squareup.retrofit2:retrofit",
          "com.squareup.retrofit2:converter-moshi",
          "com.squareup.moshi:moshi-kotlin",
          "com.squareup.okhttp3:logging-interceptor"
        ),
        healthStatus = "Engine Configured",
        leadTech = "Retrofit 2"
      ),
      AppModule(
        id = "module_coroutines",
        name = "Kotlin Coroutines & Flow Pipeline",
        category = ModuleCategory.CORE,
        status = ModuleStatus.OPERATIONAL,
        version = "1.8.1+",
        description = "Asynchronous structured concurrency providing reactive state management via StateFlow and SharedFlow.",
        features = listOf(
          "Structured concurrency lifecycle scopes",
          "Dispatchers.IO / Main / Default management",
          "MutableStateFlow for unidirectional state updates",
          "Exception handling supervision hierarchies"
        ),
        dependencies = listOf(
          "kotlinx.coroutines.core",
          "kotlinx.coroutines.android",
          "androidx.lifecycle.runtime.ktx",
          "androidx.lifecycle.viewmodel.compose"
        ),
        healthStatus = "Threads Healthy",
        leadTech = "Kotlin Coroutines"
      ),
      AppModule(
        id = "module_firebase_services",
        name = "Firebase Services & Cloud Engine",
        category = ModuleCategory.CLOUD,
        status = ModuleStatus.CONFIGURED,
        version = "BOM 33.3.0+",
        description = "Cloud integration framework with Google Services, App Check reCAPTCHA verification, and AI capabilities.",
        features = listOf(
          "Firebase BOM centralized dependency management",
          "App Check reCAPTCHA & debug providers",
          "Google Services plugin integration",
          "Graceful missing services fallback handling"
        ),
        dependencies = listOf(
          "com.google.firebase:firebase-bom",
          "com.google.firebase:firebase-ai",
          "com.google.firebase:firebase-appcheck-recaptcha",
          "com.google.firebase:firebase-appcheck-debug"
        ),
        healthStatus = "Configured (Safe Mode)",
        leadTech = "Firebase"
      ),
      AppModule(
        id = "module_qa_testing",
        name = "Robolectric & Roborazzi Testing",
        category = ModuleCategory.QA,
        status = ModuleStatus.OPERATIONAL,
        version = "4.12+ / 1.34+",
        description = "Local JVM unit testing suite paired with visual screenshot regression verification tools.",
        features = listOf(
          "Local JVM Android runtime simulation",
          "Roborazzi pixel-level visual regression testing",
          "JUnit 4 Compose UI assertion harness",
          "Coroutines test dispatcher virtual time"
        ),
        dependencies = listOf(
          "org.robolectric:robolectric",
          "io.github.takahirom.roborazzi",
          "androidx.compose.ui:ui-test-junit4",
          "kotlinx.coroutines.test"
        ),
        healthStatus = "Test Suite Ready",
        leadTech = "Robolectric"
      ),
      AppModule(
        id = "module_secrets_plugin",
        name = "Secrets Gradle Plugin & Security",
        category = ModuleCategory.CORE,
        status = ModuleStatus.OPERATIONAL,
        version = "2.0.1",
        description = "Automated environment variable injector shielding API credentials from source code repositories.",
        features = listOf(
          "Separation of secrets from Git source",
          "Safe injection through generated BuildConfig",
          "Fallback to .env.example defaults",
          "Ignore list exclusion for debug tokens"
        ),
        dependencies = listOf(
          "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
        ),
        healthStatus = "Secure & Shielded",
        leadTech = "Secrets Plugin"
      )
    )
  }

  fun getConfigurationStatus(): List<ConfigItem> {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    val activeNetwork = connectivityManager?.activeNetwork
    val networkCaps = connectivityManager?.getNetworkCapabilities(activeNetwork)

    val networkStatus = when {
      networkCaps == null -> "Offline (No active interface)"
      networkCaps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Online (Wi-Fi High-Speed)"
      networkCaps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Online (Cellular Data)"
      networkCaps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Online (Ethernet Cable)"
      else -> "Online (Connected)"
    }

    val runtime = Runtime.getRuntime()
    val totalMemMb = runtime.totalMemory() / (1024 * 1024)
    val freeMemMb = runtime.freeMemory() / (1024 * 1024)
    val usedMemMb = totalMemMb - freeMemMb
    val maxMemMb = runtime.maxMemory() / (1024 * 1024)

    return listOf(
      ConfigItem(
        id = "cfg_app_id",
        category = "Application Identity",
        label = "Application ID",
        value = BuildConfig.APPLICATION_ID,
        status = ConfigHealth.OPTIMAL,
        detail = "Unique package identifier configured in app/build.gradle.kts"
      ),
      ConfigItem(
        id = "cfg_version",
        category = "Application Identity",
        label = "Version Name & Code",
        value = "v${BuildConfig.VERSION_NAME} (Build ${BuildConfig.VERSION_CODE})",
        status = ConfigHealth.OPTIMAL,
        detail = "Release versioning aligned with Semantic Versioning 2.0"
      ),
      ConfigItem(
        id = "cfg_build_mode",
        category = "Build & Runtime",
        label = "Build Variant",
        value = if (BuildConfig.DEBUG) "Debug (Logging Enabled)" else "Release (Optimized)",
        status = ConfigHealth.NORMAL,
        detail = "Debuggable flags, ProGuard obfuscation rules, and signing configurations"
      ),
      ConfigItem(
        id = "cfg_android_sdk",
        category = "Android SDK & Platform",
        label = "Target & Compile SDK",
        value = "Target SDK 36 | Min SDK 24",
        status = ConfigHealth.OPTIMAL,
        detail = "Full backward compatibility from Android 7.0 (Nougat) up to Android 15/16 preview"
      ),
      ConfigItem(
        id = "cfg_device_os",
        category = "Android SDK & Platform",
        label = "Host Device OS",
        value = "Android ${Build.VERSION.RELEASE} (API Level ${Build.VERSION.SDK_INT})",
        status = ConfigHealth.OPTIMAL,
        detail = "Hardware: ${Build.MANUFACTURER} ${Build.MODEL} [${Build.SUPPORTED_ABIS.firstOrNull() ?: "Unknown"}]"
      ),
      ConfigItem(
        id = "cfg_memory",
        category = "System Resources",
        label = "JVM Heap Allocation",
        value = "${usedMemMb} MB Used / ${maxMemMb} MB Max",
        status = if (usedMemMb < maxMemMb * 0.8) ConfigHealth.OPTIMAL else ConfigHealth.ATTENTION,
        detail = "Active heap utilization currently at ${(usedMemMb * 100 / maxMemMb.coerceAtLeast(1))}% capacity"
      ),
      ConfigItem(
        id = "cfg_network",
        category = "System Resources",
        label = "Network Connectivity",
        value = networkStatus,
        status = if (networkCaps != null) ConfigHealth.OPTIMAL else ConfigHealth.NORMAL,
        detail = "Active network transport detected via Android ConnectivityManager"
      ),
      ConfigItem(
        id = "cfg_theme_engine",
        category = "UI & Rendering",
        label = "Material 3 Dynamic Theming",
        value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) "Dynamic Colors Supported" else "Custom High-Contrast Palette",
        status = ConfigHealth.OPTIMAL,
        detail = "Android 12+ wallpaper dynamic color extraction with fallback to modern custom palette"
      ),
      ConfigItem(
        id = "cfg_edge_to_edge",
        category = "UI & Rendering",
        label = "Edge-to-Edge Insets",
        value = "Enabled (enableEdgeToEdge)",
        status = ConfigHealth.OPTIMAL,
        detail = "Full-bleed window drawing respecting status and navigation bar insets"
      )
    )
  }

  fun runDiagnostics(): DiagnosticResult {
    val runtime = Runtime.getRuntime()
    val totalMem = runtime.totalMemory()
    val freeMem = runtime.freeMemory()
    val maxMem = runtime.maxMemory()
    val usedMb = (totalMem - freeMem) / (1024 * 1024)
    val maxMb = maxMem / (1024 * 1024)
    val percent = ((usedMb.toDouble() / maxMb.coerceAtLeast(1).toDouble()) * 100).toInt()

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    val activeNet = connectivityManager?.activeNetwork
    val caps = connectivityManager?.getNetworkCapabilities(activeNet)
    val isConnected = caps != null
    val netType = when {
      caps == null -> "Disconnected"
      caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi"
      caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
      caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
      else -> "Active Connection"
    }

    val metrics = context.resources.displayMetrics
    val displayStr = "${metrics.widthPixels}x${metrics.heightPixels}px (${metrics.densityDpi} dpi, density ${metrics.density})"

    val timeStr = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

    return DiagnosticResult(
      timestamp = timeStr,
      memoryAllocatedMb = usedMb,
      memoryMaxMb = maxMb,
      memoryPercent = percent,
      networkType = netType,
      isNetworkConnected = isConnected,
      osVersion = "Android ${Build.VERSION.RELEASE}",
      apiLevel = Build.VERSION.SDK_INT,
      deviceModel = "${Build.MANUFACTURER.replaceFirstChar { it.uppercase() }} ${Build.MODEL}",
      supportedAbis = Build.SUPPORTED_ABIS.joinToString(", "),
      displayMetrics = displayStr,
      allChecksPassed = true
    )
  }
}
