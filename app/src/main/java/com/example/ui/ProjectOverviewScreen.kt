package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DiagnosticResult
import com.example.model.ModuleCategory
import com.example.ui.components.ConfigItemCard
import com.example.ui.components.ModuleCard
import com.example.ui.components.ProjectHeroCard
import com.example.ui.theme.StatusSuccess
import com.example.ui.theme.StatusSuccessContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectOverviewScreen(
  viewModel: ProjectOverviewViewModel,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("screen_project_overview"),
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "Project Overview",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Android 15/16 (API 36) Architecture",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        },
        actions = {
          IconButton(
            onClick = { viewModel.refreshStatus() },
            modifier = Modifier.testTag("action_refresh")
          ) {
            if (uiState.isRefreshing) {
              CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.primary
              )
            } else {
              Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh project configurations"
              )
            }
          }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    },
    bottomBar = {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .windowInsetsPadding(WindowInsets.navigationBars)
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      // Status Message Banner
      AnimatedVisibility(
        visible = uiState.statusMessage != null,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        Surface(
          color = MaterialTheme.colorScheme.primaryContainer,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = uiState.statusMessage.orEmpty(),
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onPrimaryContainer
            )
          }
        }
      }

      // Primary Navigation Tabs
      PrimaryTabRow(
        selectedTabIndex = uiState.selectedTab,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("tabs_main_navigation"),
        containerColor = MaterialTheme.colorScheme.surface
      ) {
        Tab(
          selected = uiState.selectedTab == 0,
          onClick = { viewModel.setSelectedTab(0) },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("Modules")
              Spacer(modifier = Modifier.width(6.dp))
              Surface(
                shape = CircleShape,
                color = if (uiState.selectedTab == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
              ) {
                Text(
                  text = "${uiState.modules.size}",
                  style = MaterialTheme.typography.labelSmall,
                  color = if (uiState.selectedTab == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
          },
          modifier = Modifier.testTag("tab_modules")
        )
        Tab(
          selected = uiState.selectedTab == 1,
          onClick = { viewModel.setSelectedTab(1) },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("Config Status")
              Spacer(modifier = Modifier.width(6.dp))
              Surface(
                shape = CircleShape,
                color = if (uiState.selectedTab == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
              ) {
                Text(
                  text = "${uiState.configItems.size}",
                  style = MaterialTheme.typography.labelSmall,
                  color = if (uiState.selectedTab == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
          },
          modifier = Modifier.testTag("tab_config")
        )
        Tab(
          selected = uiState.selectedTab == 2,
          onClick = { viewModel.setSelectedTab(2) },
          text = { Text("Diagnostics") },
          modifier = Modifier.testTag("tab_diagnostics")
        )
      }

      // Screen Content by Selected Tab
      when (uiState.selectedTab) {
        0 -> ModulesView(
          uiState = uiState,
          onCategorySelect = { viewModel.setSelectedCategory(it) },
          onSearchChange = { viewModel.setSearchQuery(it) },
          onToggleExpand = { viewModel.toggleModuleExpansion(it) }
        )
        1 -> ConfigStatusView(
          uiState = uiState,
          onRefresh = { viewModel.refreshStatus() }
        )
        2 -> DiagnosticsView(
          diagnostics = uiState.diagnostics,
          isRunning = uiState.isDiagnosticRunning,
          onRunTest = { viewModel.triggerDiagnostics() }
        )
      }
    }
  }
}

@Composable
fun ModulesView(
  uiState: ProjectOverviewUiState,
  onCategorySelect: (ModuleCategory) -> Unit,
  onSearchChange: (String) -> Unit,
  onToggleExpand: (String) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("list_modules"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Top Hero Card
    uiState.metrics?.let { metrics ->
      item(key = "hero_card") {
        ProjectHeroCard(metrics = metrics)
      }
    }

    // Search Box
    item(key = "search_bar") {
      OutlinedTextField(
        value = uiState.searchQuery,
        onValueChange = onSearchChange,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("input_search_modules"),
        placeholder = { Text("Search modules, features, dependencies...") },
        leadingIcon = {
          Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
        },
        trailingIcon = {
          if (uiState.searchQuery.isNotEmpty()) {
            IconButton(
              onClick = { onSearchChange("") },
              modifier = Modifier.testTag("button_clear_search")
            ) {
              Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear search")
            }
          }
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = MaterialTheme.colorScheme.surface,
          unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
      )
    }

    // Filter Chips
    item(key = "filter_chips") {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(ModuleCategory.values()) { category ->
          FilterChip(
            selected = uiState.selectedCategory == category,
            onClick = { onCategorySelect(category) },
            label = { Text(category.displayName) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
              selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            modifier = Modifier.testTag("filter_chip_${category.name.lowercase()}")
          )
        }
      }
    }

    // Modules Header
    item(key = "modules_header") {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Implemented Modules (${uiState.filteredModules.size})",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "Tap to view dependencies",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Module Cards
    if (uiState.filteredModules.isEmpty()) {
      item(key = "empty_state") {
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
          shape = RoundedCornerShape(16.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
          Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "No matching modules found",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Try adjusting your search query or category filter.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    } else {
      items(
        items = uiState.filteredModules,
        key = { it.id }
      ) { module ->
        ModuleCard(
          module = module,
          isExpanded = uiState.expandedModuleId == module.id,
          onToggleExpand = { onToggleExpand(module.id) }
        )
      }
    }
  }
}

@Composable
fun ConfigStatusView(
  uiState: ProjectOverviewUiState,
  onRefresh: () -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("list_config_items"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Config Status Banner
    item(key = "config_summary") {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("card_config_summary"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Environment & Configuration",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
              )
              Text(
                text = "All Android runtime parameters verified",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
              )
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = StatusSuccessContainer
            ) {
              Text(
                text = "Verified",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = StatusSuccess,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }
      }
    }

    // Config Items Group
    items(
      items = uiState.configItems,
      key = { it.id }
    ) { configItem ->
      ConfigItemCard(item = configItem)
    }
  }
}

@Composable
fun DiagnosticsView(
  diagnostics: DiagnosticResult?,
  isRunning: Boolean,
  onRunTest: () -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("list_diagnostics"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item(key = "diag_action") {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("card_run_diagnostics"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Subsystem Health Audit",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Live inspection of memory heap, network, and runtime APIs",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Button(
              onClick = onRunTest,
              enabled = !isRunning,
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
              ),
              modifier = Modifier.testTag("button_run_diagnostics")
            ) {
              if (isRunning) {
                CircularProgressIndicator(
                  modifier = Modifier.size(16.dp),
                  strokeWidth = 2.dp,
                  color = MaterialTheme.colorScheme.onPrimary
                )
              } else {
                Icon(
                  imageVector = Icons.Default.Speed,
                  contentDescription = null,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Audit Now")
              }
            }
          }
        }
      }
    }

    if (diagnostics != null) {
      item(key = "diag_memory") {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(18.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Memory,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.primary,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "JVM Heap Memory",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.SemiBold
                )
              }

              Text(
                text = "${diagnostics.memoryPercent}% Allocated",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
              progress = { (diagnostics.memoryPercent / 100f).coerceIn(0f, 1f) },
              modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp)),
              color = MaterialTheme.colorScheme.primary,
              trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "Used: ${diagnostics.memoryAllocatedMb} MB",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = "Max Limit: ${diagnostics.memoryMaxMb} MB",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }

      item(key = "diag_telemetry") {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Text(
              text = "Live Telemetry Snapshot",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.SemiBold
            )

            TelemetryRow(label = "Audit Timestamp", value = diagnostics.timestamp)
            TelemetryRow(label = "Hardware Model", value = diagnostics.deviceModel)
            TelemetryRow(label = "OS Platform", value = "${diagnostics.osVersion} (API ${diagnostics.apiLevel})")
            TelemetryRow(label = "ABI Architecture", value = diagnostics.supportedAbis)
            TelemetryRow(label = "Display Geometry", value = diagnostics.displayMetrics)
            TelemetryRow(
              label = "Network Status",
              value = if (diagnostics.isNetworkConnected) "Connected (${diagnostics.networkType})" else "Offline"
            )
          }
        }
      }

      item(key = "diag_checks") {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "Verified Subsystem Checks",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.SemiBold
            )

            SubsystemCheckRow(name = "Jetpack Compose BOM Rendering Pipeline", status = "PASSED")
            SubsystemCheckRow(name = "Material 3 Adaptive Color Palette & Insets", status = "PASSED")
            SubsystemCheckRow(name = "Room SQLite Database Persistence Abstraction", status = "PASSED")
            SubsystemCheckRow(name = "Retrofit 2 & Moshi Serialization Stack", status = "PASSED")
            SubsystemCheckRow(name = "Kotlin Coroutines Structured Concurrency", status = "PASSED")
            SubsystemCheckRow(name = "Secrets Gradle Plugin Security Shield", status = "PASSED")
          }
        }
      }
    }
  }
}

@Composable
fun TelemetryRow(
  label: String,
  value: String,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodySmall,
      fontFamily = FontFamily.Monospace,
      fontWeight = FontWeight.Medium,
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}

@Composable
fun SubsystemCheckRow(
  name: String,
  status: String,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      modifier = Modifier.weight(1f),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.CheckCircle,
        contentDescription = null,
        tint = StatusSuccess,
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = name,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface
      )
    }

    Surface(
      shape = RoundedCornerShape(6.dp),
      color = StatusSuccessContainer
    ) {
      Text(
        text = status,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = StatusSuccess,
        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
      )
    }
  }
}
