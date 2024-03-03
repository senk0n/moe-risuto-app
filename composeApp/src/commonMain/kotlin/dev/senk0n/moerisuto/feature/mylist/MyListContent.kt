package dev.senk0n.moerisuto.feature.mylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.senk0n.moerisuto.core.compose.LocalComposeFactory
import dev.senk0n.moerisuto.core.compose.provideCompose
import me.tatarka.inject.annotations.IntoMap
import me.tatarka.inject.annotations.Provides

@Composable
fun MyListContent(component: MyListComponent, modifier: Modifier) {
    val state by component.state.subscribeAsState()
    val childStack by component.childStack.subscribeAsState()
    Column {
        Column(modifier = modifier) {
            Text(text = "MyListContent is Loading: ${state.isLoading}")
            Text(text = "config: ${state.config}")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            component.send(OpenEntryDetailsIntent())
        }) {
            Text(text = "add tab")
        }

        Children(
            stack = childStack,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val provider = LocalComposeFactory.current.create(it.instance)
            provider?.Display(it.instance, Modifier)
        }
    }

}

interface MyListDIContent {
    @Provides
    @IntoMap
    fun provideMyListContent() = provideCompose<MyListComponentImpl> {
        MyListContent(it, this)
    }
}