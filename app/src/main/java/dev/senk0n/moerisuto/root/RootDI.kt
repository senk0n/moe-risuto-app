package dev.senk0n.moerisuto.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigationSource
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.tabs.AppDI

interface AppDIOwner {
    val appDI: AppDI
}

interface AppContext :
    ComponentContext,
    AppDIOwner

class AppComponentContextImpl(
    componentContext: ComponentContext,
    override val appDI: AppDI,
) : AppContext, ComponentContext by componentContext

inline fun <reified C : ComponentConfig, T : ComponentView> AppContext.appChildStack(
    source: StackNavigationSource<C>,
    noinline initialStack: () -> List<C>,
    key: String = "DefaultStack",
    handleBackButton: Boolean = false,
    noinline childFactory: (configuration: C, AppContext) -> T
): Value<ChildStack<C, T>> =
    childStack(
        source = source,
        initialStack = initialStack,
        key = key,
        handleBackButton = handleBackButton
    ) { configuration, componentContext ->
        childFactory(
            configuration,
            AppComponentContextImpl(
                componentContext = componentContext,
                appDI = appDI
            )
        )
    }