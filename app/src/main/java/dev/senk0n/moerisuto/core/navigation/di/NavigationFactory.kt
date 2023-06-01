package dev.senk0n.moerisuto.core.navigation.di

import com.arkivanov.decompose.ComponentContext
import kotlin.reflect.KClass

inline fun <reified Key : P, P : Any, R> provideComponent(
    crossinline provider: ComponentContext.(Key) -> R
): Pair<KClass<out P>, DepProvider<P, R>> = Key::class to DepProvider { config, context ->
    val parameter = config as Key
    context.provider(parameter)
}

interface NavigationFactory<Key : Any, out R : Any> {
    val providerMap: Map<KClass<out Key>, DepProvider<Key, R>>
    fun create(key: Key, context: ComponentContext): R? {
        val provider = providerMap[key::class]
        return provider?.provide(key, context)
    }
}

fun interface DepProvider<in P, out R> {
    fun provide(config: P, context: ComponentContext): R
}

