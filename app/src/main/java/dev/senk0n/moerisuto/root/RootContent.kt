package dev.senk0n.moerisuto.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessibleForward
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import dev.senk0n.moerisuto.core.compose.ComposeFactoryDI
import dev.senk0n.moerisuto.core.compose.LocalComposeFactory
import dev.senk0n.moerisuto.core.compose.create

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    val composeFactoryDI = remember { ComposeFactoryDI::class.create() }
    val composeFactory = remember { composeFactoryDI.composeFactory }
    CompositionLocalProvider(
        LocalComposeFactory provides composeFactory,
    ) {
        val childStack by component.tabStack.subscribeAsState()
        Scaffold(
            modifier = modifier,
            content = {
                Column(modifier = Modifier.padding(it)) {
                    Text(text = "===========")
                    Text(text = "RootContent")
                    Text(text = "===========")
                    Spacer(modifier = Modifier.height(8.dp))

                    Children(
                        stack = childStack,
                        modifier = Modifier.fillMaxWidth()
                    ) { child ->
                        val provider = LocalComposeFactory.current.create(child.instance)
                        provider?.Display(child.instance, Modifier)
                    }
                }
            },
            bottomBar = {
                NavigationBar(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    val tabs by component.tabsMetadata.subscribeAsState()
                    tabs.mainTabs.forEach {
                        NavigationBarItem(
                            selected = it.config == childStack.active.configuration,
                            onClick = { component.send(ClickTab(it.config)) },
                            icon = {
                                val icon = Icons.Outlined.AccessibleForward
                                val painter = rememberVectorPainter(image = icon)
                                Icon(painter = painter, null)
                            },
                            label = { Text(text = it.name) },
                        )
                    }
                }
            }
        )
    }
}
