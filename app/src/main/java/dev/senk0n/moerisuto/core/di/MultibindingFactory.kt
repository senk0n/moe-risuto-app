package dev.senk0n.moerisuto.core.di

import kotlin.reflect.KClass

inline fun <reified Key : P, P : Any, R> provideMapEntry(
    crossinline provider: (Key) -> R
): Pair<KClass<out P>, DepProvider<P, R>> = Key::class to DepProvider {
    val parameter = it as Key
    provider.invoke(parameter)
}

interface MultibindingFactory<Key : Any, out R : Any> {
    val providerMap: Map<KClass<out Key>, DepProvider<Key, R>>
    fun create(key: Key): R? {
        val provider = providerMap[key::class]
        return provider?.provide(key)
    }
}

fun interface DepProvider<in P, out R> {
    fun provide(parameter: P): R
}

