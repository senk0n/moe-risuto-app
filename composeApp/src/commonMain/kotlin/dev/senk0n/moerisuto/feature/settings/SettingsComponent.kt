package dev.senk0n.moerisuto.feature.settings

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.navigation.ComponentFactory
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.provideComponent
import dev.senk0n.moerisuto.preferences.model.SettingsType
import dev.senk0n.moerisuto.root.AppContext
import me.tatarka.inject.annotations.IntoMap
import me.tatarka.inject.annotations.Provides

interface SettingsComponent : ComponentView {
    val state: Value<SettingsState>
}

class SettingsComponentImpl(
    private val appContext: AppContext,
    private val config: SettingsConfig,
    private val componentFactory: ComponentFactory,
) : SettingsComponent, AppContext by appContext {
    override val state: MutableValue<SettingsState> = MutableValue(SettingsState(config = config))

    override fun send(event: ComponentIntent) = when (event) {
        else -> Unit
    }
}

data class SettingsState(
    val config: SettingsConfig,
    val isLoading: Boolean = true,
    val preferencesList: List<SettingsType> = emptyList(),
)

interface SettingsDIModule {
    @Provides
    @IntoMap
    fun provideSettingsComponent() = provideComponent<SettingsConfig> {
        SettingsComponentImpl(
            appContext = this,
            config = it,
            componentFactory = appDI.componentFactoryDI.componentFactory
        )
    }
}
