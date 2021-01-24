package org.ktargeter

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration

class KtargeterComponentRegistrar : ComponentRegistrar {
    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration
    ) {
        val annotations = configuration[KEY_ANNOTATIONS] ?: return
        val annotationMapping = annotations.associateBy(
            { it.substringAfter(':') },
            { it.substringBefore(':') }
        )
        IrGenerationExtension.registerExtension(project, KtargeterExtension(annotationMapping))
    }
}
