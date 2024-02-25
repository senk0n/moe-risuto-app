package dev.senk0n.moerisuto.core.navigation

interface ComponentSink {
    fun send(event: ComponentIntent)
}

interface ComponentView : ComponentSink
fun interface ParentSink : ComponentSink

interface ComponentConfig

interface ComponentIntent
interface ComponentDI