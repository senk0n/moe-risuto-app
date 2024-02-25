package dev.senk0n.moerisuto.feature.mylist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.model.Entry
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentFactory
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.SerializerProvider
import dev.senk0n.moerisuto.core.navigation.ParentSink
import dev.senk0n.moerisuto.core.navigation.provideComponent
import dev.senk0n.moerisuto.core.navigation.tabs.TabIcon
import dev.senk0n.moerisuto.core.navigation.tabs.TabMetadata
import dev.senk0n.moerisuto.core.navigation.tabs.bringToFrontEq
import dev.senk0n.moerisuto.core.navigation.tabs.provideTab
import dev.senk0n.moerisuto.feature.mediaitem.MediaItemConfig
import dev.senk0n.moerisuto.root.RootDI
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.annotations.IntoMap
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface MyListComponent : ComponentView {
    val state: Value<MyListState>
    val childStack: Value<ChildStack<ComponentConfig, ComponentView>>
}

data class MyListState(
    val config: MyListConfig? = null,
    val isLoading: Boolean = true,
    val entriesList: List<Entry> = emptyList(),
)

@Inject
class MyListComponentImpl(
    @Assisted private val componentContext: ComponentContext,
    @Assisted private val config: MyListConfig,
    @Assisted private val parentSink: ParentSink,
    private val rootDI: RootDI,
    private val componentFactory: ComponentFactory,
    private val serializerProvider: SerializerProvider,
) : MyListComponent, ComponentContext by componentContext {
    override val state: MutableValue<MyListState> = MutableValue(MyListState(config = config))

    private val navigation = StackNavigation<ComponentConfig>()
    override val childStack: Value<ChildStack<ComponentConfig, ComponentView>> = childStack(
        source = navigation,
        serializer = null, //serializerProvider.serializer(),
        initialStack = { listOf(MediaItemConfig("anime", "completed")) },
        childFactory = { key, context -> componentFactory.run { create(key, context, rootDI) } }
    )

    override fun send(event: ComponentIntent) = when (event) {
        is OpenEntryDetailsIntent -> {
            navigation.bringToFrontEq(config)
        }

        else -> Unit
    }

}

interface MyListDIModule {
    @Provides
    @IntoMap
    fun provideMyListComponent() = provideComponent<MyListConfig, RootDI> { cfg, di, sink ->
        MyListComponentDI::class.create(di).creator(this, cfg, sink)
    }

    @Provides
    @IntoMap
    fun provideMyListTab() = provideTab<MyListConfig> {
        TabMetadata(config = it, name = "My List", icon = TabIcon.MyList)
    }

    @Provides
    @IntoSet
    fun provideMyListSerializer() = SerializersModule {
        polymorphic(ComponentConfig::class) {
            subclass(MyListConfig.serializer())
        }
    }
}

@Component
abstract class MyListComponentDI(
    @Component @get:Provides val rootDI: RootDI,
) {
    @Provides
    fun MyListComponentImpl.bind(): MyListComponent = this
    abstract val creator: (ComponentContext, MyListConfig, ParentSink) -> MyListComponent
}