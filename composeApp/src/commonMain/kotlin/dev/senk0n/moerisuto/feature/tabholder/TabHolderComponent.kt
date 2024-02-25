package dev.senk0n.moerisuto.feature.tabholder

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentFactory
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.ParentSink
import dev.senk0n.moerisuto.core.navigation.SerializerProvider
import dev.senk0n.moerisuto.feature.mediaitem.MediaItemConfig
import dev.senk0n.moerisuto.root.RootDI
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

interface TabHolderComponent : ComponentView {
    val state: Value<TabHolderState>
    val childStack: Value<ChildStack<ComponentConfig, ComponentView>>
}

data class TabHolderState(
    val singleScreenDisplayed: Boolean = true,
    val showNavBar: Boolean = true,
)

@Inject
class TabHolderComponentImpl(
    @Assisted private val componentContext: ComponentContext,
    @Assisted private val config: TabHolderConfig,
    @Assisted private val parentSink: ParentSink,
    private val rootDI: RootDI,
    private val componentFactory: ComponentFactory,
    private val serializerProvider: SerializerProvider,
) : TabHolderComponent, ComponentContext by componentContext {
    override val state: MutableValue<TabHolderState> = MutableValue(TabHolderState())

    private val navigation = StackNavigation<ComponentConfig>()

    override val childStack: Value<ChildStack<ComponentConfig, ComponentView>> = childStack(
        source = navigation,
        serializer = null, //serializerProvider.serializer(),
        initialStack = { listOf(config.rootConfig) },
        childFactory = { key, context -> componentFactory.run { create(key, context, rootDI) } }
    )

    init {
        childStack.subscribe {
            it.active
        }
    }

    override fun send(event: ComponentIntent) {
        when (event) {
            is TabHolderIntent -> {

            }
        }
    }

}