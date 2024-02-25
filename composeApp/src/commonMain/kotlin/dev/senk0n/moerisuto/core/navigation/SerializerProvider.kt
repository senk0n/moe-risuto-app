package dev.senk0n.moerisuto.core.navigation

import dev.senk0n.moerisuto.core.navigation.tabs.RootScope
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.serializer
import me.tatarka.inject.annotations.Inject

@RootScope
@Inject
class SerializerProvider(
    private val modules: Set<SerializersModule>
) {
    var combinedModules: SerializersModule? = null
    fun serializer(): KSerializer<ComponentConfig> {
        val module = combinedModules ?: let {
            val newModule = SerializersModule {
                polymorphic(ComponentConfig::class) {
                    for (module in modules) {
                        include(module)
                    }
                }
            }
//            modules.reduce { acc, module -> acc + module }
            combinedModules = newModule
            newModule
        }
        return module.serializer<ComponentConfig>()
    }
}

@OptIn(InternalSerializationApi::class)
inline fun <reified Klass : @Serializable ComponentConfig> provideConfigSerializer(
): SerializersModule = SerializersModule {
    polymorphic(ComponentConfig::class) {
        subclass(Klass::class, Klass::class.serializer())
    }
}
