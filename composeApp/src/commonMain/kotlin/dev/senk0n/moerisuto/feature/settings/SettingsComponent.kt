package dev.senk0n.moerisuto.feature.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentFactory
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentSink
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.provideComponent
import dev.senk0n.moerisuto.core.navigation.tabs.TabIcon
import dev.senk0n.moerisuto.core.navigation.tabs.TabMetadata
import dev.senk0n.moerisuto.core.navigation.tabs.provideTab
import dev.senk0n.moerisuto.preferences.model.SettingsType
import dev.senk0n.moerisuto.root.RootDI
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.annotations.IntoMap
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface SettingsComponent : ComponentView {
    val state: Value<SettingsState>
}

@Inject
class SettingsComponentImpl(
    @Assisted private val componentContext: ComponentContext,
    @Assisted private val config: SettingsConfig,
    @Assisted private val parentSink: ComponentSink,
    private val componentFactory: ComponentFactory,
) : SettingsComponent, ComponentContext by componentContext {
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
    fun provideSettingsComponent() = provideComponent<SettingsConfig, RootDI> { cfg, di, sink ->
        SettingsComponentDI::class.create(di).creator(this, cfg, sink)
    }

    @Provides
    @IntoMap
    fun provideSettingsTab() = provideTab<SettingsConfig> {
        TabMetadata(config = it, name = "Settings", icon = TabIcon.Settings)
    }

    @Provides
    @IntoSet
    fun provideSettingsSerializer() = SerializersModule {
        polymorphic(ComponentConfig::class) {
            subclass(SettingsConfig.serializer())
        }
    }
}

@Component
abstract class SettingsComponentDI(
    @Component @get:Provides val rootDI: RootDI,
) {
    @Provides
    fun SettingsComponentImpl.bind(): SettingsComponent = this
    abstract val creator: (ComponentContext, SettingsConfig, ComponentSink) -> SettingsComponent
}