package ktargeter

import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension

class KtargeterComponentRegistrar : ComponentRegistrar {
    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration
    ) {
        if (configuration[KEY_ENABLED] == false) return
        val extension = KtargeterExtension(
            ktargeterAnnotations = configuration[KEY_ANNOTATIONS]
                ?: error("ktargeter plugin requires at least one annotation class option passed to it")
        )
        project.extensionArea.getExtensionPoint(SyntheticResolveExtension.extensionPointName)
            .registerExtension(extension, project)
    }
}
