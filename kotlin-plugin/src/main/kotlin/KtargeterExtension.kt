package org.ktargeter

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment

class KtargeterExtension(ktargeterAnnotations: List<String>) : IrGenerationExtension {
    private val annotations = ktargeterAnnotations.associateBy(
        { it.substringAfter(':') },
        { it.substringBefore(':') }
    )
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        moduleFragment.transform(KtargeterTransformer(annotations), null)
    }
}
