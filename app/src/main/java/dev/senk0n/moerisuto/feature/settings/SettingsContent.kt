package dev.senk0n.moerisuto.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import dev.senk0n.moerisuto.core.compose.LocalComposeFactory
import dev.senk0n.moerisuto.core.compose.provideCompose
import me.tatarka.inject.annotations.IntoMap
import me.tatarka.inject.annotations.Provides

@Composable
fun SettingsContent(component: SettingsComponent, modifier: Modifier) {
    val state by component.state.subscribeAsState()
    Column(modifier = modifier) {
        Column {
            Text(text = "SettingsContent is Loading: ${state.isLoading}")
            Text(text = "config: ${state.config}")
        }
    }
}

interface SettingsDIContent {
    @Provides
    @IntoMap
    fun provideSettingsContent() = provideCompose<SettingsComponentImpl> {
        SettingsContent(component = it, modifier = this)
    }
}
