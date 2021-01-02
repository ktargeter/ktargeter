package ktargeter

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration

@AutoService(ComponentRegistrar::class)
class KtargeterComponentRegistrar : ComponentRegistrar {
    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration
    ) {
        if (configuration[KEY_ENABLED] == false) return
        ClassBuilderInterceptorExtension.registerExtension(
            project,
            KtargeterClassGenerationInterceptor(
                ktargeterAnnotations = configuration[KEY_ANNOTATIONS]
                    ?: error("ktargeter plugin requires at least one annotation class option passed to it")
            )
        )
    }
}
