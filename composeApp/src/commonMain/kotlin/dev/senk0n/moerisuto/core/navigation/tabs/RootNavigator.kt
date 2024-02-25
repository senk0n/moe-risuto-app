package dev.senk0n.moerisuto.core.navigation.tabs

import com.arkivanov.decompose.router.stack.StackNavigator
import dev.senk0n.moerisuto.core.navigation.ComponentDI
import dev.senk0n.moerisuto.core.navigation.ComponentFactoryDI
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Scope

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class RootScope

@Component
abstract class AppDI(
    @Component val componentFactoryDI: ComponentFactoryDI,
) : ComponentDI

fun <C : Any> StackNavigator<C>.bringToFrontEq(configuration: C, onComplete: () -> Unit = {}) {
    navigate(
        transformer = { stack -> stack.filterNot { it == configuration } + configuration },
        onComplete = { _, _ -> onComplete() },
    )
}
