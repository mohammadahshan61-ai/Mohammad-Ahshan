package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Http
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppModule
import com.example.model.ConfigHealth
import com.example.model.ConfigItem
import com.example.model.ModuleCategory
import com.example.model.ProjectMetrics
import com.example.ui.theme.StatusInfo
import com.example.ui.theme.StatusInfoContainer
import com.example.ui.theme.StatusSuccess
import com.example.ui.theme.StatusSuccessContainer
import com.example.ui.theme.StatusWarning
import com.example.ui.theme.StatusWarningContainer

@Composable
fun ProjectHeroCard(
  metrics: ProjectMetrics,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("project_hero_card"),
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(
              MaterialTheme.colorScheme.primaryContainer,
              MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
          )
        )
        .padding(20.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = metrics.projectName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = metrics.appId,
            style = MaterialTheme.typography.bodySmall,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
          )
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.primary,
          modifier = Modifier.testTag("badge_build_type")
        ) {
          Text(
            text = metrics.buildType,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 3 KPI Pills
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        KpiMetricTile(
          label = "Modules",
          value = "${metrics.operationalModulesCount}/${metrics.modulesCount}",
          subtext = "100% Operational",
          modifier = Modifier.weight(1f)
        )
        KpiMetricTile(
          label = "Target SDK",
          value = "${metrics.targetSdk}",
          subtext = "Android 15/16",
          modifier = Modifier.weight(1f)
        )
        KpiMetricTile(
          label = "Config Health",
          value = "${metrics.passingChecks}/${metrics.totalChecks}",
          subtext = "Optimal",
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Column(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Architecture Build Readiness",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
          )
          Text(
            text = "100%",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
          progress = { 1.0f },
          modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .testTag("progress_readiness"),
          color = MaterialTheme.colorScheme.primary,
          trackColor = MaterialTheme.colorScheme.surface
        )
      }
    }
  }
}

@Composable
fun KpiMetricTile(
  label: String,
  value: String,
  subtext: String,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
    tonalElevation = 1.dp
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = subtext,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
        color = StatusSuccess,
        fontWeight = FontWeight.Medium
      )
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ModuleCard(
  module: AppModule,
  isExpanded: Boolean,
  onToggleExpand: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .animateContentSize(animationSpec = spring())
      .clickable { onToggleExpand() }
      .testTag("module_card_${module.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = getCategoryIcon(module.category),
            contentDescription = module.category.displayName,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.size(24.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = module.name,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }

          Spacer(modifier = Modifier.height(2.dp))

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = StatusSuccessContainer
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(StatusSuccess)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = module.status.label,
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = StatusSuccess
                )
              }
            }

            Text(
              text = module.version,
              style = MaterialTheme.typography.labelSmall,
              fontFamily = FontFamily.Monospace,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Icon(
          imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
          contentDescription = if (isExpanded) "Collapse module details" else "Expand module details",
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(24.dp)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = module.description,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      AnimatedVisibility(visible = isExpanded) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
        ) {
          Text(
            text = "Core Features",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
          )

          Spacer(modifier = Modifier.height(6.dp))

          module.features.forEach { feature ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = StatusSuccess,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = feature,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Gradle Dependencies",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
          )

          Spacer(modifier = Modifier.height(6.dp))

          FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            module.dependencies.forEach { dep ->
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.border(
                  width = 0.5.dp,
                  color = MaterialTheme.colorScheme.outlineVariant,
                  shape = RoundedCornerShape(8.dp)
                )
              ) {
                Text(
                  text = dep,
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                  fontFamily = FontFamily.Monospace,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Subsystem Health Check",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = module.healthStatus,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = StatusSuccess
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun ConfigItemCard(
  item: ConfigItem,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("config_card_${item.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = item.category.uppercase(),
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, letterSpacing = 1.sp),
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        )

        StatusPill(status = item.status)
      }

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = item.label,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(2.dp))

      Text(
        text = item.value,
        style = MaterialTheme.typography.bodyMedium,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = item.detail,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
      )
    }
  }
}

@Composable
fun StatusPill(
  status: ConfigHealth,
  modifier: Modifier = Modifier
) {
  val (bgColor, textColor, label, icon) = when (status) {
    ConfigHealth.OPTIMAL -> Quadruple(
      StatusSuccessContainer,
      StatusSuccess,
      "Optimal",
      Icons.Default.CheckCircle
    )
    ConfigHealth.NORMAL -> Quadruple(
      StatusInfoContainer,
      StatusInfo,
      "Configured",
      Icons.Default.Info
    )
    ConfigHealth.ATTENTION -> Quadruple(
      StatusWarningContainer,
      StatusWarning,
      "Notice",
      Icons.Default.Warning
    )
    ConfigHealth.INFO -> Quadruple(
      MaterialTheme.colorScheme.surfaceVariant,
      MaterialTheme.colorScheme.onSurfaceVariant,
      "System",
      Icons.Default.Info
    )
  }

  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    color = bgColor
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = textColor,
        modifier = Modifier.size(12.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = textColor
      )
    }
  }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

fun getCategoryIcon(category: ModuleCategory): ImageVector {
  return when (category) {
    ModuleCategory.ALL -> Icons.Default.Code
    ModuleCategory.CORE -> Icons.Default.Code
    ModuleCategory.UI -> Icons.Default.Palette
    ModuleCategory.DATA -> Icons.Default.Storage
    ModuleCategory.NETWORK -> Icons.Default.Http
    ModuleCategory.CLOUD -> Icons.Default.Cloud
    ModuleCategory.QA -> Icons.Default.FactCheck
  }
}
