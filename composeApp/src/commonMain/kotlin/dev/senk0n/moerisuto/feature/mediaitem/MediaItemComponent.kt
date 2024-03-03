package dev.senk0n.moerisuto.feature.mediaitem

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.model.Media
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentSink
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.provideComponent
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

interface MediaItemComponent : ComponentView {
    val state: Value<MediaState>
}

@Inject
class MediaItemComponentImpl(
    @Assisted private val componentContext: ComponentContext,
    @Assisted private val config: MediaItemConfig,
    @Assisted private val parentSink: ComponentSink,
) : MediaItemComponent, ComponentContext by componentContext {
    override val state: MutableValue<MediaState> = MutableValue(MediaState(config = config))

    override fun send(event: ComponentIntent) = when (event) {
        else -> Unit
    }

}

data class MediaState(
    val config: MediaItemConfig? = null,
    val isLoading: Boolean = true,
    val media: Media? = null,
)

interface MediaItemDIModule {
    @Provides
    @IntoMap
    fun provideMediaItemComponent() = provideComponent<MediaItemConfig, RootDI> { cfg, di, sink ->
        MediaItemComponentDI::class.create(di).creator(this, cfg, sink)
    }

    @Provides
    @IntoSet
    fun provideMediaItemSerializer() = SerializersModule {
        polymorphic(ComponentConfig::class) {
            subclass(MediaItemConfig.serializer())
        }
    }
}

@Component
abstract class MediaItemComponentDI(
    @Component @get:Provides val rootDI: RootDI,
) {
    @Provides
    fun MediaItemComponentImpl.bind(): MediaItemComponent = this
    abstract val creator: (ComponentContext, MediaItemConfig, ComponentSink) -> MediaItemComponent
}