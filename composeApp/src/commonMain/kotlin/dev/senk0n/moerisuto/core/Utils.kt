package dev.senk0n.moerisuto.core

inline fun <reified T : Any> classNameOf(): String = T::class.simpleName ?: ""
inline val Any.className: String get() = this::class.simpleName ?: ""