package dev.senk0n.moerisuto.feature.mylist

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import dev.senk0n.moerisuto.core.compose.provideCompose
import dev.senk0n.moerisuto.feature.mediaitem.MediaItemComponent
import me.tatarka.inject.annotations.IntoMap
import me.tatarka.inject.annotations.Provides

@Composable
fun MyListContent(component: MyListComponent, modifier: Modifier) {
    val state by component.state.subscribeAsState()
    Column(modifier = modifier) {
        Text(text = "MyListContent is Loading: ${state.isLoading}")
        Text(text = "config: ${state.config}")
    }
}

interface MyListDIContent {
    @Provides
    @IntoMap
    fun provideMyListContent() =
        provideCompose<MyListComponentImpl> {
            MyListContent(it, this)
        }
}