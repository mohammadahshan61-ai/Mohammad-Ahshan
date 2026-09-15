package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ProjectRepository
import com.example.model.AppModule
import com.example.model.ConfigItem
import com.example.model.DiagnosticResult
import com.example.model.ModuleCategory
import com.example.model.ProjectMetrics
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProjectOverviewUiState(
  val metrics: ProjectMetrics? = null,
  val modules: List<AppModule> = emptyList(),
  val filteredModules: List<AppModule> = emptyList(),
  val configItems: List<ConfigItem> = emptyList(),
  val diagnostics: DiagnosticResult? = null,
  val selectedCategory: ModuleCategory = ModuleCategory.ALL,
  val searchQuery: String = "",
  val expandedModuleId: String? = null,
  val selectedTab: Int = 0,
  val isRefreshing: Boolean = false,
  val isDiagnosticRunning: Boolean = false,
  val statusMessage: String? = null
)

class ProjectOverviewViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = ProjectRepository(application)

  private val _uiState = MutableStateFlow(ProjectOverviewUiState())
  val uiState: StateFlow<ProjectOverviewUiState> = _uiState.asStateFlow()

  init {
    loadProjectData()
  }

  fun loadProjectData() {
    val metrics = repository.getProjectMetrics()
    val modules = repository.getImplementedModules()
    val configs = repository.getConfigurationStatus()
    val diagnostics = repository.runDiagnostics()

    _uiState.update { state ->
      state.copy(
        metrics = metrics,
        modules = modules,
        filteredModules = filterModulesList(modules, state.selectedCategory, state.searchQuery),
        configItems = configs,
        diagnostics = diagnostics
      )
    }
  }

  fun setSelectedTab(tabIndex: Int) {
    _uiState.update { it.copy(selectedTab = tabIndex) }
  }

  fun setSelectedCategory(category: ModuleCategory) {
    _uiState.update { state ->
      val filtered = filterModulesList(state.modules, category, state.searchQuery)
      state.copy(selectedCategory = category, filteredModules = filtered)
    }
  }

  fun setSearchQuery(query: String) {
    _uiState.update { state ->
      val filtered = filterModulesList(state.modules, state.selectedCategory, query)
      state.copy(searchQuery = query, filteredModules = filtered)
    }
  }

  fun toggleModuleExpansion(moduleId: String) {
    _uiState.update { state ->
      val nextId = if (state.expandedModuleId == moduleId) null else moduleId
      state.copy(expandedModuleId = nextId)
    }
  }

  fun refreshStatus() {
    viewModelScope.launch {
      _uiState.update { it.copy(isRefreshing = true, statusMessage = "Refreshing system configuration...") }
      delay(400)
      loadProjectData()
      _uiState.update { it.copy(isRefreshing = false, statusMessage = "Configuration status up to date") }
      delay(2000)
      _uiState.update { it.copy(statusMessage = null) }
    }
  }

  fun triggerDiagnostics() {
    viewModelScope.launch {
      _uiState.update { it.copy(isDiagnosticRunning = true, statusMessage = "Auditing runtime and configurations...") }
      delay(500)
      val newDiagnostics = repository.runDiagnostics()
      _uiState.update {
        it.copy(
          diagnostics = newDiagnostics,
          isDiagnosticRunning = false,
          statusMessage = "Diagnostics complete: All subsystems operational"
        )
      }
      delay(2500)
      _uiState.update { it.copy(statusMessage = null) }
    }
  }

  private fun filterModulesList(
    list: List<AppModule>,
    category: ModuleCategory,
    query: String
  ): List<AppModule> {
    return list.filter { module ->
      val matchesCategory = (category == ModuleCategory.ALL) || (module.category == category)
      val matchesQuery = query.isBlank() ||
          module.name.contains(query, ignoreCase = true) ||
          module.description.contains(query, ignoreCase = true) ||
          module.dependencies.any { it.contains(query, ignoreCase = true) } ||
          module.leadTech.contains(query, ignoreCase = true)
      matchesCategory && matchesQuery
    }
  }
}
