package dev.senk0n.moerisuto.core.navigation.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentView

interface TabConfig : ComponentConfig
interface TabComponentView : ComponentView {
    val navigation: StackNavigation<ComponentConfig>
    val childStack: Value<ChildStack<ComponentConfig, ComponentView>>
}
