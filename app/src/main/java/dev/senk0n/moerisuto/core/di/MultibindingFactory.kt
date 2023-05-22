package dev.senk0n.moerisuto.core.di

typealias ClassKey = String

inline fun <reified Key : P, P, R> provideMapEntry(
    crossinline provider: (Key) -> R
): Pair<ClassKey, DepProvider<P, R>> =
    (Key::class.qualifiedName as ClassKey) to DepProvider {
        val parameter = it as Key
        provider.invoke(parameter)
    }

interface MultibindingFactory<in K : Any, out R : Any> {
    val providerMap: Map<ClassKey, DepProvider<K, R>>
    fun create(key: K): R? {
        val provider = providerMap[key::class.qualifiedName]
        return provider?.provide(key)
    }
}

fun interface DepProvider<in P, out R> {
    fun provide(parameter: P): R
}

