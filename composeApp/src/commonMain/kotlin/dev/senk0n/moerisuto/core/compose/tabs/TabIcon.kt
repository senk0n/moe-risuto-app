package dev.senk0n.moerisuto.core.compose.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import dev.senk0n.moerisuto.core.navigation.tabs.TabIcon

val tabIcons = mapOf(
    TabIcon.Home to (Icons.Outlined.Home to Icons.Filled.Home),
    TabIcon.MyList to (Icons.Outlined.List to Icons.Filled.List),
    TabIcon.Catalog to (Icons.Outlined.Search to Icons.Filled.Search),
    TabIcon.Settings to (Icons.Outlined.Settings to Icons.Filled.Settings),
    TabIcon.Profile to (Icons.Outlined.Person to Icons.Filled.Person),
)