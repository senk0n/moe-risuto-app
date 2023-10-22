package dev.senk0n.moerisuto.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
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
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.senk0n.moerisuto.Res
import dev.senk0n.moerisuto.core.compose.ComposeFactoryDI
import dev.senk0n.moerisuto.core.compose.LocalComposeFactory
import dev.senk0n.moerisuto.core.compose.create
import dev.senk0n.moerisuto.core.compose.tabs.tabIcons

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    val composeFactoryDI = remember { ComposeFactoryDI::class.create() }
    val composeFactory = remember { composeFactoryDI.composeFactory }
    CompositionLocalProvider(
        LocalComposeFactory provides composeFactory,
    ) {
        val childStack by component.tabStack.subscribeAsState()
        Scaffold(
            modifier = modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.waterfall,
            content = {
                Column(modifier = Modifier
                    .padding(it)
                    .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeContent))
                    Text(text = "===========")
                    Text(text = Res.string.app_name)
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
                        val selected = it.config == childStack.active.configuration
                        NavigationBarItem(
                            selected = selected,
                            onClick = { component.send(ClickTab(it.config)) },
                            icon = {
                                val tabIcon = tabIcons[it.icon]
                                val icon = tabIcon?.let {
                                    if (selected) tabIcon.second else tabIcon.first
                                } ?: Icons.Outlined.Menu
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
