package dev.senk0n.moerisuto.feature.mediaitem

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.model.Media
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.provideComponent
import me.tatarka.inject.annotations.IntoMap
import me.tatarka.inject.annotations.Provides

interface MediaItemComponent : ComponentView {
    val state: Value<MediaState>
}

class MediaItemComponentImpl(
    private val componentContext: ComponentContext,
    private val config: MediaItemConfig,
) : MediaItemComponent, ComponentContext by componentContext {
    override val state: MutableValue<MediaState> = MutableValue(MediaState(config = config))

    override fun send(event: ComponentIntent) {
        when (event) {
        }
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
    fun provideMediaItemComponent() =
        provideComponent<MediaItemConfig> { MediaItemComponentImpl(this, it) }
}