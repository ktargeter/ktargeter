package ktargeter

import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.ClassBuilderFactory
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
import org.jetbrains.kotlin.diagnostics.DiagnosticSink
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin

abstract class DelegatingClassBuilder(
    private val delegatingClassBuilder: ClassBuilder
) : DelegatingClassBuilder() {
    override fun getDelegate(): ClassBuilder = delegatingClassBuilder
}

class KtargeterClassGenerationInterceptor(
    val ktargeterAnnotations: List<String>
) : ClassBuilderInterceptorExtension {
    override fun interceptClassBuilderFactory(
        interceptedFactory: ClassBuilderFactory,
        bindingContext: BindingContext,
        diagnostics: DiagnosticSink
    ): ClassBuilderFactory = object : ClassBuilderFactory by interceptedFactory {
        override fun newClassBuilder(origin: JvmDeclarationOrigin) = KtargeterClassBuilder(
            ktargeterAnnotations,
            interceptedFactory.newClassBuilder(origin)
        )
    }
}

