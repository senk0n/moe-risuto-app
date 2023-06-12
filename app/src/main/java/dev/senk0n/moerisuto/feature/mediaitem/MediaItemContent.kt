package dev.senk0n.moerisuto.feature.mediaitem

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import dev.senk0n.moerisuto.core.compose.provideCompose
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.IntoMap
import me.tatarka.inject.annotations.Provides

@Composable
fun MediaItemContent(component: MediaItemComponent, modifier: Modifier) {
    val state by component.state.subscribeAsState()
    Column(modifier = modifier) {
        Text(text = "MediaItemContent is Loading: ${state.isLoading}")
        Text(text = "config: ${state.config}")
    }
}

interface MediaItemDIContent {
    @Provides
    @IntoMap
    fun provideMediaItemContent() =
        provideCompose<MediaItemComponentImpl> {
            MediaItemContent(it, this)
        }
}
