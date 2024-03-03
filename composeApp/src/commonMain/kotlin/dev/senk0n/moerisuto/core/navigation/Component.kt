package dev.senk0n.moerisuto.core.navigation

fun interface ComponentSink {
    fun send(event: ComponentIntent)
}

interface ComponentView : ComponentSink

interface ComponentConfig

interface ComponentIntent
interface ComponentDI