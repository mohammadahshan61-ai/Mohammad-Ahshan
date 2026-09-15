package com.example.model

enum class ModuleCategory(val displayName: String) {
  ALL("All Modules"),
  CORE("Core Architecture"),
  UI("UI & Design System"),
  DATA("Data & Persistence"),
  NETWORK("Network & API"),
  CLOUD("Cloud & Services"),
  QA("Testing & QA")
}

enum class ModuleStatus(val label: String) {
  OPERATIONAL("Operational"),
  ACTIVE("Active"),
  CONFIGURED("Configured"),
  READY("Production Ready")
}

enum class ConfigHealth {
  OPTIMAL,
  NORMAL,
  ATTENTION,
  INFO
}

data class AppModule(
  val id: String,
  val name: String,
  val category: ModuleCategory,
  val status: ModuleStatus,
  val version: String,
  val description: String,
  val features: List<String>,
  val dependencies: List<String>,
  val healthStatus: String,
  val leadTech: String
)

data class ConfigItem(
  val id: String,
  val category: String,
  val label: String,
  val value: String,
  val status: ConfigHealth,
  val detail: String
)

data class ProjectMetrics(
  val projectName: String,
  val appId: String,
  val versionName: String,
  val versionCode: Int,
  val targetSdk: Int,
  val minSdk: Int,
  val compileSdk: String,
  val modulesCount: Int,
  val operationalModulesCount: Int,
  val passingChecks: Int,
  val totalChecks: Int,
  val buildType: String,
  val kotlinDsl: String,
  val composeBom: String
)

data class DiagnosticResult(
  val timestamp: String,
  val memoryAllocatedMb: Long,
  val memoryMaxMb: Long,
  val memoryPercent: Int,
  val networkType: String,
  val isNetworkConnected: Boolean,
  val osVersion: String,
  val apiLevel: Int,
  val deviceModel: String,
  val supportedAbis: String,
  val displayMetrics: String,
  val allChecksPassed: Boolean
)
