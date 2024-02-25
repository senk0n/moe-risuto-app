package dev.senk0n.moerisuto.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentDI
import dev.senk0n.moerisuto.core.navigation.ComponentFactory
import dev.senk0n.moerisuto.core.navigation.ComponentFactoryDI
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.SerializerProvider
import dev.senk0n.moerisuto.core.navigation.create
import dev.senk0n.moerisuto.core.navigation.tabs.AppDI
import dev.senk0n.moerisuto.core.navigation.tabs.RootScope
import dev.senk0n.moerisuto.core.navigation.tabs.TabFactory
import dev.senk0n.moerisuto.core.navigation.tabs.TabMetadata
import dev.senk0n.moerisuto.core.navigation.tabs.create
import dev.senk0n.moerisuto.feature.mylist.MyListConfig
import dev.senk0n.moerisuto.feature.settings.SettingsConfig
import me.tatarka.inject.annotations.Component

interface RootComponent : ComponentView {
    val tabStack: Value<ChildStack<ComponentConfig, ComponentView>>
    val tabsMetadata: Value<List<TabMetadata>>
}

class RootComponentImpl(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {
    private val tabNavigation = StackNavigation<ComponentConfig>()

    private val rootDI: RootDI
    init {
        val componentFactoryDI = ComponentFactoryDI::class.create()
        val appDI = AppDI::class.create(
            componentFactoryDI = componentFactoryDI,
        )
        rootDI = RootDI::class.create(
            appDI = appDI,
        )
    }

    private val initialStack = listOf(
        MyListConfig("anime", "completed"),
        SettingsConfig("42", "mushoku"),
    )

    override val tabsMetadata: Value<List<TabMetadata>> = initialStack
        .mapNotNull { rootDI.tabFactory.create(it) }
        .let { MutableValue(it) }

    override val tabStack = componentContext.childStack(
        source = tabNavigation,
        serializer = null, //rootDI.serializerProvider.serializer(),
        initialStack = { tabsMetadata.value.map { it.config } },
        childFactory = { key, context ->
            rootDI.componentFactory.run { create(key, context, rootDI) }
        }
    )

    override fun send(event: ComponentIntent) {
        when (event) {
            is ClickTab -> {
                tabNavigation.bringToFront(event.config)
            }
        }
    }
}

@RootScope
@Component
abstract class RootDI(
    @Component val appDI: AppDI,
) : ComponentDI {
    abstract val componentFactory: ComponentFactory
    abstract val serializerProvider: SerializerProvider
    abstract val tabFactory: TabFactory
}